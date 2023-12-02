package com.example.cscb07_project;


public class Users {
    String name, email;
    Boolean adminAccess;

    public Users(){
    }

    public Users(String name, String email, Boolean adminAccess) {
        this.name = name;
        this.email = email;
        this.adminAccess = adminAccess;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Boolean getAdminAccess() {
        return adminAccess;
    }

    public void setAdminAccess(Boolean adminAccess) {
        this.adminAccess = adminAccess;
    }
}
