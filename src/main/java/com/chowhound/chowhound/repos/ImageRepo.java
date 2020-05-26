package com.chowhound.chowhound.repos;

import com.chowhound.chowhound.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image,Long> {
}
