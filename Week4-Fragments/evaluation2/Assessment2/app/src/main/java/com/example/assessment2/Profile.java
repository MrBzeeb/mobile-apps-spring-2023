package com.example.assessment2;

import java.io.Serializable;

public class Profile implements Serializable {
    Double weight;
    String gender;

    public Profile() {
        weight = null;
        gender = null;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
