package com.boostmytool.StudentManagement.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.persistence.*;
import java.text.ParseException;

@Entity
@Table(name = "lecturers", uniqueConstraints = {@UniqueConstraint(columnNames = "Fullname")})
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LecturerID")
    private int lecturerId;

    @Column(name = "Fullname", unique = true)
    private String fullname;

    @Column(name = "Email")
    private String email;

    @Column(name = "DOB")
    private Date dob;

    @Column(name = "Address")
    private String address;

    @Column(name = "Skill")
    private String skill;

    @Column(name = "Profile")
    private String profile;

    @Column(name = "Password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "LeaveID", referencedColumnName = "LeaveID")
    private LeaveRequest leave;

    @Column(name = "ApproveType")
    private String approveType;

    public int getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(int lecturerId) {
        this.lecturerId = lecturerId;
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

    public Date getDob() {
        return dob;
    }

    // Modified setDob method to accept a String argument and parse it to Date
    public void setDob(String dobString) {
        try {
            // Parse the string representation of the date to a Date object
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            this.dob = dateFormat.parse(dobString);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the exception if parsing fails
        }
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LeaveRequest getLeave() {
        return leave;
    }

    public void setLeave(LeaveRequest leave) {
        this.leave = leave;
    }

    public String getApproveType() {
        return approveType;
    }

    public void setApproveType(String approveType) {
        this.approveType = approveType;
    }
}
