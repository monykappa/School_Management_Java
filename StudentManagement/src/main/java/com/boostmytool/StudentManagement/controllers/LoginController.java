package com.boostmytool.StudentManagement.controllers;

import com.boostmytool.StudentManagement.models.LoginForm;
import com.boostmytool.StudentManagement.models.Admin;
import com.boostmytool.StudentManagement.models.Student;
import com.boostmytool.StudentManagement.models.Lecture;
import com.boostmytool.StudentManagement.repositories.AdminRepository;
import com.boostmytool.StudentManagement.repositories.StudentRepository;
import com.boostmytool.StudentManagement.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;





@RestController
public class LoginController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private LectureRepository lectureRepository;



    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody LoginForm loginForm) {
        String username = loginForm.getUsername(); 
        String password = loginForm.getPassword();
        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        if (admin != null) {
            return "admin";
        }
    Student student = studentRepository.findByFullnameAndPassword(username, password);
    if (student != null) {
        return "student";
    }   
    Lecture lecture = lectureRepository.findByFullnameAndPassword(username, password);
    if (lecture != null) {
        return "lecture";
    }
    return "invalid";
}
}
