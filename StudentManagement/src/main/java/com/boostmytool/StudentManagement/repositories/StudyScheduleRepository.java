package com.boostmytool.StudentManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boostmytool.StudentManagement.models.StudySchedule;

public interface StudyScheduleRepository extends JpaRepository<StudySchedule, Integer> {
}
