package com.chowhound.chowhound.models;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "reviews")
public class Review {
    //setting up the primary key for users table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //rating
    @Column
    private Integer stars;

    @Column
    private String reviewText;

    //relationships
    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "truck_id")
    Truck truck;

    @Column
    private Date datestamp;

    public Review() {
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public int getStars() { return stars; }

    public void setStars(int stars) { this.stars = stars; }

    public String getReviewText() { return reviewText; }

    public void setReviewText(String review) { this.reviewText = review; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Truck getTruck() { return truck; }

    public void setTruck(Truck truck) { this.truck = truck; }
}
