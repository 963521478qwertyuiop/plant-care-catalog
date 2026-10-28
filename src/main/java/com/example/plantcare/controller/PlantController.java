package com.example.plantcare.controller;

import com.example.plantcare.model.Plant;
import com.example.plantcare.model.CareLog;
import com.example.plantcare.repository.PlantRepository;
import com.example.plantcare.repository.CareLogRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.security.Principal;

@Controller
@RequestMapping("/plants")
public class PlantController {

    private final PlantRepository plantRepository;
    private final CareLogRepository careLogRepository;

    public PlantController(PlantRepository plantRepository, CareLogRepository careLogRepository) {
        this.plantRepository = plantRepository;
        this.careLogRepository = careLogRepository;
    }

    // 1. СПИСОК (Read) - защищенный от null
    @GetMapping
    public String listPlants(Model model, Principal principal) {
        if (principal == null) {
            model.addAttribute("plants", plantRepository.findAll());
        } else if (principal.getName().equals("admin")) {
            model.addAttribute("plants", plantRepository.findAll());
        } else {
            model.addAttribute("plants", plantRepository.findByOwnerUsername(principal.getName()));
        }
        return "plants";
    }

    // 2. СОЗДАНИЕ (Create) - Показ формы
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("plant", new Plant());
        return "plant-form";
    }

    // 2. СОЗДАНИЕ (Create) - Сохранение
    @PostMapping
    public String createPlant(@ModelAttribute Plant plant, Principal principal) {
        if (principal != null) {
            plant.setOwnerUsername(principal.getName()); 
        } else {
            plant.setOwnerUsername("unknown");
        }
        plantRepository.save(plant); 
        return "redirect:/plants";
    }

    // 3. ОБНОВЛЕНИЕ (Update) - Показ формы
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный ID растения: " + id));
        model.addAttribute("plant", plant);
        return "plant-form";
    }

    // 3. ОБНОВЛЕНИЕ (Update) - Сохранение
    @PostMapping("/update/{id}")
    public String updatePlant(@PathVariable Long id, @ModelAttribute Plant plant) {
        plant.setId(id); 
        Plant oldPlant = plantRepository.findById(id).orElse(null);
        if (oldPlant != null) {
            plant.setOwnerUsername(oldPlant.getOwnerUsername());
        }
        plantRepository.save(plant); 
        return "redirect:/plants";
    }

    // 4. УДАЛЕНИЕ (Delete)
    @GetMapping("/delete/{id}")
    public String deletePlant(@PathVariable Long id) {
        plantRepository.deleteById(id);
        return "redirect:/plants";
    }

    // --- РАБОТА С ЖУРНАЛОМ УХОДА (CareLog) ---

    @GetMapping("/{id}/logs")
    public String listLogs(@PathVariable Long id, Model model) {
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный ID растения: " + id));
        model.addAttribute("plant", plant);
        model.addAttribute("logs", plant.getCareLogs());
        return "logs";
    }

    @GetMapping("/{id}/logs/new")
    public String showLogForm(@PathVariable Long id, Model model) {
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный ID растения: " + id));
        CareLog log = new CareLog();
        log.setPlant(plant);
        log.setDate(LocalDate.now());
        model.addAttribute("careLog", log);
        return "log-form";
    }

    @PostMapping("/{id}/logs")
    public String createLog(@PathVariable Long id, @ModelAttribute CareLog careLog) {
        Plant plant = plantRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Неверный ID растения: " + id));
        careLog.setPlant(plant);
        careLogRepository.save(careLog);
        return "redirect:/plants/" + id + "/logs";
    }
    
    @GetMapping("/{id}/logs/delete/{logId}")
    public String deleteLog(@PathVariable Long id, @PathVariable Long logId) {
        careLogRepository.deleteById(logId);
        return "redirect:/plants/" + id + "/logs";
    }
}