package com.chowhound.chowhound.controllers;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import com.chowhound.chowhound.repos.CuisineRepo;
import com.chowhound.chowhound.repos.ImageRepo;
import com.chowhound.chowhound.repos.TruckRepo;
import com.chowhound.chowhound.repos.UserRepo;
import com.chowhound.chowhound.services.SortTrucksService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FavoritesController {

    private TruckRepo truckRepo;
    private UserRepo userRepo;
    private ImageRepo imageRepo;
    private CuisineRepo cuisineRepo;
    private SortTrucksService sortTrucksService;

    public FavoritesController(TruckRepo truckRepo, UserRepo userRepo, ImageRepo imageRepo, CuisineRepo cuisineRepo, SortTrucksService sortTrucksService) {
        this.truckRepo = truckRepo;
        this.userRepo = userRepo;
        this.imageRepo = imageRepo;
        this.cuisineRepo = cuisineRepo;
        this.sortTrucksService = sortTrucksService;
    }

    //mapping to add a truck to user's favorite list
    @GetMapping("/favorites/{id}/addFav")
    public String addNewFavoriteTruck(@ModelAttribute Truck truck, @PathVariable("id") long truckId, Model model) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long findUserID = user.getId();
            user = userRepo.findById(findUserID);

            truck = truckRepo.getOne(truckId);

            model.addAttribute("truck", truck);
            List<Truck> favTrucks = user.getFavoriteTrucks();
            favTrucks.add(truck);
            user.setFavoriteTrucks(favTrucks);

            user = userRepo.save(user);

            model.addAttribute("user", user);

            model.addAttribute("isFav", true);

            if (truck.getUser() == null) {
                User tempUser = new User();
                truck.setUser(tempUser);
            }
            model.addAttribute("favMsg", "Truck added to your favorites");
        } catch (Exception e) {
            System.out.println("No User logged in");
            e.printStackTrace();
        }


        return "redirect:/trucks/" + truck.getId();
    }
    // maoping to remove a truck from favorites list
    @GetMapping("/favorites/{id}/removeFav")
    public String removeFavoriteTruck(@ModelAttribute Truck truck, @PathVariable("id") long id, Model model) {

        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            long userId = user.getId();
            User loggedInUser = userRepo.findById(userId);

            List<Truck> favoriteTrucks = loggedInUser.getFavoriteTrucks();

            int index = 0;
            for(Truck trk : favoriteTrucks){
                if (trk.getId() == id) {
                    break;
                }
                index++;
            }

            favoriteTrucks.remove(index);

            loggedInUser.setFavoriteTrucks(favoriteTrucks);
            userRepo.save(loggedInUser);

            model.addAttribute("favMsg", "Truck removed from your favorites");
            model.addAttribute("user", loggedInUser);
        } catch (Exception e){
            e.printStackTrace();

        }


        model.addAttribute("truck", truck);
        return "redirect:/trucks/" + id;
    }
}
