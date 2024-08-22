package org.example.programmeringseksamenbackend.event;

import org.example.programmeringseksamenbackend.discipline.Discipline;
import org.example.programmeringseksamenbackend.discipline.DisciplineDTO;
import org.example.programmeringseksamenbackend.discipline.DisciplineRepository;
import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.example.programmeringseksamenbackend.timeslot.TimeSlot;
import org.example.programmeringseksamenbackend.timeslot.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    EventRepository eventRepository;
    DisciplineRepository disciplineRepository;
    TimeSlotRepository timeSlotRepository;

    public EventService(EventRepository eventRepository, DisciplineRepository disciplineRepository, TimeSlotRepository timeSlotRepository) {
        this.eventRepository = eventRepository;
        this.disciplineRepository = disciplineRepository;
        this.timeSlotRepository = timeSlotRepository;
    }

    private EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setMinimumDuration(event.getMinimumDuration());
        eventDTO.setMaximumParticipants(event.getMaximumParticipants());
        return eventDTO;
    }

    private Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setMinimumDuration(eventDTO.getMinimumDuration());
        event.setMaximumParticipants(eventDTO.getMaximumParticipants());

        if (eventDTO.getDiscipline() != null && eventDTO.getDiscipline().getId() != null) {
            Discipline discipline = disciplineRepository.findById(eventDTO.getDiscipline().getId())
                    .orElseThrow(() -> new NotFoundException("Discipline not found with id: " + eventDTO.getDiscipline().getId()));
            event.setDiscipline(discipline);
        }
        return event;
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<EventDTO> getEventById(Long id) {
        if (id == null || id < 0) {
            throw new NotFoundException("Id must be provided");
        }

        Optional<Event> eventOptional = eventRepository.findById(id);

        if (eventOptional.isEmpty()) {
            throw new NotFoundException("Event not found, provided id: " + id);
        }

        return eventOptional.map(this::toDTO);
    }

    /*public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        return toDTO(eventRepository.save(event));
    }*/

    public EventDTO createEvent(EventDTO eventDTO) {
        // Check if an event with the same details already exists
        Optional<Event> existingEvent = eventRepository.findByEventNameAndMinimumDurationAndDisciplineId(
                eventDTO.getEventName(), eventDTO.getMinimumDuration(), eventDTO.getDiscipline() != null ? eventDTO.getDiscipline().getId() : null);

        if (existingEvent.isPresent()) {
            // Handle the case where the event already exists
            throw new RuntimeException("Event with the same details already exists");
        }

        Event event = toEntity(eventDTO);
        Event savedEvent = eventRepository.save(event);
        return toDTO(savedEvent);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id).map(event -> {
            event.setEventName(eventDTO.getEventName());
            event.setMinimumDuration(eventDTO.getMinimumDuration());
            event.setMaximumParticipants(eventDTO.getMaximumParticipants());
            if (eventDTO.getDiscipline() != null && eventDTO.getDiscipline().getId() != null) {
                Discipline discipline = disciplineRepository.findById(eventDTO.getDiscipline().getId())
                        .orElseThrow(() -> new NotFoundException("Discipline not found with id: " + eventDTO.getDiscipline().getId()));
                event.setDiscipline(discipline);
            }
            return toDTO(eventRepository.save(event));
        }).orElse(null);
    }

    public EventDTO deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found, provided id: " + id));
        EventDTO eventDTO = toDTO(event);
        eventRepository.deleteById(id);
        return eventDTO;
    }

    public EventDTO assignTimeSlotToEvent(Long eventId, Long timeSlotId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + timeSlotId));

        event.setTimeSlot(timeSlot);
        Event updatedEvent = eventRepository.save(event);
        return toDTO(updatedEvent);
    }

    public EventDTO removeTimeSlotFromEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + eventId));

        event.setTimeSlot(null);
        Event updatedEvent = eventRepository.save(event);
        return toDTO(updatedEvent);
    }

}
