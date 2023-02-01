package com.example.inclass03;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String email;
    int ID;
    String department;

    public User() {
        name = null;
        email = null;
        ID = -1;
        department = null;
    }

    public User(String name, String email, int ID, String department) {
        this.name = name;
        this.email = email;
        this.ID = ID;
        this.department = department;
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

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
