package be.kevinbaes.goaltracker.controllers;

import be.kevinbaes.goaltracker.entities.Goal;
import be.kevinbaes.goaltracker.repositories.GoalRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

/**
 * controller to handle CRUD operations on goals
 */
@Controller
@RequestMapping("/goal")
public class GoalController {
    private GoalRepository goalRepository;
    private Logger logger = LoggerFactory.getLogger(GoalController.class);

    @ModelAttribute
    public void populateModel(Model model) {
        logger.debug("populating model");
        model.addAttribute("goal", new Goal(""));
        model.addAttribute("goals", goalRepository.findAll());
    }

    @Autowired
    public void setGoalRepository(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    @GetMapping
    public String findAll() {
        return "goal/index";
    }

    @GetMapping("/create")
    public String getCreateForm() {
        return "goal/create";
    }

    @PostMapping("/create")
    public String createGoal(@Valid Goal goal, BindingResult result) {
        if(result.hasErrors()) {
            logger.debug("goal has errors");
            return "/goal/create";
        }
        goalRepository.save(goal);
        return "redirect:/goal";
    }

    @PostConstruct
    public void initData() {
        if(goalRepository == null){
            throw new BeanInstantiationException(GoalController.class, "goalDao not set");
        }
        goalRepository.save(new Goal("A new goal"));
    }
}