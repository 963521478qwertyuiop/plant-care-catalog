package com.example.plantcare.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "care_logs")
public class CareLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private Plant plant;

    private String actionType; // WATERING, FERTILIZING, REPOTTING
    private LocalDate date;
    private String note;

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Plant getPlant() { return plant; }
    public void setPlant(Plant plant) { this.plant = plant; }
    public String getActionType() { return actionType; }
    public void setActionType(String actionType) { this.actionType = actionType; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
}