package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.repos.ReviewRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {
    private TruckRepo truckRepo;
    private UserRepo userRepo;
    private ReviewRepo reviewRepo;

    public ReviewController (
            TruckRepo truckRepo,
            UserRepo userRepo,
            ReviewRepo reviewRepo
    ) {
        this.truckRepo = truckRepo;
        this.userRepo = userRepo;
        this.reviewRepo = reviewRepo;
    }

    //Mapping to get review page
    @GetMapping ("/trucks/{id}/review")


    //Mapping to post review page
}

