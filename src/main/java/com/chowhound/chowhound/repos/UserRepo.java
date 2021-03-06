package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByUsername(String username);

//    List<Truck> findAllByFavoriteTrucks(User user);
    User findById(long id);
}
