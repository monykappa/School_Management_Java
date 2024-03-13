package com.boostmytool.StudentManagement.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.boostmytool.StudentManagement.models.Class;
import com.boostmytool.StudentManagement.repositories.ClassRepository;

import java.util.List;

@RestController
public class ClassController {
    private final ClassRepository classRepository;

    public ClassController(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }
    
    @GetMapping("/classes")
    public List<Class> getClasses() {
        return classRepository.findAll();
    }

    @PostMapping("/save-class")
    public String saveClass(@RequestBody Class classObj) {
        if (classObj != null) {
            classRepository.save(classObj); // Save the class to the database
            return "Class saved successfully!";
        } else {
            return "Class object is null!";
        }
    }

    @DeleteMapping("/delete-class/{id}")
    public ResponseEntity<String> deleteClass(@PathVariable Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().body("Class ID cannot be null");
        }
        try {
            classRepository.deleteById(id);
            return ResponseEntity.ok("Class deleted successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the class.");
        }
    }
    
}

