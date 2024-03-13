package com.boostmytool.StudentManagement.repositories;

import com.boostmytool.StudentManagement.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByFullnameAndPassword(String fullname, String password);
    List<Student> findBySubject(String subject);
}
