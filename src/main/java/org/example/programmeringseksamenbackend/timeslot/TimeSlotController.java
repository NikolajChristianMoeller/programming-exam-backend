package org.example.programmeringseksamenbackend.timeslot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/timeslots")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping
    public ResponseEntity<List<TimeSlotDTO>> getAllTimeSlot() {
        return ResponseEntity.ok(timeSlotService.getAllTimeSlots());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> getTimeSlotById(@PathVariable Long id) {
        return ResponseEntity.of(timeSlotService.getTimeSlotById(id));
    }

    @PostMapping
    public ResponseEntity<TimeSlotDTO> createTimeSlot(@RequestBody TimeSlotDTO timeSlotDTO) {
        return ResponseEntity.status(201).body(timeSlotService.createTimeSlot(timeSlotDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> updateTimeSlot(@PathVariable Long id, @RequestBody TimeSlotDTO timeSlotDTO) {
        return ResponseEntity.ok(timeSlotService.updateTimeSlot(id, timeSlotDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TimeSlotDTO> deleteTimeSlot(@PathVariable Long id) {
        return ResponseEntity.ok(timeSlotService.deleteTimeSlot(id));
    }

    @PostMapping("/{timeSlotId}/events/{eventId}")
    public ResponseEntity<TimeSlotDTO> addEventToTimeSlot(@PathVariable Long timeSlotId, @PathVariable Long eventId) {
        return ResponseEntity.ok(timeSlotService.addEventToTimeSlot(timeSlotId, eventId));
    }

    @DeleteMapping("/{timeSlotId}/events/{eventId}")
    public ResponseEntity<TimeSlotDTO> removeEventFromTimeSlot(@PathVariable Long timeSlotId, @PathVariable Long eventId) {
        return ResponseEntity.ok(timeSlotService.removeEventFromTimeSlot(timeSlotId, eventId));
    }
}

