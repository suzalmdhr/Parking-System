package com.test.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class floor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;

    private String name;

    private String Address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "floors")
    private List<Slot> slots = new ArrayList<>();

    public floor() {
        super();
    }

    private int numberOfSlots;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public floor(Long id, String name, String address, List<Slot> slots, int numberOfSlots) {
        this.id = id;
        this.name = name;
        Address = address;
        this.slots = slots;
        this.numberOfSlots = numberOfSlots;
    }

    @Override
    public String toString() {
        return "floor [id=" + id + ", name=" + name + ", Address=" + Address + ", numberOfSlots=" + numberOfSlots + "]";
    }

}
