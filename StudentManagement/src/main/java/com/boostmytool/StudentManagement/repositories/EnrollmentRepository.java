package com.boostmytool.StudentManagement.repositories;
import com.boostmytool.StudentManagement.models.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

}
