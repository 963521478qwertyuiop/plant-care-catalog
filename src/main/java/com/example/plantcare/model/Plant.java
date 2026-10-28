package com.example.plantcare.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int wateringIntervalDays;
    private String lightLevel; // LOW, MEDIUM, HIGH
    private String ownerUsername;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CareLog> careLogs;

    // Геттеры и сеттеры (можно сгенерировать в VS Code: правой кнопкой -> Source Action -> Generate Getters and Setters)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getWateringIntervalDays() { return wateringIntervalDays; }
    public void setWateringIntervalDays(int wateringIntervalDays) { this.wateringIntervalDays = wateringIntervalDays; }
    public String getLightLevel() { return lightLevel; }
    public void setLightLevel(String lightLevel) { this.lightLevel = lightLevel; }
    public String getOwnerUsername() { return ownerUsername; }
    public void setOwnerUsername(String ownerUsername) { this.ownerUsername = ownerUsername; }
    public List<CareLog> getCareLogs() { return careLogs; }
    public void setCareLogs(List<CareLog> careLogs) { this.careLogs = careLogs; }
}