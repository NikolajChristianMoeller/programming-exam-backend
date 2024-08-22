package org.example.programmeringseksamenbackend.discipline;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.participant.Participant;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Discipline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String disciplineName;
    private String approxDuration;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Participant> participants = new ArrayList<>();
}
