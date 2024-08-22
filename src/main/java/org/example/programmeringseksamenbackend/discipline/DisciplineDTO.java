package org.example.programmeringseksamenbackend.discipline;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DisciplineDTO {
    private Long id;
    private String disciplineName;
    private String approxDuration;
    private int numberOfParticipants;

    public DisciplineDTO(String disciplineName, String approxDuration) {
        this.disciplineName = disciplineName;
        this.approxDuration = approxDuration;
    }
}
