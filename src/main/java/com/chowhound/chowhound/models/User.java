package com.chowhound.chowhound.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    //setting up the primary key for users table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    //username
    @Column(nullable = false, unique =true)
    private String username;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    //email
    @Column(nullable = false)
    private String email;

    //password
    @Column(nullable = false)
    private String password;

    // admin rights
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isAdmin;

    // boolean for truck owner
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isOwner;

    //current location lat
    @Column
    private double currentLat;

    //current location lon
    @Column
    private double currentLon;

    // relationship for images
    @OneToMany(mappedBy = "user")
    private List<Image> images;

    //relationship for reviews
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

    // relationship for favorites
    @ManyToMany
    @JoinTable(
            name = "favorites",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "truck_id")
    )
    private List<Truck> favoriteTrucks;

    // constructors,getters,and setters
    public User(){
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
    }

//    public User(User copy) {
//        id = copy.id;
//        username = copy.username;
//        firstName = copy.firstName;
//        lastName = copy.lastName;
//        email = copy.email;
//        password = copy.password;
//        isAdmin = copy.isAdmin;
//        isOwner = copy.isOwner;
//        currentLat = copy.currentLat;
//        currentLon = copy.currentLon;
//        images = copy.images;
//        reviews = copy.reviews;
//        favoriteTrucks = copy.favoriteTrucks;
//    }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Truck> getFavoriteTrucks() {
        return favoriteTrucks;
    }

    public void setFavoriteTrucks(List<Truck> favoriteTrucks) {
        this.favoriteTrucks = favoriteTrucks;
    }
}
