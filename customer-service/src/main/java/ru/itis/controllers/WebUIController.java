package ru.itis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.services.CitiesService;

@Controller
public class WebUIController {

    @Autowired
    private CitiesService citiesService;

    @RequestMapping(value = "/ui", method = RequestMethod.GET)
    public String uiPage(Model model){
        model.addAttribute("cities", citiesService.getAll());
        return "webui";
    }
}
