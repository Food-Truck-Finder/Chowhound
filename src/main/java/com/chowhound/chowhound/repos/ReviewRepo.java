package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review,Long> {
}
