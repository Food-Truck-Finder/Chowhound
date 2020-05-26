package com.chowhound.chowhound.models;

import com.chowhound.chowhound.ChowhoundApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
public class TruckTests {

    @Test
    public void testTruckGettersAndSetters() {
        Truck testTruck = new Truck();

        int id = 1;
        User testOwner = new User();
        String name = "testTruck", description = "test for truck model", address = "123 some rd, sometown, somestate, 12345", hours = "hours";
        List<Review> reviews = new ArrayList<>();
        List<User> favorites = new ArrayList<>();
        List<Image> images = new ArrayList<>();
        List<Cuisine> cuisines = new ArrayList<>();
        double lat = 10.000000, lon = 10.000000;

        testTruck.setId(id);
        testTruck.setName(name);
        testTruck.setDescription(description);
        testTruck.setAddress(address);
        testTruck.setHours(hours);
        testTruck.setReviews(reviews);
        testTruck.setUser(testOwner);
        testTruck.setImages(images);
        testTruck.setFavoritedUsers(favorites);
        testTruck.setCuisines(cuisines);
        testTruck.setVerified(true);
        testTruck.setLat(lat);
        testTruck.setLon(lon);

        assertEquals(1,testTruck.getId());
        assertEquals("testTruck", testTruck.getName());
        assertEquals("test for truck model", testTruck.getDescription());
        assertEquals("123 some rd, sometown, somestate, 12345", testTruck.getAddress());
        assertEquals("hours", testTruck.getHours());
        assertEquals(0,testTruck.getReviews().size());
        assertEquals(0, testTruck.getFavoritedUsers().size());
        assertEquals(0,testTruck.getImages().size());
        assertEquals(0,testTruck.getCuisines().size());
        assertNotNull(testTruck.getUser());
        assertEquals(10.000000, testTruck.getLat(),0);
        assertEquals(10.000000, testTruck.getLon(),0);
        assertTrue(testTruck.isVerified());

    }
}