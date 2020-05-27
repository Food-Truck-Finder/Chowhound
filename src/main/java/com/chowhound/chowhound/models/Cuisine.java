package com.chowhound.chowhound.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cuisines")
public class Cuisine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String category;

    @Column(columnDefinition = "boolean default false")
    private boolean isPrimary;

    @ManyToMany(mappedBy = "cuisines")
    private List<Truck> trucks;

    public Cuisine() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String description) {
        this.category = description;
    }

    public boolean isIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean primary) {
        primary = primary;
    }

    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
