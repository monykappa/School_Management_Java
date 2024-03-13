package com.boostmytool.StudentManagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boostmytool.StudentManagement.models.StudySchedule;
import com.boostmytool.StudentManagement.repositories.StudyScheduleRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class StudyScheduleController {

    private final StudyScheduleRepository studyScheduleRepository;

    public StudyScheduleController(StudyScheduleRepository studyScheduleRepository) {
        this.studyScheduleRepository = studyScheduleRepository;
    }

    @PutMapping("/update-study-schedule/{timeId}")
    public ResponseEntity<String> updateStudySchedule(@PathVariable int timeId, @RequestBody StudySchedule updatedSchedule) {
        try {
            // Retrieve the existing study schedule by its timeId
            Optional<StudySchedule> existingScheduleOptional = studyScheduleRepository.findById(timeId);
            if (existingScheduleOptional.isPresent()) {
                // Update the existing study schedule with the new data
                StudySchedule existingSchedule = existingScheduleOptional.get();
                existingSchedule.setDayOfWeek(updatedSchedule.getDayOfWeek());
                existingSchedule.setStartTime(updatedSchedule.getStartTime());
                existingSchedule.setEndTime(updatedSchedule.getEndTime());
                
                // Save the updated study schedule
                studyScheduleRepository.save(existingSchedule);

                return ResponseEntity.ok("Study schedule updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating study schedule: " + e.getMessage());
        }
    }

    @GetMapping("/get-study-schedules")
    public List<StudySchedule> getStudySchedules() {
        return studyScheduleRepository.findAll();
    }

    @DeleteMapping("/delete-study-schedule/{id}")
    public ResponseEntity<String> deleteStudySchedule(@PathVariable int id) {
        try {
            studyScheduleRepository.deleteById(id);
            return ResponseEntity.ok("Study schedule deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting study schedule.");
        }
    }
    
}
