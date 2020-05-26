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
import org.thymeleaf.util.ListUtils;

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

    //mapping to show form to add a truck
     @GetMapping("/trucks/create")
    public String registerTruck(Model model){
        model.addAttribute("truck",new Truck());
        model.addAttribute("cuisineOptions", cuisineRepo.findAllByIsPrimaryIsTrue());
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

    //mapping for searching through posts
    @GetMapping("/trucks/search")
    public String searchForPosts(@RequestParam(name = "searchTerm") String searchTerm, Model model){
        List<Truck> filteredTrucks = truckRepo.findAllByNameContainingOrDescriptionContaining(searchTerm,searchTerm);
        model.addAttribute("trucks", filteredTrucks);
        return "index";
    }

    //mapping to show a single truck
    @GetMapping("trucks/{id}")
    public String truckById(@ModelAttribute Truck truck, Model model) {
        model.addAttribute("truck",truckRepo.getOne(truck.getId()));
        return "trucks/show";
    }

    //mapping to show list of user's favorite trucks
    @GetMapping("trucks/my_favorites")
    public String showUsersFavoriteTrucks(Model model){
        List<Truck> usersFavs = truckRepo.findAllByFavoritedUsersEquals((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        model.addAttribute("trucks", usersFavs);
        return "index";
    }

    //mapping to add a truck to user's favorite list
    @PostMapping("trucks/my_favorites")
    public String addNewFavoriteTruck(@ModelAttribute Truck truck, Model model){
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Truck> trucksToAdd = new ArrayList<>();
        trucksToAdd.add(truck);
        loggedInUser.setFavoriteTrucks(trucksToAdd);
        ListUtils.toList(truck);
        model.addAttribute("favmsg", "Truck added to your favorites");
        return "redirect:/trucks/" + truck.getId();
    }
}
