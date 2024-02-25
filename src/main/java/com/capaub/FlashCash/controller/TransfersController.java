package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.UserAccountService;
import com.capaub.FlashCash.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransfersController {

    @Autowired
    private final UserService userService;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final UserAccountService userAccountService;

    public TransfersController(UserService userService, SessionService sessionService, UserAccountService userAccountService) {
        this.userService = userService;
        this.sessionService = sessionService;
        this.userAccountService = userAccountService;
    }

    @GetMapping("/addFound")
    public ModelAndView showAddFoundForm(Model model) {
        model.addAttribute("userFromTransfer", sessionService.sessionUser());
        return new ModelAndView("iban");
    }

    @PostMapping("/addFound")
    public ModelAndView processAddFound(@ModelAttribute("userAccountUpdated") UserAccount userAccountUpdated) {
        userAccountService.updateIban(userAccountUpdated);
        return new ModelAndView("/account");
    }
}
