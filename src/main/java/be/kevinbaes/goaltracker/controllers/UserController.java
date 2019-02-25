package be.kevinbaes.goaltracker.controllers;

import be.kevinbaes.goaltracker.DTO.RegistrationInfo;
import be.kevinbaes.goaltracker.entities.GoaltrackerUser;
import be.kevinbaes.goaltracker.repositories.GoaltrackerUserRepository;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private GoaltrackerUserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(GoaltrackerUserRepository userRepository) { this.userRepository = userRepository; }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) { this.passwordEncoder = passwordEncoder; }

    @GetMapping("/listusers")
    @ResponseBody
    public List<GoaltrackerUser> getUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/register")
    public String registrationForm(Model model) {
        model.addAttribute("user", new GoaltrackerUser());
        return "/user/register";
    }

    @PostMapping("/register")
    public String register(@Valid RegistrationInfo registrationInfo, BindingResult result) {
        if(result.hasErrors()) {
            return "/user/register";
        }

        GoaltrackerUser user = new GoaltrackerUser(
                registrationInfo.getUsername(),
                passwordEncoder.encode(registrationInfo.getPassword()),
                new ArrayList<>());

        userRepository.save(user);

        return "redirect:/";
    }

    @PostConstruct
    public void postInit() {
        if(userRepository == null) {
            throw new BeanInstantiationException(UserController.class, "userRepository not set");
        }
    }
}
