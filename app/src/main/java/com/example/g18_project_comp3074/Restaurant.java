package com.example.g18_project_comp3074;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private String name;
    private String address;
    private String phone;
    private String notes;
    private String tags;

    public Restaurant(String name, String address, String phone, String notes, String tags) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.notes = notes;
        this.tags = tags;
    }

    public String getName() { return name; }

    public String getAddress() { return address; }

    public String getPhone() { return phone; }

    public String getNotes() { return notes; }

    public String getTags() { return tags; }
}
