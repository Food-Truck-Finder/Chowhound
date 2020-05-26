package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Cuisine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuisineRepo  extends JpaRepository<Cuisine,Long> {
//    List<Cuisine> findByPrimaryTrue();
    List<Cuisine> findAllByIsPrimaryIsTrue();
}
