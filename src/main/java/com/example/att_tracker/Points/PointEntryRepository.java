package com.example.att_tracker.Points;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PointEntryRepository extends JpaRepository<PointEntry, Long> {

  @Query("SELECT COALESCE(SUM(p.pointsEarned), 0) FROM PointEntry p WHERE p.user.id = :userId")
  int sumPointsByUserId(@Param("userId") Long userId);

  @Query("""
        SELECT COALESCE(SUM(p.pointsEarned), 0) 
        FROM PointEntry p 
        WHERE p.user.id = :userId 
        AND p.event.type.required = true
    """)
  int sumRequiredPointsByUserId(@Param("userId") Long userId);
}
