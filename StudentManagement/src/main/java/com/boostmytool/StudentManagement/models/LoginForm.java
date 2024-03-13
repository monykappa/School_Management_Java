package com.boostmytool.StudentManagement.models;

public class LoginForm {
    private String fullname; 
    private String password;
    private String Username; // Change 'Username' to 'username'

    public String getFullname() {
        return fullname;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
