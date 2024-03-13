package com.boostmytool.StudentManagement.repositories;

import com.boostmytool.StudentManagement.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
    Admin findByUsernameAndPassword(String username, String password); // Change method name
}
