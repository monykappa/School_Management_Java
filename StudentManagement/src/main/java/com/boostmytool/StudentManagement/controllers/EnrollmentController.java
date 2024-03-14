package com.boostmytool.StudentManagement.controllers;

import com.boostmytool.StudentManagement.models.Enrollment;
import com.boostmytool.StudentManagement.models.StudySchedule;
import com.boostmytool.StudentManagement.models.Course;
import com.boostmytool.StudentManagement.repositories.EnrollmentRepository;
import com.boostmytool.StudentManagement.repositories.CourseRepository;
import com.boostmytool.StudentManagement.repositories.StudyScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Optional;

@RestController
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final StudyScheduleRepository studyScheduleRepository;
    private final CourseRepository courseRepository;

    public EnrollmentController(EnrollmentRepository enrollmentRepository,
            StudyScheduleRepository studyScheduleRepository,
            CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studyScheduleRepository = studyScheduleRepository;
        this.courseRepository = courseRepository;
    }

    @PostMapping
    public ResponseEntity<String> createEnrollment(@RequestBody Enrollment enrollment) {
        try {
            // Ensure that the study schedule is not null
            StudySchedule studyTime = enrollment.getStudyTime();
            if (studyTime == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Study schedule cannot be null.");
            }
    
            // Check if the study schedule already exists in the database
            StudySchedule existingStudySchedule = studyScheduleRepository
                    .findByStartTimeAndEndTime(studyTime.getStartTime(), studyTime.getEndTime())
                    .orElse(null);
    
            // If the study schedule does not exist, save it
            if (existingStudySchedule == null) {
                existingStudySchedule = studyScheduleRepository.save(studyTime);
            }
    
            // Set the study schedule in the enrollment
            enrollment.setStudyTime(existingStudySchedule);
    
            // Check if course is associated with enrollment
            Course course = enrollment.getCourse();
            if (course == null || course.getId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Course ID cannot be null.");
            }
    
            // If the course is not already set, fetch it from the database
            if (enrollment.getCourse() == null) {
                course = courseRepository.findById(course.getId()).orElse(null);
                if (course == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Course with ID " + course.getId() + " not found.");
                }
                // Set the fetched course to enrollment
                enrollment.setCourse(course);
            }
    
            // Save enrollment
            enrollmentRepository.save(enrollment);
    
            // Return a success response
            return ResponseEntity.ok("Enrollment created successfully");
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
    
            // Return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating enrollment: " + e.getMessage());
        }
    }

}
