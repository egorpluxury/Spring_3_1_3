package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Autowired
    public AdminController(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String index(Model model, Principal principal) {
        model.addAttribute("user", userService.findByEmail(principal.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleRepository.findAll());
        model.addAttribute("newUser", new User());
        return "/adminPage";
    }

    @PostMapping("/admin")
    public String create(User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        userService.updateUserById(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
