package com.example.att_tracker.Points;

import com.example.att_tracker.Event.Event;
import com.example.att_tracker.Event.EventRepository;
import com.example.att_tracker.User.User;
import com.example.att_tracker.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/points")
@RequiredArgsConstructor
public class PointEntryController {
  private final UserRepository userRepository;
  private final EventRepository eventRepository;
  private final PointEntryRepository pointEntryRepository;

  // 1. Mark a user as attended an event
  @PostMapping("/attend")
  public ResponseEntity<String> attendEvent(
      @RequestBody Map<String, Long> payload) {
    Long userId = payload.get("userId");
    Long eventId = payload.get("eventId");

    Optional<User> userOpt = userRepository.findById(userId);
    Optional<Event> eventOpt = eventRepository.findById(eventId);

    if (userOpt.isEmpty() || eventOpt.isEmpty()) {
      return ResponseEntity.badRequest().body("Invalid user or event ID.");
    }

    User user = userOpt.get();
    Event event = eventOpt.get();

    PointEntry entry = new PointEntry();
    entry.setUser(user);
    entry.setEvent(event);
    entry.setPointsEarned(event.getPointValue());
    entry.setEarnedAt(LocalDateTime.now());

    pointEntryRepository.save(entry);

    return ResponseEntity.ok("Attendance recorded, points added.");
  }
  // 2. Get total points for a user
  @GetMapping("/total/{userId}")
  public int getTotalPoints(@PathVariable Long userId) {
    return pointEntryRepository.sumPointsByUserId(userId);
  }

  // 3. Get required points for a user
  @GetMapping("/required/{userId}")
  public int getRequiredPoints(@PathVariable Long userId) {
    return pointEntryRepository.sumRequiredPointsByUserId(userId);
  }
}
