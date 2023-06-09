package kz.ali.Israf.Service;

import kz.ali.Israf.Repository.PeopleRepository;
import kz.ali.Israf.Repository.RestaurantRepository;
import kz.ali.Israf.models.Person;
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
public class PeopleService implements UserDetailsService {
    private final PeopleRepository peopleRepository;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository, RestaurantRepository restaurantRepository) {
        this.peopleRepository = peopleRepository;
        this.restaurantRepository = restaurantRepository;
    }


    public List<Person> findAll(){
        return peopleRepository.findAll();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> person = peopleRepository.findByUsername(username);

        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new PersonDetails(person.get());
    }
    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    public Person findOne(int id) {
        Optional<Person> foundPerson = peopleRepository.findById(id);
        return foundPerson.orElse(null);
    }

    public void deleteById(int id) {
        Person personToDelete = findOne(id);
        if (personToDelete.getRestaurant() != null) {
            personToDelete.getRestaurant().setPerson(null);
            restaurantRepository.delete(personToDelete.getRestaurant());
        }
        peopleRepository.delete(personToDelete);
    }


}



