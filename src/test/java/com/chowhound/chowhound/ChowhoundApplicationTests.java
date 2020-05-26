package com.chowhound.chowhound;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChowhoundApplication.class)
@AutoConfigureMockMvc
public class ChowhoundApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void contextLoads(){
        // Sanity Test, just to make sure the MVC bean is working
        assertNotNull(mvc);
    }

}
