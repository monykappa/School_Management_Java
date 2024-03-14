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
            // Save study schedule
            StudySchedule studyTime = enrollment.getStudyTime();
            studyScheduleRepository.save(studyTime);

            // Check if course is associated with enrollment
            Course course = enrollment.getCourse();
            if (course != null && course.getId() != null) {
                // Fetch the course from the database
                course = courseRepository.findById(course.getId()).orElse(null);
                if (course == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Course with ID " + enrollment.getCourse().getId() + " not found.");
                }
                // Set the fetched course to enrollment
                enrollment.setCourse(course);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Invalid course provided.");
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
