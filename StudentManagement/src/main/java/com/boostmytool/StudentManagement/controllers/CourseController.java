package com.boostmytool.StudentManagement.controllers;

import com.boostmytool.StudentManagement.models.Course;
import com.boostmytool.StudentManagement.repositories.CourseRepository;
// import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import java.util.List;


@RestController
public class CourseController<models> {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("/save-course")
    public String saveCourse(@RequestBody Course course) {
        if (course != null) {
            courseRepository.save(course); // Save the course to the database
            return "Course saved successfully!";
        } else {
            return "Course object is null!";
        }
    }
    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
    @DeleteMapping("/delete-course/{id}")
    public String deleteCourse(@NonNull @PathVariable Long id) {
        try {
            courseRepository.deleteById(id);
            return "Course deleted successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while deleting the course.";
        }
    }

    @PostMapping("/update-course/{id}")
    public ResponseEntity<String> updateCourse(@NonNull @PathVariable Long id, @RequestBody Course updatedCourse) {
    try {
        Course existingCourse = courseRepository.findById(id).orElse(null);
        if (existingCourse == null) {
            return ResponseEntity.notFound().build();
        }
        existingCourse.setCourseName(updatedCourse.getCourseName()); 
        courseRepository.save(existingCourse); 
        return ResponseEntity.ok("Course updated successfully!");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the course.");
    }
}
}
