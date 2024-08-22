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
}
