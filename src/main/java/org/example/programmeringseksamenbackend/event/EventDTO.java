package org.example.programmeringseksamenbackend.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.discipline.DisciplineDTO;
import org.example.programmeringseksamenbackend.participant.ParticipantDTO;
import org.example.programmeringseksamenbackend.timeslot.TimeSlotDTO;
import org.example.programmeringseksamenbackend.track.TrackDTO;


@Getter
@Setter
@NoArgsConstructor
public class EventDTO {
    private Long id;
    private Long disciplineId;
    private Long timeSlotId;
    private Long trackId;
    private Long participantId;
    private String eventName;
    private String minimumDuration;
    private String participantGender;
    private String participantAgeGroup;
    private String maximumParticipants;
    private DisciplineDTO discipline;
    private TimeSlotDTO timeSlot;
    private TrackDTO track;
    private ParticipantDTO participant;

    public EventDTO(String eventName, String minimumDuration, String participantGender, String participantAgeGroup, String maximumParticipants) {
        this.eventName = eventName;
        this.minimumDuration = minimumDuration;
        this.participantGender = participantGender;
        this.participantAgeGroup = participantAgeGroup;
        this.maximumParticipants = maximumParticipants;
    }

}
