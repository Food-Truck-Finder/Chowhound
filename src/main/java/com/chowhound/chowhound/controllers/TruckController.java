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
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ListUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TruckController {
    private TruckRepo truckRepo;
    private UserRepo userRepo;
    private ImageRepo imageRepo;
    private CuisineRepo cuisineRepo;

    public TruckController(TruckRepo truckRepo, UserRepo userRepo, ImageRepo imageRepo, CuisineRepo cuisineRepo) {
        this.truckRepo = truckRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.cuisineRepo = cuisineRepo;
    }

    //mapping for index page
    @GetMapping("/index")
    public String sortTrucks(Model model, @RequestParam(defaultValue = "") String sortType) {

        List<Truck> trucks = truckRepo.findAll();
        switch (sortType) {
            case "rating_a":
                trucks.sort(Comparator.comparing(Truck::getAverageReviewRating));
                break;
            case "rating_d":
                trucks.sort(Comparator.comparing(Truck::getAverageReviewRating));
                Collections.reverse(trucks);
                break;
            case "name_a":
                trucks.sort((Truck t1, Truck t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
                break;
            case "name_d":
                trucks.sort((Truck t1, Truck t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
                Collections.reverse(trucks);
                break;
        }

        model.addAttribute("trucks", trucks);
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

    //mapping for searching through posts
    @GetMapping("/trucks/search")
    public String searchForPosts(@RequestParam(name = "searchTerm") String searchTerm, Model model) {
//        List<Truck> filterByName = truckRepo.findAllByNameContainingOrDescriptionContainingOrCuisines_CategoryContaining(searchTerm, searchTerm, searchTerm);
//        List<Truck> filterByNameOrDescription = truckRepo.findAllByNameContainingOrDescriptionContaining(searchTerm, searchTerm);
//        List<Truck> filterByCuisine = truckRepo.findAllByCuisinesContaining(cuisineRepo.findAllByCategoryContaining(searchTerm));
        List<Truck> combinedResults = truckRepo.findAllBySearchTerm(searchTerm);
        model.addAttribute("trucks", combinedResults);
        return "index";
    }

    //mapping to show a single truck
    @GetMapping("/trucks/{id}")
    public String truckById(@ModelAttribute Truck truck, Model model) {
        model.addAttribute("truck", truckRepo.getOne(truck.getId()));
        return "trucks/show";
    }

    //mapping to show list of user's favorite trucks
    @GetMapping("/trucks/my_favorites")
    public String showUsersFavoriteTrucks(Model model) {
        List<Truck> usersFavs = truckRepo.findAllByFavoritedUsersEquals((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
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
        model.addAttribute("favmsg", "Truck added to your favorites");
        return "redirect:/trucks/" + truck.getId();
    }


}
