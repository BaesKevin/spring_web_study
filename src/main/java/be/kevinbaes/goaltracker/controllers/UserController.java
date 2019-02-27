package be.kevinbaes.goaltracker.controllers;

import be.kevinbaes.goaltracker.DTO.RegistrationInfo;
import be.kevinbaes.goaltracker.entities.GoaltrackerUser;
import be.kevinbaes.goaltracker.repositories.GoaltrackerUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    private GoaltrackerUserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private Logger logger = LoggerFactory.getLogger(UserController.class);
    private static final String KEY_REGISTRATION = "registration";

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
        RegistrationInfo info = new RegistrationInfo();
        model.addAttribute(KEY_REGISTRATION, info);
        return "/user/register";
    }

    /*
    The databinding will automatically create a modelattribute with key 'registrationInfo' when binding the form values.
    To make sure it is bound to 'registration' we must use @ModelAttribute on the argument.
    If you don't do this, the error messages break in the frontend because there are no errors associated with the object we added,
    only with the object that was automatically bound with the key 'registrationInfo'
     */
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registration") RegistrationInfo registration, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "/user/register";
        }

        GoaltrackerUser user = new GoaltrackerUser(
                registration.getUsername(),
                passwordEncoder.encode(registration.getPassword()),
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
