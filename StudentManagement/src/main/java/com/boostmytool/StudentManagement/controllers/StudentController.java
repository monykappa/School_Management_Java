package com.boostmytool.StudentManagement.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.boostmytool.StudentManagement.repositories.StudentRepository;
import com.boostmytool.StudentManagement.models.Student;
import java.util.Optional;
import java.nio.file.Path;
import org.springframework.util.StringUtils;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/Student")
    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    @GetMapping("/studentss/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable int studentId) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(studentOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/saveStudent")
    @ResponseBody
    public ResponseEntity<String> saveStudent(@RequestParam("studentProfile") MultipartFile profile,
            @RequestParam("fullname") String fullname, @RequestParam("email") String email,
            @RequestParam("dob") String dob, @RequestParam("address") String address,
            @RequestParam("studentPhoneNumber") String studentPhoneNumber,
            @RequestParam("parentName") String parentName, @RequestParam("parentPhoneNumber") String parentPhoneNumber,
            @RequestParam("password") String password,
            @RequestParam("Subject") String Subject) {
        // Check if the student data is valid
        if (isStudentValid(fullname, email, dob, address, profile, studentPhoneNumber, parentName, parentPhoneNumber, password,Subject)) {
            try {
                // Save the profile image to the specified directory
                @SuppressWarnings("null")
                String fileName = StringUtils.cleanPath(profile.getOriginalFilename());
                Path uploadDir = Paths.get("StudentManagement/src/main/resources/static/img/students");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                try (InputStream inputStream = profile.getInputStream()) {
                    Files.copy(inputStream, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save profile image");
                }
    
                // Save student data to database
                Student student = new Student();
                student.setFullname(fullname);
                student.setEmail(email);
                student.setDob(dob);
                student.setAddress(address);
                student.setProfile(fileName); // Save file name to the database
                student.setStudentPhoneNumber(studentPhoneNumber);
                student.setParentName(parentName);
                student.setParentPhoneNumber(parentPhoneNumber);
                student.setPassword(password);
                student.setSubject(Subject);
    
                studentRepository.save(student);
                return ResponseEntity.ok("Student data saved successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save student data");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid student data. Please ensure all fields are filled.");
        }
    }
    
    // Define the isStudentValid method to validate student data
    private boolean isStudentValid(String fullname, String email, String dob, String address, MultipartFile profile,
            String studentPhoneNumber, String parentName, String parentPhoneNumber, String password,String Subject) {
        // Perform validation logic here
        // Ensure that none of the parameters are null or empty
        return fullname != null && !fullname.isEmpty() &&
                email != null && !email.isEmpty() &&
                dob != null && !dob.isEmpty() &&
                address != null && !address.isEmpty() &&
                profile != null && !profile.isEmpty() &&
                studentPhoneNumber != null && !studentPhoneNumber.isEmpty() &&
                parentName != null && !parentName.isEmpty() &&
                parentPhoneNumber != null && !parentPhoneNumber.isEmpty() &&
                password != null && !password.isEmpty() &&
                Subject != null && !Subject.isEmpty();
    }
    

    @DeleteMapping("/deleteStudent/{studentId}")
    public ResponseEntity<String> deleteStudent(@PathVariable int studentId) {
        try {
            studentRepository.deleteById(studentId);
            return ResponseEntity.ok("Student deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete student");
        }
    }

    @GetMapping("/studentsBySubject/{subject}")
    public List<Student> getStudentsBySubject(@PathVariable String subject) {
        return studentRepository.findBySubject(subject);
    }


    @PutMapping("/updateStudent/{studentId}")
    public ResponseEntity<String> updateStudent(@PathVariable int studentId, @RequestBody Student updatedStudent) {
        try {
            // Fetch the existing student record
            Optional<Student> existingStudentOptional = studentRepository.findById(studentId);

            if (existingStudentOptional.isPresent()) {
                Student existingStudent = existingStudentOptional.get();

                // Update the existing student record with the new data
                existingStudent.setFullname(updatedStudent.getFullname());
                existingStudent.setAddress(updatedStudent.getAddress());
                existingStudent.setDob(updatedStudent.getDob());
                existingStudent.setEmail(updatedStudent.getEmail());
                existingStudent.setParentName(updatedStudent.getParentName());
                existingStudent.setParentPhoneNumber(updatedStudent.getParentPhoneNumber());
                existingStudent.setPassword(updatedStudent.getPassword());
                existingStudent.setProfile(updatedStudent.getProfile());
                existingStudent.setStudentPhoneNumber(updatedStudent.getStudentPhoneNumber());
                existingStudent.setSubject(updatedStudent.getSubject());
                studentRepository.save(existingStudent);

                return ResponseEntity.ok("Student updated successfully");
            } else {
                return ResponseEntity.notFound().build(); // Student not found with the given ID
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update student");
        }
    }

}
