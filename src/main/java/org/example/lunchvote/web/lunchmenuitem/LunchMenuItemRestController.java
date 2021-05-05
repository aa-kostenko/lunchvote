package org.example.lunchvote.web.lunchmenuitem;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.repository.LunchMenuItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = LunchMenuItemRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class LunchMenuItemRestController {
    static final String REST_URL = "/rest/lunchmenuitems";

    private final LunchMenuItemRepository repository;

    public LunchMenuItemRestController(LunchMenuItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<LunchMenuItem> getAllOnCurrentDate(@PathVariable int restaurantId) {
        return repository.getAllOnCurrentDate(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LunchMenuItem> createWithLocation(@Validated @RequestBody LunchMenuItem lunchMenuItem) {
        LunchMenuItem created = repository.save(lunchMenuItem);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody LunchMenuItem lunchMenuItem, @PathVariable int id) {
        repository.save(lunchMenuItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        repository.delete(repository.getOne(id));
    }
}
