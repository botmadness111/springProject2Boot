package ru.andrey.springProject2Boot.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.andrey.springProject2Boot.models.Human;
import ru.andrey.springProject2Boot.services.HumanService;
import ru.andrey.springProject2Boot.validators.HumanValidator;

@Controller
@RequestMapping("/human")
public class HumanController {

    HumanService humanService;
    HumanValidator humanValidator;

    @Autowired
    public HumanController(HumanService humanService, HumanValidator humanValidator) {
        this.humanService = humanService;
        this.humanValidator = humanValidator;
    }

    @GetMapping("/all")
    public String getPeoplePage(Model model) {
        model.addAttribute("people", humanService.getPeople());

        return "/human/people";
    }

    @GetMapping("/add")
    public String getAddPage(Model model) {
        model.addAttribute("human", new Human());

        return "/human/addHuman";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("human") @Valid Human human, BindingResult bindingResult) {
        humanValidator.validate(human, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/human/addHuman";
        }

        humanService.add(human);

        return "redirect:/human/all";
    }

    @GetMapping("/edit/{id}")
    public String getUpdatePage(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("human", humanService.getHuman(id));

        return "/human/editHuman";
    }

    @PatchMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute Human human, BindingResult bindingResult, @PathVariable("id") Integer id) {
        humanValidator.validate(human, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/human/editHuman";
        }

        humanService.update(human, id);

        return "redirect:/human/all";
    }

    @GetMapping("/{id}")
    public String getHumanPage(Model model, @PathVariable("id") int id) {
        model.addAttribute("human", humanService.getHuman(id));

        return "/human/human";
    }
}
