package org.example.programmeringseksamenbackend.timeslot;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.example.programmeringseksamenbackend.event.Event;
import org.example.programmeringseksamenbackend.event.EventDTO;
import org.example.programmeringseksamenbackend.event.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TimeSlotService {
    TimeSlotRepository timeSlotRepository;
    EventRepository eventRepository;

    public TimeSlotService(TimeSlotRepository timeSlotRepository, EventRepository eventRepository) {
        this.timeSlotRepository = timeSlotRepository;
        this.eventRepository = eventRepository;
    }

    private TimeSlotDTO toDTO(TimeSlot timeSlot) {
        TimeSlotDTO timeSlotDTO = new TimeSlotDTO();
        timeSlotDTO.setId(timeSlot.getId());
        timeSlotDTO.setDate(timeSlot.getDate());
        timeSlotDTO.setStartTime(timeSlot.getStartTime());
        timeSlotDTO.setEndTime(timeSlot.getEndTime());
        timeSlotDTO.setEvents(timeSlot.getEvents().stream()
                .map(this::eventToDTO) // You need to implement this method
                .collect(Collectors.toList()));
        return timeSlotDTO;
    }

    private TimeSlot toEntity(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = new TimeSlot();
        timeSlot.setId(timeSlotDTO.getId());
        timeSlot.setDate(timeSlotDTO.getDate());
        timeSlot.setStartTime(timeSlotDTO.getStartTime());
        timeSlot.setEndTime(timeSlotDTO.getEndTime());
        return timeSlot;
    }

    public List<TimeSlotDTO> getAllTimeSlots() {
        return timeSlotRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<TimeSlotDTO> getTimeSlotById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<TimeSlot> timeSlotOptional = timeSlotRepository.findById(id);

        if (timeSlotOptional.isEmpty()) {
            throw new NotFoundException("TimeSlot not found, provided id: " + id);
        }

        return timeSlotOptional.map(this::toDTO);
    }

    /*public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = new TimeSlot();
        return toDTO(timeSlotRepository.save(timeSlot));
    }*/

    public TimeSlotDTO createTimeSlot(TimeSlotDTO timeSlotDTO) {
        TimeSlot timeSlot = toEntity(timeSlotDTO);
        return toDTO(timeSlotRepository.save(timeSlot));
    }

    public TimeSlotDTO updateTimeSlot(Long id, TimeSlotDTO timeSlotDTO) {
        return timeSlotRepository.findById(id).map(timeSlot -> {
            timeSlot.setDate(timeSlotDTO.getDate());
            timeSlot.setStartTime(timeSlotDTO.getStartTime());
            timeSlot.setEndTime(timeSlotDTO.getEndTime());
            return toDTO(timeSlotRepository.save(timeSlot));
        }).orElse(null);
    }

    public TimeSlotDTO deleteTimeSlot(Long id) {
        TimeSlot timeSlot = timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Time slot not found, provided id: " + id));
        TimeSlotDTO timeSlotDTO = toDTO(timeSlot);
        timeSlotRepository.deleteById(id);
        return timeSlotDTO;
    }

    public TimeSlotDTO addEventToTimeSlot(Long timeSlotId, Long eventId) {
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + timeSlotId));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        if (event.getTimeSlot() != null) {
            throw new RuntimeException("Event is already assigned to a different time slot.");
        }

        timeSlot.getEvents().add(event);
        event.setTimeSlot(timeSlot);
        timeSlotRepository.save(timeSlot);
        eventRepository.save(event);

        return toDTO(timeSlot);
    }

    public TimeSlotDTO removeEventFromTimeSlot(Long timeSlotId, Long eventId) {
        // Find the time slot
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + timeSlotId));

        // Find the event
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        // Check if the event is actually associated with the time slot
        if (event.getTimeSlot() == null || !event.getTimeSlot().getId().equals(timeSlotId)) {
            throw new RuntimeException("Event is not associated with this time slot.");
        }

        // Remove the event from the time slot
        timeSlot.getEvents().remove(event);
        event.setTimeSlot(null); // Clear the association

        // Save changes to both time slot and event
        timeSlotRepository.save(timeSlot);
        eventRepository.save(event);

        return toDTO(timeSlot);
    }

    private EventDTO eventToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setMinimumDuration(event.getMinimumDuration());
        eventDTO.setMaximumParticipants(event.getMaximumParticipants());
        // Set other properties if needed
        return eventDTO;
    }
}
