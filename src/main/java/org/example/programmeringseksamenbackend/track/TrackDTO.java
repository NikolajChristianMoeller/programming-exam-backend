package org.example.programmeringseksamenbackend.track;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TrackDTO {
    private Long id;
    private String typeOfTrack;
    private String shapeType;
    private String surfaceType;
    private Double trackLength;
    private String lanes;
}
