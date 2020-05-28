package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.Cuisine;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.CuisineRepo;
import com.chowhound.chowhound.repos.ImageRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import com.chowhound.chowhound.services.SortTrucksService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.*;

@Controller
public class TruckController {
    private TruckRepo truckRepo;
    private UserRepo userRepo;
    private ImageRepo imageRepo;
    private CuisineRepo cuisineRepo;
    private SortTrucksService sortTrucksService;

    public TruckController(TruckRepo truckRepo, UserRepo userRepo, ImageRepo imageRepo, CuisineRepo cuisineRepo, SortTrucksService sortTrucksService) {
        this.truckRepo = truckRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.cuisineRepo = cuisineRepo;
        this.sortTrucksService = sortTrucksService;
    }

    //mapping for index page
    @GetMapping("/index")
    public String sortTrucks(Model model, @RequestParam(defaultValue = "") String sortType, @RequestParam(defaultValue = "") String searchTerm) {

        List<Truck> trucks = truckRepo.findAllBySearchTerm(searchTerm);


        if (!searchTerm.equals("")) {
            model.addAttribute("searchTerm", searchTerm);
        }

        trucks =  sortTrucksService.sortTrucks(trucks,sortType);

        model.addAttribute("trucks", trucks);
        return "index";
    }

    //mapping for searching through trucks
    @GetMapping("/trucks/search")
    public String searchForTrucks(@RequestParam(name = "searchTerm",defaultValue = "") String searchTerm, @RequestParam(defaultValue = "") String sortType, Model model) {


        List<Truck> combinedResults = truckRepo.findAllBySearchTerm(searchTerm);

        combinedResults =  sortTrucksService.sortTrucks(combinedResults,sortType);
        model.addAttribute("searchTerm", searchTerm);
        model.addAttribute("trucks", combinedResults);
        model.addAttribute("sortType", sortType);
        return "index";
    }

    //mapping to show form to add a truck
    @GetMapping("/trucks/create")
    public String registerTruck(Model model) {
        model.addAttribute("truck", new Truck());
        model.addAttribute("cuisineOptions", cuisineRepo.findAllByIsPrimaryIsTrue());
        return "trucks/create";
    }

    @PostMapping("/trucks/create")
    public String submitTruckRegistration(@ModelAttribute Truck truck, @RequestParam List<Cuisine> cuisines, @RequestParam boolean owner, @RequestParam String newCuisine, Model model) {
        Cuisine newCustomCuisine = null;

        if (!newCuisine.equals("")) {
            try {
                newCustomCuisine = cuisineRepo.findCuisineByCategoryContaining(newCuisine);
                cuisines.add(newCustomCuisine);
//                System.out.println(newCustomCuisine.getDescription());
            } catch (Exception e) {
                newCustomCuisine = new Cuisine();
                newCustomCuisine.setCategory(newCuisine);
                newCustomCuisine = cuisineRepo.save(newCustomCuisine);
                cuisines.add(newCustomCuisine);
//                System.out.println(newCustomCuisine.getDescription());
            }
        }
//        System.out.println(newCustomCuisine.getDescription());
        if (owner) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            truck.setUser(user);
        }
        cuisines.toString();

        truck.setCuisines(cuisines);
        truck = this.truckRepo.save(truck);
        model.addAttribute("truck", truck);
        return "redirect:/index";
    }

    //mapping to show a single truck
    @GetMapping("/trucks/{id}")
    public String truckById(@ModelAttribute Truck truck, Model model) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
        } catch (Exception e) {
            System.out.println("No User logged in");
        }
        model.addAttribute("truck", truckRepo.getOne(truck.getId()));

        return "trucks/show";
    }

    //mapping to show list of user's favorite trucks
    @GetMapping("/trucks/my_favorites")
    public String showUsersFavoriteTrucks(Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Truck> usersFavs = truckRepo.findAllByFavoritedUsersEquals(loggedInUser);

        for (Truck fav : usersFavs){
            System.out.println(fav.getName());
        }
//        System.out.println(usersFavs);
        model.addAttribute("trucks", usersFavs);
        return "index";
    }

    //mapping to add a truck to user's favorite list
    @PostMapping("/trucks/my_favorites")
    public String addNewFavoriteTruck(@ModelAttribute Truck truck, Model model) {
        User loggedInUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Truck> trucksToAdd = new ArrayList<>();
        trucksToAdd.add(truck);
        loggedInUser.setFavoriteTrucks(trucksToAdd);
        ListUtils.toList(truck);
        model.addAttribute("favMsg", "Truck added to your favorites");
        return "redirect:/trucks/" + truck.getId();
    }


}
