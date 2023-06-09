package kz.ali.Israf.controllers;

import kz.ali.Israf.Repository.PeopleRepository;
import kz.ali.Israf.Service.PeopleService;
import kz.ali.Israf.Service.RestaurantService;
import kz.ali.Israf.Service.StorageService;
import kz.ali.Israf.models.Person;
import kz.ali.Israf.models.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final PeopleService peopleService;

    private final RestaurantService restaurantService;

    private final PeopleRepository peopleRepository;

    private final StorageService storageService;


    @Autowired
    public AdminController(PeopleService peopleService, RestaurantService restaurantService,PeopleRepository peopleRepository,StorageService storageService) {
        this.peopleService = peopleService;
        this.restaurantService = restaurantService;
        this.peopleRepository = peopleRepository;
        this.storageService = storageService;
    }


    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<Person> users = peopleService.findAll();
        for (Person user : users) {
            Restaurant restaurant = user.getRestaurant();
            if (restaurant != null) {
                restaurant.setPerson(user);
            }
        }
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
    public String saveUser(@ModelAttribute("user") Person user, @RequestParam("imageFile") MultipartFile imageFile) {
        if (!imageFile.isEmpty()) {
            String imageUrl = storageService.uploadFile(imageFile);
            user.getRestaurant().setImageUrl(imageUrl);
        }
        user.getRestaurant().setPerson(user);
        peopleService.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam("id") int id) {
        // Находим пользователя по id
        Optional<Person> personOptional = peopleRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();

            // Удаляем ресторан, если у пользователя был связанный ресторан
            if (person.getRestaurant() != null) {
                restaurantService.delete(person.getRestaurant());
            }

            // Удаляем пользователя
            peopleRepository.delete(person);
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/edit-user/{id}")
    public String editUser(@PathVariable("id") int id, Model model) {
        Person user = peopleService.findOne(id);
        user.getRestaurant().setPerson(user);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @GetMapping("/user/{id}")
    public String getUserById(@PathVariable("id") int id, Model model) {
        Person user = peopleService.findOne(id);
        user.getRestaurant().setPerson(user);
        model.addAttribute("user", user);
        return "admin/user";
    }

    @PostMapping("/update-user")
    public String updateUser(@ModelAttribute("user") Person user,
                             @RequestParam("file") MultipartFile file,
                             @RequestParam("restaurant.longitude") double longitude,
                             @RequestParam("restaurant.latitude") double latitude) {

        Person existingUser = peopleService.findOne(user.getId());

        // Обновите свойства объекта existingUser
        existingUser.setUsername(user.getUsername());
        existingUser.setPassword(user.getPassword());
        existingUser.setRole(user.getRole());



        // Обновите свойства связанного объекта ресторана, если он существует
        if (existingUser.getRestaurant() != null) {
            Restaurant restaurant = existingUser.getRestaurant();
            restaurant.setName(user.getRestaurant().getName());
            restaurant.setAddress(user.getRestaurant().getAddress());
            restaurant.setDescription(user.getRestaurant().getDescription());
            restaurant.setOpen(user.getRestaurant().isOpen());
            restaurant.setLatitude(latitude);
            restaurant.setLongitude(longitude);
            if (!file.isEmpty()) {
                String imageUrl = storageService.uploadFile(file);
                restaurant.setImageUrl(imageUrl);
            }


        }

        // Сохраните обновленный объект existingUser в базе данных
        peopleService.save(existingUser);
        return "redirect:/admin/users";
    }
}

