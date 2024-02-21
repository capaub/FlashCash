package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.repository.UserRepository;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.UserService;
import com.capaub.FlashCash.service.form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserService userService;
    @Autowired
    private final SessionService sessionService;

    public UserController(UserRepository userRepository, UserService userService, SessionService sessionService) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ModelAndView home(Model model) {
        model.addAttribute("user",sessionService.SessionUser());
        return new ModelAndView("/account");
    }

    @PostMapping("/signUp")
    public ModelAndView processRequest(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        User user = sessionService.SessionUser();

        return new ModelAndView("signIn");
    }

    @GetMapping("/signUp")
    public ModelAndView showRegisteredForm() {
        return new ModelAndView("signUp", "signUpForm", new SignUpForm());
    }

}