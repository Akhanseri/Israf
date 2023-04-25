package kz.ali.Israf.Repository;

import kz.ali.Israf.models.Person;
import kz.ali.Israf.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository <Person,Integer>{
    Optional<Person> findByUsername(String username);

}
