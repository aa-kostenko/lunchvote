package org.example.lunchvote.web.lunchmenuitem;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.repository.LunchMenuItemRepository;
import org.example.lunchvote.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.example.lunchvote.util.validation.ValidationUtil.assureIdConsistent;
import static org.example.lunchvote.util.validation.ValidationUtil.checkSingleModification;

@RestController
@RequestMapping(value = LunchMenuItemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class LunchMenuItemRestController {
    static final String REST_URL = "/rest/lunchmenuitems";

    private final LunchMenuItemRepository repository;

    public LunchMenuItemRestController(LunchMenuItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{id}")
    public LunchMenuItem get(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found Lunch Menu Item with id " + id));
    }

    @GetMapping("/allToday")
    public List<LunchMenuItem> getAllTodayForRestaurant(@RequestParam int restaurantId) {
        LocalDate dateNow = LocalDate.now();
        return repository.getAllBetween(restaurantId, dateNow, dateNow );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LunchMenuItem> createWithLocation(@Validated @RequestBody LunchMenuItem lunchMenuItem) {
        LunchMenuItem created = repository.save(lunchMenuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@Validated @RequestBody LunchMenuItem lunchMenuItem, @PathVariable int id) {
        assureIdConsistent(lunchMenuItem, id);
        repository.save(lunchMenuItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        checkSingleModification(repository.delete(id), "LunchMenuItem id=" + id + " missed");
    }
}
