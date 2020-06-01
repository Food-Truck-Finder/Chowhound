package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.repos.ReviewRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
    @GetMapping ("/reviews/{id}/addReview")
    public String GetReviewForm (
            @ModelAttribute Truck truck, @PathVariable ("id") Long truckId, Model model
    ) {
        truck = truckRepo.getOne(truckId);
        return "trucks/review";
    }


    //Mapping to post review page
}

