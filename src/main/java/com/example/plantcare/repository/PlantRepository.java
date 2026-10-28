package com.example.plantcare.repository;

import com.example.plantcare.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    // Найдёт все растения конкретного пользователя
    List<Plant> findByOwnerUsername(String ownerUsername);
}