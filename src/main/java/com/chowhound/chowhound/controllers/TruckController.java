package com.chowhound.chowhound.controllers;

import com.chowhound.chowhound.models.Cuisine;
import com.chowhound.chowhound.models.Image;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.CuisineRepo;
import com.chowhound.chowhound.repos.ImageRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import com.chowhound.chowhound.services.SortTrucksService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @GetMapping({"/index", "/"})
    public String sortTrucks(Model model, @RequestParam(defaultValue = "") String sortType, @RequestParam(defaultValue = "") String searchTerm) {
        User user;
        List<Truck> favorites = new ArrayList<>();

        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            user = null;
        }
        if (user != null) {
            user = userRepo.getOne(user.getId());
            favorites = user.getFavoriteTrucks();
        }

        List<Truck> trucks = truckRepo.findAllBySearchTerm(searchTerm);
        if (!searchTerm.equals("")) {
            model.addAttribute("searchTerm", searchTerm);
        }

        trucks = sortTrucksService.sortTrucks(trucks, sortType);

        model.addAttribute("favorites", favorites);
        model.addAttribute("trucks", trucks);
        return "index";
    }

    //mapping for searching through trucks
    @GetMapping("/trucks/search")
    public String searchForTrucks(@RequestParam(name = "searchTerm", defaultValue = "") String searchTerm, @RequestParam(defaultValue = "") String sortType, Model model) {


        List<Truck> combinedResults = truckRepo.findAllBySearchTerm(searchTerm);


        combinedResults = sortTrucksService.sortTrucks(combinedResults, sortType);
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
    public String submitTruckRegistration(
            @ModelAttribute Truck truck,
            @RequestParam List<Cuisine> cuisines,
            @RequestParam boolean ownerCheckbox,
            @RequestParam String newCuisine,
            @RequestParam(value = "imageURL") String imageUrl,
            Model model) {

        Image newTruckImage = new Image();
        List<Image> images = new ArrayList<>();

        Cuisine newCustomCuisine = null;
        if (!newCuisine.equals("")) {
            try {
                newCustomCuisine = cuisineRepo.findCuisineByCategoryContaining(newCuisine);
                cuisines.add(newCustomCuisine);
            } catch (Exception e) {
                newCustomCuisine = new Cuisine();
                newCustomCuisine.setCategory(newCuisine);
                newCustomCuisine = cuisineRepo.save(newCustomCuisine);
                cuisines.add(newCustomCuisine);
            }
        }
        if (ownerCheckbox) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            user = userRepo.getOne(user.getId());
            truck.setUser(user);
        }

        if (truck.getImages() != null){
            images = truck.getImages();
            int primary = -1;
            for (Image image: images){
                if (image.isPrimary()){
                    primary = images.indexOf(image);
                }
            }
            if (primary != -1) {
                images.remove(primary);
            }
        }

//        if (imageUrl.equals("")) {
//            newTruckImage.setPath("https://user-images.githubusercontent.com/13071055/45196982-c7bd6100-b213-11e8-90c9-8c9cdee8717f.png");
//        } else {
//            newTruckImage.setPath(imageUrl);
//        }


        User user =(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userRepo.getOne(user.getId());

        Date date = new Date();
        newTruckImage.setPath(imageUrl);
        newTruckImage.setPrimary(true);
        newTruckImage.setUser(user);
        newTruckImage.setTruck(truck);
        newTruckImage.setDatestamp(new java.sql.Date(date.getTime()));
        images.add(newTruckImage);

        truck.setDateAdded(new java.sql.Date(date.getTime()));
        truck.setImages(images);
//        cuisines.toString();
        truck.setCuisines(cuisines);
        truck = this.truckRepo.save(truck);
        if (!newTruckImage.getPath().equals("")) {
            imageRepo.save(newTruckImage);
        }
        model.addAttribute("truck", truck);
        return "redirect:/index";
    }

    //mapping to show a single truck
    @GetMapping("/trucks/{id}")
    public String truckById(@ModelAttribute Truck truck, Model model) {

        truck = truckRepo.getOne(truck.getId());

        List<Truck> favTrucks = new ArrayList<>();
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            model.addAttribute("user", user);
            favTrucks = truckRepo.findAllByFavoritedUsersEquals(user);
            System.out.println(favTrucks.contains(truck));

            model.addAttribute("isFav", favTrucks.contains(truck));

            if (truck.getUser() == null) {
                User tempUser = new User();
                truck.setUser(tempUser);
            }
        } catch (Exception e) {
            System.out.println("No User logged in");
        }
        model.addAttribute("truck", truck);

        return "trucks/show";
    }

    //post mapping for viewing a single truck to add an image
    @PostMapping("/trucks/{id}/saveImg")
    public String addImage(@PathVariable("id") long truckId, @ModelAttribute Truck truck, Model model, @RequestParam("imageURL") String imageUrl) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        user = userRepo.findById(user.getId());

        List<Image> truckImages = new ArrayList<>();

        Date date = new Date();
        Image newImage = new Image();
        newImage.setDatestamp(new java.sql.Date(date.getTime()));
        newImage.setPath(imageUrl);
        newImage.setPrimary(false);
        newImage.setUser(user);
        newImage.setTruck(truck);

        truck = truckRepo.getOne(truck.getId());
        if (truck.getImages() != null){
            truckImages = truck.getImages();
        }

        truckImages.add(newImage);
        truck.setImages(truckImages);
        imageRepo.save(newImage);
        truckRepo.save(truck);

        model.addAttribute("truck", truck);

        return "redirect:/trucks/" + truckId;
    }

    @GetMapping("/trucks/{id}/edit")
    public String getTruckUpdateView(
            Model model,
            @PathVariable("id") long truckId,
            @ModelAttribute Truck truck
    ){
        model.addAttribute("cuisineOptions", cuisineRepo.findAllByIsPrimaryIsTrue());
        model.addAttribute("truck", truckRepo.getOne(truckId));
        return "/trucks/update";
    }

    @PostMapping("trucks/{id}/edit")
    public String updateTruckInfo(
            @ModelAttribute Truck truck,
            @RequestParam List<Cuisine> cuisines,
            @RequestParam String newCuisine,
            @RequestParam(value = "imageURL") String imageUrl,
            Model model){

        Image newTruckImage = new Image();
        List<Image> images = new ArrayList<>();

//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        truck.setUser(user);

        //adds a custom cuisine if one is added
        Cuisine newCustomCuisine = null;
        if (!newCuisine.equals("")) {
            try {
                newCustomCuisine = cuisineRepo.findCuisineByCategoryContaining(newCuisine);
                cuisines.add(newCustomCuisine);
            } catch (Exception e) {
                newCustomCuisine = new Cuisine();
                newCustomCuisine.setCategory(newCuisine);
                newCustomCuisine = cuisineRepo.save(newCustomCuisine);
                cuisines.add(newCustomCuisine);
            }
        }

//        handles adding or changing a primary image
        if (!imageUrl.equals("")) {
            if (truck.getImages() != null) {
                images = truck.getImages();
                int primary = -1;
                for (Image image : images) {
                    if (image.isPrimary()) {
                        primary = images.indexOf(image);
                    }
                }
                if (primary != -1) {
                    images.remove(primary);
                }
            }
            Date date = new Date();
            newTruckImage.setPath(imageUrl);
            newTruckImage.setPrimary(true);
            newTruckImage.setUser(truck.getUser());
            newTruckImage.setTruck(truck);
            newTruckImage.setDatestamp(new java.sql.Date(date.getTime()));
            images.add(newTruckImage);
        }

        truck.setImages(images);
//        cuisines.toString();
        truck.setCuisines(cuisines);
        truckRepo.save(truck);
        if (newTruckImage.getPath() != null) {
            imageRepo.save(newTruckImage);
        }
        truck = truckRepo.save(truck);

        model.addAttribute("truck", truck);

        return "redirect:/trucks/" + truck.getId();
    }

}


