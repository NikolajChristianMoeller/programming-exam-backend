package org.example.programmeringseksamenbackend.track;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.discipline.DisciplineDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TrackDTO {
    private Long id;
    @Enumerated(EnumType.STRING)
    private TrackType trackType;
    @Enumerated(EnumType.STRING)
    private TrackShape trackShape;
    @Enumerated(EnumType.STRING)
    private TrackSurface trackSurface;
    @Enumerated(EnumType.STRING)
    private TrackLength trackLength;
    private String lanes;
    private List<DisciplineDTO> disciplines;
}
