package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.Review;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.ReviewRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import java.sql.Date;
import java.util.Date;

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
    public String getReviewForm (
            @ModelAttribute Truck truck, @PathVariable ("id") Long truckId, Model model
    ) {
        truck = truckRepo.getOne(truckId);
        model.addAttribute("truck",truck);
        Review review = new Review();
        model.addAttribute("review", review);
        return "trucks/review";
    }


    //Mapping to post review page
    @PostMapping ("/reviews/{id}/addReview")
    public String postReview (
            @ModelAttribute Truck truck, @PathVariable ("id") Long truckId, Model model, @ModelAttribute Review review, @RequestParam ("stars") int stars

    ) {
        Date date = new Date();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long findUserId = user.getId();
        user=userRepo.findById(findUserId);
        truck = truckRepo.getOne((truckId));

        review.setDatestamp(new java.sql.Date(date.getTime()));
        review.setStars(stars);
        review.setTruck(truck);
        review.setUser(user);

        reviewRepo.save(review);

        return "redirect:/trucks/" + truck.getId();

    }

}

