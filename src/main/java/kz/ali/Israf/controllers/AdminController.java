package kz.ali.Israf.controllers;

import kz.ali.Israf.Service.PeopleService;
import kz.ali.Israf.Service.RestaurantService;
import kz.ali.Israf.models.Person;
import kz.ali.Israf.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeopleService peopleService;

    private final RestaurantService restaurantService;
    @Autowired
    public AdminController(PeopleService peopleService, RestaurantService restaurantService) {
        this.peopleService = peopleService;
        this.restaurantService = restaurantService;
    }



    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<Person> users = peopleService.findAll();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @GetMapping("/new-user")
    public String createUserForm(Model model) {
        Person user = new Person();
        Restaurant restaurant = new Restaurant();
        user.setRestaurant(restaurant);
        model.addAttribute("user", user);
        return "admin/new-user";
    }

    @PostMapping("/save-user")
    public String saveUser(@ModelAttribute("user") Person user) {
        peopleService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        Person user = peopleService.findOne(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        Person user = peopleService.findOne(id);
        model.addAttribute("user", user);
        return "admin/user";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") Person user) {
        peopleService.save(user);
        return "redirect:/admin/users";
    }
}
