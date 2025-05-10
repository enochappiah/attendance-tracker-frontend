package com.example.att_tracker.config;

import com.example.att_tracker.Event.EventType;
import com.example.att_tracker.Event.EventTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

  private final EventTypeRepository eventTypeRepository;

  @Override
  public void run(String... args) {
    if (eventTypeRepository.count() == 0) {
      eventTypeRepository.save(new EventType(null, "Workshop", true, 4));
//      eventTypeRepository.save(new EventType("Seminar", true, null));
//      eventTypeRepository.save(new EventType("Meeting", true, null));
    }
  }
}
