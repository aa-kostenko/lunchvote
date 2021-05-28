package org.example.lunchvote.web.vote;

import org.example.lunchvote.AuthorizedUser;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.model.User;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.RestaurantRepository;
import org.example.lunchvote.repository.UserRepository;
import org.example.lunchvote.repository.VoteRepository;
import org.example.lunchvote.to.VoteTo;
import org.example.lunchvote.util.exception.NotFoundException;
import org.example.lunchvote.web.json.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.example.lunchvote.util.DateTimeUtil.atStartOfDayOrMin;
import static org.example.lunchvote.util.DateTimeUtil.atStartOfNextDayOrMax;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    private final VoteRepository repository;

    private final UserRepository userRepository;

    private final RestaurantRepository restaurantRepository;


    public VoteRestController(VoteRepository repository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }


    @GetMapping("/{id}")
    public Vote get(@AuthenticationPrincipal AuthorizedUser authUser, @PathVariable int id) {
        return repository
                .get(id, authUser.getId())
                .orElseThrow(() -> new NotFoundException("Not found Vote with id " + id + " for user with id " + authUser.getId()));
    }

    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthorizedUser authUser) {
        int userId = authUser.getId();
        return repository.getAll(userId);
    }

    @GetMapping("/today")
    public List<Vote> getToday(@AuthenticationPrincipal AuthorizedUser authUser) {
        LocalDate dateNow = LocalDate.now();
        int userId = authUser.getId();
        return repository.getAllBetween(atStartOfDayOrMin(dateNow), atStartOfNextDayOrMax(dateNow), userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@AuthenticationPrincipal AuthorizedUser authUser, @Validated @RequestBody VoteTo voteTo) {
        int userId = authUser.getId();
        User user = userRepository.findById(userId).get();
        int restaurantId =  voteTo.getRestaurantId();
        Restaurant restaurant = restaurantRepository
                .findById(restaurantId)
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id " + restaurantId));
        Vote vote = new Vote(null, LocalDateTime.now(), user, restaurant);
        Vote created = repository.save(vote);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Vote vote, @PathVariable int id) {
        repository.save(vote);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(repository.getOne(id));
    }

}
