package com.example.att_tracker.Event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
  List<Event> findByType(EventType type);
  List<Event> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
  List<Event> findByTypeAndDateBetween(EventType type, LocalDateTime startDate, LocalDateTime endDate);

}
