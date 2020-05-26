package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Truck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TruckRepo extends JpaRepository<Truck,Long> {
    public List<Truck> findAllByNameContainingOrDescriptionContaining(String name, String description);
//    public List<Truck> findAllByAverageReviewRatingBetween(double low, double high);
//    public Truck findByName(String name);

}
