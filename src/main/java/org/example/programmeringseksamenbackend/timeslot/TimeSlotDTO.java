package org.example.programmeringseksamenbackend.timeslot;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.programmeringseksamenbackend.event.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class TimeSlotDTO {
    private Long id;
    private LocalDateTime date;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<EventDTO> events;
}
