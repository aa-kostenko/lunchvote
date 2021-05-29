package org.example.lunchvote.web.lunchmenuitem;

import org.example.lunchvote.model.LunchMenuItem;
import org.example.lunchvote.model.Restaurant;
import org.example.lunchvote.model.Vote;
import org.example.lunchvote.repository.LunchMenuItemRepository;
import org.example.lunchvote.repository.RestaurantRepository;
import org.example.lunchvote.to.LunchMenuItemTo;
import org.example.lunchvote.util.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindException;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
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

    private final RestaurantRepository restaurantRepository;

    @Autowired
    @Qualifier("defaultValidator")
    private Validator validator;

    public LunchMenuItemRestController(LunchMenuItemRepository repository, RestaurantRepository restaurantRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
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
    public ResponseEntity<LunchMenuItem> createWithLocation(@Validated @RequestBody LunchMenuItemTo lunchMenuItemTo) throws BindException {

        Restaurant restaurant = restaurantRepository
                .findById(lunchMenuItemTo.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id " + lunchMenuItemTo.getRestaurantId()));

        LunchMenuItem lunchMenuItem = new LunchMenuItem();
        lunchMenuItem.setRestaurant(restaurant);
        lunchMenuItem.setMenuDate(lunchMenuItemTo.getMenuDate());
        lunchMenuItem.setName(lunchMenuItemTo.getName());
        lunchMenuItem.setPrice(lunchMenuItemTo.getPrice());

        validateLunchMenuItem(lunchMenuItem);

        LunchMenuItem created = repository.save(lunchMenuItem);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void update(@Validated @RequestBody LunchMenuItemTo lunchMenuItemTo, @PathVariable int id) throws BindException {
        assureIdConsistent(lunchMenuItemTo, id);
        LunchMenuItem lunchMenuItem = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Not found LunchMenuItem with id " + id ));

        Restaurant restaurant = restaurantRepository
                .findById(lunchMenuItemTo.getRestaurantId())
                .orElseThrow(() -> new NotFoundException("Not found restaurant with id " + lunchMenuItemTo.getRestaurantId()));

        lunchMenuItem.setRestaurant(restaurant);
        lunchMenuItem.setMenuDate(lunchMenuItemTo.getMenuDate());
        lunchMenuItem.setName(lunchMenuItemTo.getName());
        lunchMenuItem.setPrice(lunchMenuItemTo.getPrice());

        validateLunchMenuItem(lunchMenuItem);

        repository.save(lunchMenuItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable int id) {
        checkSingleModification(repository.delete(id), "LunchMenuItem id=" + id + " missed");
    }

    protected void validateLunchMenuItem(LunchMenuItem lunchMenuItem) throws BindException {
        DataBinder binder = new DataBinder(lunchMenuItem);
        binder.addValidators(validator);
        binder.validate();
        if (binder.getBindingResult().hasErrors()) {
            throw new BindException(binder.getBindingResult());
        }
    }
}
