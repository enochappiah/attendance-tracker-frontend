package com.example.att_tracker.Event;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
class EventController {
  private final EventRepository eventRepository;

  @PostMapping
  public ResponseEntity<Event> createEvent(@RequestBody Event event) {
    return ResponseEntity.ok(eventRepository.save(event));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Event> getEventById(@PathVariable Long id) {
    return eventRepository.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public ResponseEntity<List<Event>> getAllEvents() {
    return ResponseEntity.ok(eventRepository.findAll());
  }

  @PutMapping("/{id}")
  public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event updatedEvent) {
    return eventRepository.findById(id)
        .map(existingEvent -> {
          existingEvent.setTitle(updatedEvent.getTitle());
          existingEvent.setDate(updatedEvent.getDate());
          existingEvent.setType(updatedEvent.getType());
          return ResponseEntity.ok(eventRepository.save(existingEvent));
        })
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
    if (!eventRepository.existsById(id)) {
      return ResponseEntity.notFound().build();
    }
    eventRepository.deleteById(id);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/type/{type}")
  public ResponseEntity<List<Event>> getEventsByType(@PathVariable EventType type) {
    return ResponseEntity.ok(eventRepository.findByType(type));
  }

  @GetMapping("/dateRange")
  public ResponseEntity<List<Event>> getEventsByDateRange(
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
    return ResponseEntity.ok(eventRepository.findByDateBetween(startDate, endDate));
  }

  @GetMapping("/type/{type}/dateRange")
  public ResponseEntity<List<Event>> getEventsByTypeAndDateRange(
      @PathVariable EventType type,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
    return ResponseEntity.ok(eventRepository.findByTypeAndDateBetween(type, startDate, endDate));
  }


}
