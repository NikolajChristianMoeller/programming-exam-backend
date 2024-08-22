package org.example.programmeringseksamenbackend.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return ResponseEntity.of(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent(@RequestBody EventDTO eventDTO) {
        EventDTO createdEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(201).body(createdEvent);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        EventDTO updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> deleteEvent(@PathVariable Long id) {
        EventDTO deletedEvent = eventService.deleteEvent(id);
        return ResponseEntity.ok(deletedEvent);
    }
}

