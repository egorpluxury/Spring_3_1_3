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
    public String index(Model model, @ModelAttribute("user") User user, Principal principal) {
        User authuser = userService.findByUsername(principal.getName());
        model.addAttribute("user", authuser);
        model.addAttribute("roles", authuser.getRoles());
        model.addAttribute("users", userService.getAllUsers());
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("allRoles", roles);
        return "/adm_1";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("user1") User user, Model model) {
        userService.saveUser(user);
        model.addAttribute("user1", new User());
        return "redirect:/admin";
    }

    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("person") User user, @PathVariable("id") int id, Model model) {
        userService.updateUserById(id, user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
