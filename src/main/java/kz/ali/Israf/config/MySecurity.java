package kz.ali.Israf.config;

import kz.ali.Israf.Repository.PeopleRepository;
import kz.ali.Israf.models.Person;
import kz.ali.Israf.security.PersonDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("mySecurity")
public class MySecurity {

    private final PeopleRepository peopleRepository;

    public MySecurity(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public boolean checkRestaurant(Authentication authentication, int id) {
        PersonDetails userDetails = (PersonDetails) authentication.getPrincipal();
        Optional<Person> person = peopleRepository.findByUsername(userDetails.getUsername());
        if (person.isPresent() && person.get().getRestaurant() != null) {
            int restaurantId = person.get().getRestaurant().getId();
            return id == restaurantId;
        }
        return false;
    }
}