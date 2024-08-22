package org.example.programmeringseksamenbackend.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventNameAndMinimumDurationAndDisciplineId(String eventName, String minimumDuration, Long aLong);
}
