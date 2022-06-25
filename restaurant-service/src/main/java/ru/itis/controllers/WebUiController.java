package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itis.services.IngredientService;

@Controller
public class WebUiController {

    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/ui")
    public String getUiPage(Model model) {
        model.addAttribute("ingredients", ingredientService.getAll());
        return "ui";
    }
}
