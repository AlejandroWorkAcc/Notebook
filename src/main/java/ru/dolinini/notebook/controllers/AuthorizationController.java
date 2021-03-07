package ru.dolinini.notebook.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.dolinini.notebook.model.User;
import ru.dolinini.notebook.repos.UserRepo;
import ru.dolinini.notebook.security.Role;
import ru.dolinini.notebook.security.Status;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthorizationController {

    public final UserRepo userRepo;


    public AuthorizationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "/auth/login";
    }
    @GetMapping("/success")
    public String getSuccessAuthorizationPage() {
        return "/auth/success";
    }

    @GetMapping("/registration")
    public String addUser(@ModelAttribute("user") User user) {
        return "/user/createuser";
    }

    @PostMapping("/registration")
    public String addNewUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if(userRepo.existsByFirstname(user.getFirstname())) {
            String warning="Error, user with such name already exists";
            model.addAttribute("warning", warning);
            return "/user/createuser";
        }
        if (bindingResult.hasErrors()) {
            return "/user/createuser";
        }
        String pass=new BCryptPasswordEncoder(12).encode(user.getPassword());
        User newUser=new User(user.getFirstname(),user.getLastname(), pass, user.getEmail());
        newUser.setRole(Role.USER);
        newUser.setStatus(Status.ACTIVE);
        userRepo.save(newUser);
        String warning="Success, now you can login";
        model.addAttribute("warning", warning);
        return "/auth/login";
    }
}
