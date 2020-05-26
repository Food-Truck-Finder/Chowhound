package com.chowhound.chowhound.models;

import com.chowhound.chowhound.ChowhoundApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
public class ImageTests {

    @Test
    public void testImageGettersAndSetters() {

        Image testImage = new Image();

        int id = 1;
        String testPath = "https://chowhound.test.com";
        User testUser = new User();
        Truck testTruck = new Truck();

        testImage.setId(id);
        testImage.setPath(testPath);
        testImage.setPrimary(true);
        testImage.setTruck(testTruck);
        testImage.setUser(testUser);

        assertEquals(id, testImage.getId());
        assertEquals(testPath, testImage.getPath());
        assertTrue(testImage.isPrimary());
        assertNotNull(testImage.getUser());
        assertNotNull(testImage.getTruck());
    }
}
