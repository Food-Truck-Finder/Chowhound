package com.chowhound.chowhound.models;

import com.chowhound.chowhound.ChowhoundApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
public class ReviewTests {

    @Test
    public void testReviewGettersAndSetters(){
        Review testReview = new Review();

        int id = 1, testStars = 5;
        String testReviewText = "test review text";
        User testUser = new User();
        Truck testTruck = new Truck();

        testReview.setId(id);
        testReview.setStars(testStars);
        testReview.setReviewText(testReviewText);
        testReview.setUser(testUser);
        testReview.setTruck(testTruck);

        assertEquals(id,testReview.getId());
        assertEquals(testStars,testReview.getStars());
        assertEquals(testReviewText,testReview.getReviewText());
        assertNotNull(testReview.getUser());
        assertNotNull(testReview.getTruck());
    }
}
