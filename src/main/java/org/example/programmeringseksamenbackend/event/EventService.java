package org.example.programmeringseksamenbackend.event;

import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
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
        Event event = toEntity(eventDTO);
        return toDTO(eventRepository.save(event));
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id).map(event -> {
            event.setEventName(eventDTO.getEventName());
            event.setMinimumDuration(eventDTO.getMinimumDuration());
            event.setMaximumParticipants(eventDTO.getMaximumParticipants());
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

}
