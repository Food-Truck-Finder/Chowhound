package com.chowhound.chowhound.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "images")
public class Image {
    //setting up the primary key for users table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //image path
    @Column(nullable = false, unique =true)
    private String path;

    //relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    Truck truck;

    //boolean for primary image set by truck owner
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isPrimary;

    @Column(nullable = false)
    private Date datestamp;

    public Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Truck getTruck() {
        return truck;
    }

    public void setTruck(Truck truck) {
        this.truck = truck;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }
}
