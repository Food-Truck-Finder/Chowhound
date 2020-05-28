package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Cuisine;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface TruckRepo extends JpaRepository<Truck,Long> {
    @Query("SELECT DISTINCT t FROM Truck t left join t.cuisines c WHERE t.name LIKE %:searchTerm% OR t.description LIKE %:searchTerm% OR c.category LIKE %:searchTerm%")
    List<Truck> findAllBySearchTerm(@Param("searchTerm") String searchTerm);
    List<Truck> findAllByNameContainingOrDescriptionContaining(String name, String description);
    List<Truck> findAllByDescriptionContaining(String description);
    List<Truck> findAllByCuisinesContaining(Cuisine cuisine);
    Truck findById(long id);
//    List<Truck> findAll(Pageable page);

//    List<Truck> findAllBy


//    public Truck findByName(String name);
    List<Truck> findAllByFavoritedUsersEquals(User user);
}
