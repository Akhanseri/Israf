package kz.ali.Israf.Repository;

import kz.ali.Israf.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
        List<Restaurant> findAllByIsOpenTrue();
}
