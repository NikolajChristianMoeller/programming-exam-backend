package org.example.programmeringseksamenbackend.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Long disciplineId;
    private Long timeSlotId;
    private Long trackId;
    private Long participantId;

    public EventDTO(Long disciplineId, Long timeSlotId, Long trackId, Long participantId, String eventName, String minimumDuration, String participantGender, String participantAgeGroup, String maximumParticipants) {
        this.disciplineId = disciplineId;
        this.timeSlotId = timeSlotId;
        this.trackId = trackId;
        this.participantId = participantId;
        this.eventName = eventName;
        this.minimumDuration = minimumDuration;
        this.participantGender = participantGender;
        this.participantAgeGroup = participantAgeGroup;
        this.maximumParticipants = maximumParticipants;
    }

}
