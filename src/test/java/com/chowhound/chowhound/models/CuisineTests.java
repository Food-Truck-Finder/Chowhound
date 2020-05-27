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
public class CuisineTests {

    @Test
    public void testCuisineGettersAndSetters() {
        Cuisine testCuisine = new Cuisine();

        int id = 1;
        String description = "test cuisine";
        List<Truck> trucksWithTestCuisine = new ArrayList<>();

        testCuisine.setId(id);
        testCuisine.setCategory(description);
        testCuisine.setIsPrimary(true);
        testCuisine.setTrucks(trucksWithTestCuisine);

        assertEquals(id,testCuisine.getId());
        assertEquals(description, testCuisine.getCategory());
        assertTrue(testCuisine.isIsPrimary());
        assertEquals(0, testCuisine.getTrucks().size());
    }
}
