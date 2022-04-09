package com.example.diplom3.controller;

import com.example.diplom3.entity.User;
import com.example.diplom3.repos.UserRepo;
import com.example.diplom3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

import static com.example.diplom3.entity.Role.USER;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam String password,
                          @RequestParam String username,
                          @RequestParam String firstname,
                          @RequestParam String secondname,
                          @RequestParam String patronymic,
                          @RequestParam String number,
                          @RequestParam String email,
                          Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(username);

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }
        User user = new User(username, password, firstname, secondname, patronymic, number, email, USER);
        userRepo.save(user);
        userService.addUser(user);
        return "redirect:/login";
    }
}