package org.example.programmeringseksamenbackend.event;

import org.example.programmeringseksamenbackend.discipline.Discipline;
import org.example.programmeringseksamenbackend.discipline.DisciplineRepository;
import org.example.programmeringseksamenbackend.errorhandling.exception.NotFoundException;
import org.example.programmeringseksamenbackend.timeslot.TimeSlot;
import org.example.programmeringseksamenbackend.timeslot.TimeSlotRepository;
import org.example.programmeringseksamenbackend.track.Track;
import org.example.programmeringseksamenbackend.track.TrackRepository;
import org.example.programmeringseksamenbackend.participant.Participant;
import org.example.programmeringseksamenbackend.participant.ParticipantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final DisciplineRepository disciplineRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final TrackRepository trackRepository;
    private final ParticipantRepository participantRepository;

    public EventService(EventRepository eventRepository, DisciplineRepository disciplineRepository,
                        TimeSlotRepository timeSlotRepository, TrackRepository trackRepository,
                        ParticipantRepository participantRepository) {
        this.eventRepository = eventRepository;
        this.disciplineRepository = disciplineRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.trackRepository = trackRepository;
        this.participantRepository = participantRepository;
    }

    private EventDTO toDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setEventName(event.getEventName());
        eventDTO.setMinimumDuration(event.getMinimumDuration());
        eventDTO.setMaximumParticipants(event.getMaximumParticipants());

        if (event.getDiscipline() != null) {
            eventDTO.setDisciplineId(event.getDiscipline().getId());
        }
        if (event.getTimeSlot() != null) {
            eventDTO.setTimeSlotId(event.getTimeSlot().getId());
        }
        if (event.getTrack() != null) {
            eventDTO.setTrackId(event.getTrack().getId());
        }
        if (event.getParticipant() != null) {
            eventDTO.setParticipantId(event.getParticipant().getId());
        }

        return eventDTO;
    }

    private Event toEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setEventName(eventDTO.getEventName());
        event.setMinimumDuration(eventDTO.getMinimumDuration());
        event.setMaximumParticipants(eventDTO.getMaximumParticipants());

        if (eventDTO.getDisciplineId() != null) {
            Discipline discipline = disciplineRepository.findById(eventDTO.getDisciplineId())
                    .orElseThrow(() -> new NotFoundException("Discipline not found with id: " + eventDTO.getDisciplineId()));
            event.setDiscipline(discipline);
        }
        if (eventDTO.getTrackId() != null) {
            Track track = trackRepository.findById(eventDTO.getTrackId())
                    .orElseThrow(() -> new NotFoundException("Track not found with id: " + eventDTO.getTrackId()));
            event.setTrack(track);
        }
        if (eventDTO.getTimeSlotId() != null) {
            TimeSlot timeSlot = timeSlotRepository.findById(eventDTO.getTimeSlotId())
                    .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + eventDTO.getTimeSlotId()));
            event.setTimeSlot(timeSlot);
        }
        if (eventDTO.getParticipantId() != null) {
            Participant participant = participantRepository.findById(eventDTO.getParticipantId())
                    .orElseThrow(() -> new NotFoundException("Participant not found with id: " + eventDTO.getParticipantId()));
            event.setParticipant(participant);
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

        return eventOptional.map(this::toDTO);
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Track track = trackRepository.findById(eventDTO.getTrackId())
                .orElseThrow(() -> new NotFoundException("Track not found"));
        Discipline discipline = disciplineRepository.findById(eventDTO.getDisciplineId())
                .orElseThrow(() -> new NotFoundException("Discipline not found"));

        if (!track.getDisciplines().contains(discipline)) {
            throw new IllegalArgumentException("Discipline is not associated with the track");
        }

        Event event = toEntity(eventDTO);
        return toDTO(eventRepository.save(event));
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        return eventRepository.findById(id).map(event -> {
            event.setEventName(eventDTO.getEventName());
            event.setMinimumDuration(eventDTO.getMinimumDuration());
            event.setMaximumParticipants(eventDTO.getMaximumParticipants());

            if (eventDTO.getDisciplineId() != null) {
                Discipline discipline = disciplineRepository.findById(eventDTO.getDisciplineId())
                        .orElseThrow(() -> new NotFoundException("Discipline not found with id: " + eventDTO.getDisciplineId()));
                event.setDiscipline(discipline);
            }
            if (eventDTO.getTrackId() != null) {
                Track track = trackRepository.findById(eventDTO.getTrackId())
                        .orElseThrow(() -> new NotFoundException("Track not found with id: " + eventDTO.getTrackId()));
                event.setTrack(track);
            }
            if (eventDTO.getTimeSlotId() != null) {
                TimeSlot timeSlot = timeSlotRepository.findById(eventDTO.getTimeSlotId())
                        .orElseThrow(() -> new NotFoundException("TimeSlot not found with id: " + eventDTO.getTimeSlotId()));
                event.setTimeSlot(timeSlot);
            }
            if (eventDTO.getParticipantId() != null) {
                Participant participant = participantRepository.findById(eventDTO.getParticipantId())
                        .orElseThrow(() -> new NotFoundException("Participant not found with id: " + eventDTO.getParticipantId()));
                event.setParticipant(participant);
            }

            return toDTO(eventRepository.save(event));
        }).orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
    }

    public EventDTO deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Event not found with id: " + id));
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

    public EventDTO assignTrackToEvent(Long eventId, Long trackId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found with ID: " + eventId));
        Track track = trackRepository.findById(trackId)
                .orElseThrow(() -> new NotFoundException("Track not found with ID: " + trackId));

        if (event.getDiscipline() != null && !track.getDisciplines().contains(event.getDiscipline())) {
            throw new IllegalArgumentException("Track is not suitable for the event's discipline.");
        }

        event.setTrack(track);
        Event updatedEvent = eventRepository.save(event);
        return toDTO(updatedEvent);
    }
}
