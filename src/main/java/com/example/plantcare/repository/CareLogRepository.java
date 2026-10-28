package com.example.plantcare.repository;

import com.example.plantcare.model.CareLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareLogRepository extends JpaRepository<CareLog, Long> {
}