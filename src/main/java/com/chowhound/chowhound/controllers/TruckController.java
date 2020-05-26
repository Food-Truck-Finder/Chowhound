package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.Cuisine;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.CuisineRepo;
import com.chowhound.chowhound.repos.ImageRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TruckController {
    private TruckRepo truckRepo;
    private UserRepo userRepo;
    private ImageRepo imageRepo;
    private CuisineRepo cuisineRepo;

    public TruckController( TruckRepo truckRepo, UserRepo userRepo, ImageRepo imageRepo, CuisineRepo cuisineRepo){
        this.truckRepo = truckRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.cuisineRepo = cuisineRepo;
    }

    //mapping for index page

    @GetMapping("/index")
    public String index(Model model) {
        List<Truck> trucks = truckRepo.findAll();
        Collections.reverse(trucks);
        model.addAttribute("trucks", trucks);
        return "index";
    }

     @GetMapping("/trucks/create")
    public String registerTruck(Model model){
        model.addAttribute("truck",new Truck());
        model.addAttribute("options", cuisineRepo.findAllByIsPrimaryIsTrue());
        return "trucks/create";
     }

     @PostMapping("/trucks/create")
    public String submitTruckRegistration(@ModelAttribute Truck truck, @RequestParam List<Cuisine> cuisines, @RequestParam boolean owner, Model model){
//         User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (user == null){
//            return "redirect:/login";
//        } else
         if (owner){
             try {
                 User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                 truck.setUser(user);
             } catch (Exception e) {
                 return "redirect:/login";
             }
         }
        truck.setCuisines(cuisines);
        System.out.println(cuisines.toString());
        truck = this.truckRepo.save(truck);
        model.addAttribute("truck", truck);
        return "redirect:/index";
     }
}
