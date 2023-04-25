package kz.ali.Israf.controllers;

import kz.ali.Israf.Repository.RestaurantRepository;
import kz.ali.Israf.Service.RestaurantService;
import kz.ali.Israf.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestaurantApiController {

    private final RestaurantService restaurantService;

    @Autowired
    public RestaurantApiController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/restaurants")
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }

    @GetMapping("trueRestaurants")
    public List<Restaurant> getOpenRestaurants() {
        return restaurantService.getOpenRestaurants();
    }
}