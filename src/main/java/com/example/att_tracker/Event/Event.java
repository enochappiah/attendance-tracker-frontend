package com.example.att_tracker.Event;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Event {
  @Id
  @GeneratedValue
  private Long id;
  private String title;
  private LocalDateTime date;

  @ManyToOne(optional = false)
  private EventType type;

  @Nullable
  private Integer customPointValue; //nullable for overriding

  // Helper method to get effective point value
  public Integer getPointValue() {
    return customPointValue != null ? customPointValue : type.getDefaultPointValue();
  }
}
