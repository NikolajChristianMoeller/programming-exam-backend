package org.example.programmeringseksamenbackend.timeslot;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.event.Event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToMany(mappedBy = "timeSlot", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events = new ArrayList<>();
}

