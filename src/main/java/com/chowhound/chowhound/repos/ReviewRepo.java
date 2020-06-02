package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Review;
import com.chowhound.chowhound.models.Truck;
import com.chowhound.chowhound.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
    Review findByUserEqualsAndTruckEquals(User user, Truck truck);
}
