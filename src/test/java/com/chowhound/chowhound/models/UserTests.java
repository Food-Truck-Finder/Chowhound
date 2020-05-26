package com.chowhound.chowhound.models;

import com.chowhound.chowhound.ChowhoundApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
public class UserTests {

    @Test
    public void testUserGettersAndSetters() {
        int id = 1;
        String username = "testUser", email = "user@test.com", password = "pass";
        List<Review> reviews = new ArrayList<>();
        List<Truck> favorites = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        double currentLat = 10.000000, currentLon = 10.000000;

        User testUser = new User();
        testUser.setId(id);
        testUser.setUsername(username);
        testUser.setEmail(email);
        testUser.setPassword(password);
        testUser.setReviews(reviews);
        testUser.setOwner(true);
        testUser.setAdmin(true);
        testUser.setImages(images);
        testUser.setFavoriteTrucks(favorites);
        testUser.setCurrentLat(currentLat);
        testUser.setCurrentLon(currentLon);

        assertEquals(1,testUser.getId());
        assertEquals("testUser", testUser.getUsername());
        assertEquals("user@test.com", testUser.getEmail());
        assertEquals("pass", testUser.getPassword());
        assertEquals(0,testUser.getReviews().size());
        assertEquals(0,testUser.getFavoriteTrucks().size());
        assertEquals(0,testUser.getImages().size());
        assertTrue(testUser.isOwner());
        assertTrue(testUser.isAdmin());
        assertEquals(10.000000, testUser.getCurrentLat(),0);
        assertEquals(10.000000, testUser.getCurrentLon(),0);

    }
}
