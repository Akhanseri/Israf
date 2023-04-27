package kz.ali.Israf.controllers;

import kz.ali.Israf.Repository.RestaurantRepository;
import kz.ali.Israf.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {


    private final RestaurantRepository restaurantRepository;
    @Autowired
    public UserController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public String viewProfile(@PathVariable("id") int id, Model model) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            model.addAttribute("restaurant", restaurant);
            return "user/profile";
        } else {
            // handle restaurant not found error
            return "error";
        }
    }

    @PostMapping("/change-status/{id}")
    public String changeStatus(@PathVariable("id") int id, @RequestParam("isOpen") boolean isOpen) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if (optionalRestaurant.isPresent()) {
            Restaurant restaurant = optionalRestaurant.get();
            restaurant.setOpen(isOpen);
            restaurantRepository.save(restaurant);
            return "redirect:/user/{id}";
        } else {
            // handle restaurant not found error
            return "error";
        }
    }

}

}
