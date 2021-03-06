package org.example.lunchvote.web.vote;

import org.example.lunchvote.AuthorizedUser;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.RestaurantRepository;
import org.example.lunchvote.repository.UserRepository;
import org.example.lunchvote.repository.VoteRepository;
import org.example.lunchvote.to.VoteResult;
import org.example.lunchvote.to.VoteTo;
import org.example.lunchvote.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.lunchvote.util.DateTimeUtil.*;
import static org.example.lunchvote.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.lunchvote.util.validation.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    private final VoteRepository repository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    public VoteRestController(VoteRepository repository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public Vote getForUser(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int id) {
        return repository
                .getForUser(id, authUser.getId())
                .orElseThrow(() -> new NotFoundException("Not found Vote with id " + id + " for user with id " + authUser.getId()));
    }

    @GetMapping
    public List<Vote> getAllForUser(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        return repository.getAllForUser(userId);
    }

    @GetMapping("/today")
    public List<Vote> getAllTodayForUser(@AuthenticationPrincipal AuthorizedUser authUser) {
        LocalDate dateNow = LocalDate.now();
        int userId = authUser.getId();
        return repository.getAllBetweenForUser(atStartOfDayOrMin(dateNow), atStartOfNextDayOrMax(dateNow), userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthorizedUser authUser, @Validated @RequestBody VoteTo voteTo) throws BindException {

        Restaurant restaurant = restaurantRepository
                .findById(voteTo.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id " + voteTo.getRestaurantId()));

        Vote vote = new Vote();
        if (isUseCurrentTime()) {
            vote.setDateTime(LocalDateTime.now());
        } else {
            vote.setDateTime(LocalDateTime.of(LocalDate.now(), getTimeForTest()));
        }

        vote.setRestaurant(restaurant);
        vote.setUser(userRepository.getOne(authUser.getId()));
        validateVote(vote);
        Vote created = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthorizedUser authUser, @Validated @RequestBody VoteTo voteTo, @PathVariable int id) throws BindException {
        assureIdConsistent(voteTo, id);
        Vote vote = repository
                .getForUser(id, authUser.getId())
                        .orElseThrow(() -> new NotFoundException("Not found Vote with id " + id + " for AuthorizedUser!"));

        Restaurant restaurant = restaurantRepository
                .findById(voteTo.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id " + voteTo.getRestaurantId()));

        vote.setRestaurant(restaurant);

        if (isUseCurrentTime()) {
            vote.setDateTime(LocalDateTime.now());
        } else {
            vote.setDateTime(LocalDateTime.of(LocalDate.now(), getTimeForTest()));
        }

        validateVote(vote);

        repository.save(vote);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @GetMapping("/todayResult")
    public List<VoteResult> getVoteResultToday() {
        LocalDate dateNow = LocalDate.now();
        List<Vote> votes = repository.getAllWithRestaurantsBetween(atStartOfDayOrMin(dateNow), atStartOfNextDayOrMax(dateNow));
        List<VoteResult> voteResultList = new ArrayList<>();
        Map<Restaurant, Long> restaurantVoteCount = votes.stream().collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        restaurantVoteCount.forEach((k, v)-> voteResultList.add(new VoteResult(k, dateNow, v)));
        return voteResultList.stream()
                .sorted(Comparator.comparing(VoteResult::getVoteCount).reversed())
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkSingleModification(repository.delete(id), "Vote id=" + id + " missed");
    }

    protected void validateVote(Vote vote) throws BindException {
        DataBinder binder = new DataBinder(vote);
        binder.addValidators(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }

}
