package be.kevinbaes.goaltracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class HelloController {
    @RequestMapping("/")
    public String sayHello() {
        return "index";
    }
}