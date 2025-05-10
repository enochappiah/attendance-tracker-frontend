package com.example.att_tracker.Attendance;


import com.example.att_tracker.User.UserRepository;
import com.example.att_tracker.Points.PointEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
class AttendanceController {
  private final UserRepository userRepository;
  private final PointEntryRepository pointEntryRepository;


  @GetMapping("/summary/{userId}")
  public ResponseEntity<Map<String, Object>> getSummary(@PathVariable Long userId) {
    return userRepository.findById(userId)
        .map(user -> {
          int total = pointEntryRepository.sumPointsByUserId(userId);
          int required = pointEntryRepository.sumRequiredPointsByUserId(userId);

          Map<String, Object> summary = Map.of(
              "userName", user.getName(),
              "totalPoints", total,
              "requiredPoints", required
          );

          return ResponseEntity.ok(summary);
        })
        .orElseGet(() -> ResponseEntity.notFound().build());
  }
}
