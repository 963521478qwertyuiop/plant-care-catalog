package com.example.plantcare.controller;

import com.example.plantcare.model.Plant;
import com.example.plantcare.repository.PlantRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/plants")
public class PlantController {

    private final PlantRepository plantRepository;

    public PlantController(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }

    // Страница со списком всех растений
    @GetMapping
    public String listPlants(Model model) {
        model.addAttribute("plants", plantRepository.findAll());
        return "plants"; // Имя HTML-файла (без .html)
    }

    // Форма добавления нового растения
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("plant", new Plant());
        return "plant-form";
    }

    // Сохранение нового или обновлённого растения
    @PostMapping
    public String savePlant(@ModelAttribute Plant plant) {
        // Пока просто сохраняем (в 5 задании прикрутим владельца из авторизации)
        plantRepository.save(plant);
        return "redirect:/plants";
    }
}