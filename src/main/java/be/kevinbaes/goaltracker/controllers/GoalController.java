package be.kevinbaes.goaltracker.controllers;

import be.kevinbaes.goaltracker.entities.Goal;
import be.kevinbaes.goaltracker.entities.GoaltrackerUser;
import be.kevinbaes.goaltracker.services.GoalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

/**
 * controller to handle CRUD operations on goals
 */
@Controller
@RequestMapping("/goal")
public class GoalController {
    private GoalService goalService;
    private Logger logger = LoggerFactory.getLogger(GoalController.class);

    @ModelAttribute
    public void populateModel(Model model) {
        logger.debug("populating model");
        model.addAttribute("goal", new Goal(""));
        model.addAttribute("goals", goalService.findAll());
    }

    @Autowired
    public void setGoalService(GoalService goalService) {
        this.goalService = goalService;
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
    public String createGoal(@Valid Goal goal, BindingResult result, Principal principal) {
        if(result.hasErrors()) {
            logger.debug("goal has errors");
            return "/goal/create";
        }

        // TODO find a cleaner way than 2 (!) downcasts to get the GoaltrackerUser
        Authentication authentication = (Authentication) principal;
        GoaltrackerUser user = (GoaltrackerUser) authentication.getPrincipal();
        goal.setOwner(user);

        goalService.save(goal);
        return "redirect:/goal";
    }

    @PostConstruct
    public void initData() {
        if(goalService == null){
            throw new BeanInstantiationException(GoalController.class, "goalDao not set");
        }
    }
}