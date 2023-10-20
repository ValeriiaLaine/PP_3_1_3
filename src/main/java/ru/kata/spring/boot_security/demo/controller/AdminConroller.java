package ru.kata.spring.boot_security.demo.controller;

import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminConroller {
    private final RoleService roleService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminConroller(RoleService roleService, UserService userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/user_profile/{id}")
    public String findUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("userRoles", user.getRoles());
        return "/user_profile";
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "list_users";
    }

    @GetMapping("/modify/{id}")
    public String modify(Model model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("userRoles", roleService.findAll());
        return "/modify";
    }

    @PatchMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") @Valid User updateUser,
                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/modify";
        }
        userService.updateUser(updateUser);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @GetMapping("/new")
    public String formForCreate(Model model, User user) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("user", new User());
        return "/form_for_create";
    }

    @PostMapping("/create")
    public String saveNewUser(@ModelAttribute("user") @Valid User user,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/form_for_create";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }
}
