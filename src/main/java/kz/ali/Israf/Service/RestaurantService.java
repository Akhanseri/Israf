package kz.ali.Israf.Service;

import kz.ali.Israf.Repository.RestaurantRepository;
import kz.ali.Israf.models.Person;
import kz.ali.Israf.models.Restaurant;
import kz.ali.Israf.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RestaurantService  {
    private final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {

        return restaurantRepository.findAll();
    }

    public List<Restaurant> getOpenRestaurants(){
        return restaurantRepository.findAllByIsOpenTrue();
    }
    @Transactional
    public void saveRestaurant(Restaurant restaurant){
        restaurantRepository.save(restaurant);
    }

    public Restaurant getRestaurantById(int id){
        Optional<Restaurant> foundRestaurant = restaurantRepository.findById(id);
        return foundRestaurant.orElse(null);
    }

}
