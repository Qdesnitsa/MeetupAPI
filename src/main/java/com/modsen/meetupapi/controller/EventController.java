package com.modsen.meetupapi.controller;

import com.modsen.meetupapi.entity.Event;
import com.modsen.meetupapi.service.BaseService;
import com.modsen.meetupapi.util.AppConstant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/events")
public class EventController {
    BaseService baseService;

    public EventController(@Qualifier("eventBaseService") BaseService baseService) {
        this.baseService = baseService;
    }

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(
            @RequestParam(value = "sortBy", defaultValue = AppConstant.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstant.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "filterBy", defaultValue = AppConstant.DEFAULT_FILTER, required = false) String filterBy,
            @RequestParam(value = "filterValue", defaultValue = AppConstant.DEFAULT_FILTER_VALUE, required = false) String filterValue
    ) {
        List<Event> events = baseService.getAllEntities(sortBy, sortDir, filterBy, filterValue);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event targetEvent = (Event) baseService.getEntityById(id).get();
        return new ResponseEntity<>(targetEvent, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Event> create(@RequestBody @Valid Event event) {
        baseService.createOrUpdateEntity(event).get();
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Event> update(@RequestBody @Valid Event event) {
        baseService.createOrUpdateEntity(event).get();
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Event> delete(@PathVariable Long id) {
        baseService.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
