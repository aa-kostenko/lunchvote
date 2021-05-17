package org.example.lunchvote.web.vote;

import org.example.lunchvote.AuthorizedUser;
import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.VoteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.util.DateTimeUtil.atStartOfDayOrMin;
import static org.example.lunchvote.util.DateTimeUtil.atStartOfNextDayOrMax;

@RestController
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    static final String REST_URL = "/rest/votes";

    private final VoteRepository repository;

    public VoteRestController(VoteRepository repository) {
        this.repository = repository;
    }
    @GetMapping
    public List<Vote> getAllOnDate(@AuthenticationPrincipal AuthorizedUser authUser) {
        LocalDate dateNow = LocalDate.now();
        int userId = authUser.getId();
        return repository.getAllBetween(atStartOfDayOrMin(dateNow), atStartOfNextDayOrMax(dateNow), userId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createWithLocation(@Validated @RequestBody Vote vote) {
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

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(repository.getOne(id));
    }

}
