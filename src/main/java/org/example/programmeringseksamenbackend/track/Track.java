package org.example.programmeringseksamenbackend.track;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.discipline.Discipline;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

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
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Discipline> disciplines = new ArrayList<>();

}
