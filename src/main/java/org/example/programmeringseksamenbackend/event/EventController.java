package org.example.programmeringseksamenbackend.event;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.getAllEvents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable Long id) {
        return  ResponseEntity.of(eventService.getEventById(id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> createEvent (@RequestBody EventDTO eventDTO) {
        return ResponseEntity.status(202).body(eventService.createEvent(eventDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> updateEvent (@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.updateEvent(id, eventDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EventDTO> deleteEvent (@PathVariable Long id) {
        return ResponseEntity.ok(eventService.deleteEvent(id));
    }
}
