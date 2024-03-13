package com.boostmytool.StudentManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.boostmytool.StudentManagement.models.Class;

public interface ClassRepository extends JpaRepository<Class, Long> {
   
}
