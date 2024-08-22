package org.example.programmeringseksamenbackend.event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.discipline.Discipline;
import org.example.programmeringseksamenbackend.participant.Participant;
import org.example.programmeringseksamenbackend.timeslot.TimeSlot;
import org.example.programmeringseksamenbackend.track.Track;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String eventName;
    private String minimumDuration;
    private String maximumParticipants;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_slot_id")  // Add this annotation to specify the foreign key column
    private TimeSlot timeSlot;
    @ManyToOne(fetch = FetchType.EAGER)
    private Track track;
    @ManyToOne(fetch = FetchType.EAGER)
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.EAGER)
    private Participant participant;
}
