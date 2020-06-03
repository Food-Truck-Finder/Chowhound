package com.chowhound.chowhound.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "trucks")
public class Truck {
    //setting up the primary key for users table
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private String hours;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String zipCode;

    @Column
    private String phoneNumber;

    @Column
    private String email;

    @Column(nullable = false)
    private double lat;

    @Column(nullable = false)
    private double lon;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVerified;

    @Column
    private String instagram;

    @Column
    private String facebook;

    @Column
    private String twitter;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // relationship for images
    @OneToMany(mappedBy = "truck")
    List<Image> images;

    //relationship fir reviews
    @OneToMany(mappedBy = "truck")
    List<Review> reviews;

    @ManyToMany(mappedBy = "favoriteTrucks")
    List<User> favoritedUsers;

    @ManyToMany
    @JoinTable(
            name = "truck_cuisines",
            joinColumns = @JoinColumn(name = "truck_id"),
            inverseJoinColumns = @JoinColumn(name = "cuisine_id")
    )
    private List<Cuisine> cuisines;

    public Truck() {
    }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }

    public String getZipCode() { return zipCode; }

    public void setZipCode(String zipCode) { this.zipCode = zipCode; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<User> getFavoritedUsers() {
        return favoritedUsers;
    }

    public void setFavoritedUsers(List<User> favoritedUsers) {
        this.favoritedUsers = favoritedUsers;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public double getAverageReviewRating(){
        return reviews.stream().mapToInt(Review::getStars).average().orElse(0);
    }

    public String getInstagram() { return instagram; }

    public void setInstagram(String instagram) { this.instagram = instagram; }

    public String getFacebook() { return facebook; }

    public void setFacebook(String facebook) { this.facebook = facebook; }

    public String getTwitter() { return twitter; }

    public void setTwitter(String twitter) { this.twitter = twitter; }
}
