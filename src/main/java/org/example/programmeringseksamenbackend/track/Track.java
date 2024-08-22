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
    private String typeOfTrack;
    private String shapeType;
    private String surfaceType;
    private Double trackLength;
    private String lanes;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Discipline> disciplines = new ArrayList<>();

}
