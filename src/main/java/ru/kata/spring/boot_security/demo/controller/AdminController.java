package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

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
    public String index(Model model){
        model.addAttribute("users",userService.getAllUsers());
        return "users";
    }
    @GetMapping("/admin/new")
    public String newUser(Model model){
        model.addAttribute("user",new User());
        List<Role> roles=roleRepository.findAll();
        model.addAttribute("allRoles",roles);
        return "new";
    }
    @PostMapping("/admin")
    public String create(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/{id}")
    public String showUser(@PathVariable("id") int id,Model model){
        model.addAttribute("userId",userService.showUserById(id));
        return "id";
    }
    @GetMapping("/admin/{id}/edit")
    public String edit(Model model,@PathVariable("id") int id){
        model.addAttribute("user",userService.showUserById(id));
        List<Role> roles=roleRepository.findAll();
        model.addAttribute("allRoles",roles);
        return "edit";
    }
    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("person") User user,@PathVariable("id") int id){
        userService.updateUserById(id,user);
        return "redirect:/admin";
    }
    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id")int id){
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
