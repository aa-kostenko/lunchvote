package org.example.lunchvote.web.restaurant;

import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.repository.RestaurantRepository;
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
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    static final String REST_URL = "/rest/restaurants";

    private final RestaurantRepository repository;

    public RestaurantRestController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found restaurant with id " + id));
    }

    @GetMapping("hasMenuToday")
    public List<Restaurant> getAllHasMenuToday(){
        return repository.geAllHasMenuBetween(LocalDate.now(), LocalDate.now());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Validated @RequestBody Restaurant restaurant) {
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Validated @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        checkSingleModification(repository.delete(id), "Restaurant id=" + id + " missed");
    }

}
