package com.boostmytool.StudentManagement.controllers;

import com.boostmytool.StudentManagement.models.Lecture;
// import com.boostmytool.StudentManagement.models.Student;
import com.boostmytool.StudentManagement.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
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


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.nio.file.Path;

@RestController
public class LectureController {

    @Autowired
    private final LectureRepository lectureRepository;

    public LectureController(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @GetMapping("/getAllLectures")
    public List<Lecture> getAllLectures() {
        return lectureRepository.findAll();
    }

    @GetMapping("/getLectureProfileImage/{lectureId}")
    public ResponseEntity<Resource> getLectureProfileImage(@PathVariable int lectureId) {
        // Retrieve lecture profile image based on lecture ID
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        if (lectureOptional.isPresent()) {
            Lecture lecture = lectureOptional.get();
            try {
                Path imagePath = Paths
                        .get("StudentManagement/src/main/resources/static/img/lectures/" + lecture.getProfile());
                Resource resource = new UrlResource(imagePath.toUri());
                if (resource.exists() && resource.isReadable()) {
                    return ResponseEntity.ok().body(resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getLectureById/{lectureId}")
    public ResponseEntity<Lecture> getLectureById(@PathVariable int lectureId) {
        Optional<Lecture> lectureOptional = lectureRepository.findById(lectureId);
        if (lectureOptional.isPresent()) {
            Lecture lecture = lectureOptional.get();
            return ResponseEntity.ok(lecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/saveLecture")
    @ResponseBody
    public ResponseEntity<String> saveLecture(@RequestParam("profile") MultipartFile profile,
            @RequestParam("fullname") String fullname, @RequestParam("email") String email,
            @RequestParam("dob") String dob, @RequestParam("address") String address,
            @RequestParam("password") String password, @RequestParam("skill") String skill) {
        // Check if the lecture data is valid
        if (isLectureValid(fullname, email, dob, address, profile, password, skill)) {
            try {
                // Save the profile image to the specified directory
                @SuppressWarnings("null")
                String fileName = StringUtils.cleanPath(profile.getOriginalFilename());
                Path uploadDir = Paths.get("StudentManagement/src/main/resources/static/img/lectures");
                if (!Files.exists(uploadDir)) {
                    Files.createDirectories(uploadDir);
                }
                try (InputStream inputStream = profile.getInputStream()) {
                    Files.copy(inputStream, uploadDir.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save profile image");
                }

                // Save lecture data to database
                Lecture lecture = new Lecture();
                lecture.setFullname(fullname);
                lecture.setEmail(email);
                lecture.setDob(dob);
                lecture.setAddress(address);
                lecture.setProfile(fileName); // Save file name to the database
                lecture.setPassword(password);
                lecture.setSkill(skill);

                lectureRepository.save(lecture);
                return ResponseEntity.ok("Lecture data saved successfully");
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save lecture data");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid lecture data. Please ensure all fields are filled.");
        }
    }

    // Define the isLectureValid method to validate lecture data
    private boolean isLectureValid(String fullname, String email, String dob, String address, MultipartFile profile,
            String password, String skill) {
        // Perform validation logic here
        // Ensure that none of the parameters are null or empty
        return fullname != null && !fullname.isEmpty() &&
                email != null && !email.isEmpty() &&
                dob != null && !dob.isEmpty() &&
                address != null && !address.isEmpty() &&
                profile != null && !profile.isEmpty() &&
                password != null && !password.isEmpty() &&
                skill != null && !skill.isEmpty();
    }

    @GetMapping("/lecturesBySkill/{skill}")
    public ResponseEntity<List<Lecture>> getLecturesBySkill(@PathVariable String skill) {
        List<Lecture> lectures = lectureRepository.findBySkill(skill);
        if (lectures != null && !lectures.isEmpty()) {
            return ResponseEntity.ok(lectures);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/deletelecture/{lectureid}")
    public ResponseEntity<String> deleteLecture(@PathVariable int lectureid) {
        try {
            lectureRepository.deleteById(lectureid);
            return ResponseEntity.ok("Lecture deleted successfully");
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete student");
        }
    }


    @PutMapping("/updateLecture/{lectureId}")
    public ResponseEntity<String> updateLecture(@PathVariable int lectureId, @RequestBody Lecture updatedLecture) {
        try {
            Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);
            // Check if the lecture exists
            if (optionalLecture.isPresent()) {
                Lecture existingLecture = optionalLecture.get();

                // Update the existing lecture with the data from the updatedLecture object
                existingLecture.setFullname(updatedLecture.getFullname());
                existingLecture.setEmail(updatedLecture.getEmail());
                existingLecture.setAddress(updatedLecture.getAddress());
                existingLecture.setDob(updatedLecture.getDob());
                existingLecture.setPassword(updatedLecture.getPassword());
                existingLecture.setSkill(updatedLecture.getSkill());

                lectureRepository.save(existingLecture);

                return ResponseEntity.ok("Lecture updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update lecture");
        }
    }



    @GetMapping("/getLectureByFullname/{fullname}")
    public ResponseEntity<Lecture> getLectureByFullname(@PathVariable String fullname) {
        Optional<Lecture> lectureOptional = lectureRepository.findByFullname(fullname);
        if (lectureOptional.isPresent()) {
            Lecture lecture = lectureOptional.get();
            return ResponseEntity.ok(lecture);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
    


}
