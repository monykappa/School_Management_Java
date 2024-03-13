package com.boostmytool.StudentManagement.repositories;

import com.boostmytool.StudentManagement.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
   
}
