package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.repository.UserAccountRepository;
import com.capaub.FlashCash.repository.UserRepository;
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
import org.springframework.web.servlet.ModelAndView;

import java.nio.file.attribute.UserPrincipal;

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
        model.addAttribute("user",sessionService.SessionUser());
        return new ModelAndView("/account");
    }

    @GetMapping("/signUp")
    public ModelAndView showRegisteredForm() {
        return new ModelAndView("signUp", "signUpForm", new SignUpForm());
    }

    @PostMapping("/signUp")
    public ModelAndView processRequest(@ModelAttribute("signUpForm") SignUpForm form) {
        userService.registration(form);
        sessionService.SessionUser();

        return new ModelAndView("signIn");
    }

    @GetMapping("/addIban")
    public ModelAndView showIbanForm(Model model) {
        model.addAttribute("userAccountUpdated", sessionService.SessionUser().getUserAccount());
        return new ModelAndView("iban");
    }

    @PostMapping("/addIban")
    public ModelAndView processAddIban(@ModelAttribute("userAccountUpdated") UserAccount userAccountUpdated) {

        userAccountService.updateIban(userAccountUpdated);

        return new ModelAndView("index");
    }
}
