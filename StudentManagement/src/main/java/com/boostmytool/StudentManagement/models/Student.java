package com.boostmytool.StudentManagement.models;

import jakarta.persistence.*;

@Entity
@Table(name = "students", uniqueConstraints = {@UniqueConstraint(columnNames = "Fullname")})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StudentID")
    private int studentId;

    @Column(name = "Fullname", unique = true)
    private String fullname;

    @Column(name = "Email")
    private String email;

    @Column(name = "DOB")
    private String dob;

    @Column(name = "Address")
    private String address;

    @Column(name = "Profile")
    private String profile;

    @Column(name = "StudentPhoneNumber")
    private String studentPhoneNumber;

    @Column(name = "ParentName")
    private String parentName;

    @Column(name = "ParentPhoneNumber")
    private String parentPhoneNumber;

    @Column(name = "Password")
    private String password;

    @Column(name = "isEnrollment")
    private Boolean isEnrollment;

    @Column(name = "Subject")
    private String subject;

    public Student() {
        
    }
    

    public Student(int studentId, String fullname, String email, String dob, String address, String profile,
    String studentPhoneNumber, String parentName, String parentPhoneNumber, String password,String Subject, boolean isEnrollment) {
    this.studentId = studentId;
    this.fullname = fullname;
    this.email = email;
    this.dob = dob;
    this.address = address;
    this.profile = profile;
    this.studentPhoneNumber = studentPhoneNumber;
    this.parentName = parentName;
    this.parentPhoneNumber = parentPhoneNumber;
    this.password = password;
    this.subject=Subject;
    this.isEnrollment = false;
    }   

    public int getStudentId() {
        return studentId;
    }

    public Boolean getIsEnrollment() {
        return isEnrollment;
    }


    public void setIsEnrollment(Boolean isEnrollment) {
        this.isEnrollment = isEnrollment;
    }


    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfile() {
        return profile;
    }



    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getStudentPhoneNumber() {
        return studentPhoneNumber;
    }

    public void setStudentPhoneNumber(String studentPhoneNumber) {
        this.studentPhoneNumber = studentPhoneNumber;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
