package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.UserAccountService;
import com.capaub.FlashCash.service.UserService;
import com.capaub.FlashCash.service.form.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final UserAccountService userAccountService;

    public UserController(UserService userService, SessionService sessionService, UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping("/")
    public ModelAndView home(Model model) {
        model.addAttribute("user",sessionService.sessionUser());
        return new ModelAndView("/account");
    }

    @GetMapping("/signUp")
    public ModelAndView showRegisteredForm() {
        return new ModelAndView("signUp", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signUp")
    public ModelAndView processRequest(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        sessionService.sessionUser();
        return new ModelAndView("signIn");
    }

    @GetMapping("/addIban")
    @ResponseBody
    public ModelAndView showIbanForm(Model model) {
        model.addAttribute("userAccountUpdated", sessionService.sessionUser().getUserAccount());
        return new ModelAndView("addIbanForm");
    }

    @PostMapping("/addIban")
    public ModelAndView processAddIban(@ModelAttribute("userAccountUpdated") UserAccount userAccountUpdated) {
        userAccountService.updateIban(userAccountUpdated);
        return new ModelAndView("/account", "user", sessionService.sessionUser());
    }

}
