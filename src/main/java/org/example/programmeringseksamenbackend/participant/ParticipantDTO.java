package org.example.programmeringseksamenbackend.participant;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class ParticipantDTO {
    private Long id;
    private Long disciplineId;
    private String fullName;
    private String participantNumber;
    private Gender gender;
    private AgeGroup ageGroup;

    public ParticipantDTO(Long disciplineId, String participantNumber) {
        this.disciplineId = disciplineId;
        this.participantNumber = participantNumber;
    }
}
