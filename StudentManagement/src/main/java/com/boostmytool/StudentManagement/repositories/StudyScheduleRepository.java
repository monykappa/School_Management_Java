package com.boostmytool.StudentManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.boostmytool.StudentManagement.models.StudySchedule;

import java.util.Optional;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Integer> {

    Optional<StudySchedule> findByStartTimeAndEndTime(String startTime, String endTime);

}
