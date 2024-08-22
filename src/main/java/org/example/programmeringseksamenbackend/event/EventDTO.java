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
    private String eventName;
    private String minimumDuration;
    private String participantGender;
    private String participantAgeGroup;
    private String maximumParticipants;
    private DisciplineDTO discipline;
    private TimeSlotDTO timeSlot;
    private TrackDTO track;
    private ParticipantDTO participant;
}
