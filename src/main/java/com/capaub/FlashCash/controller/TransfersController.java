package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.UserAccountService;
import com.capaub.FlashCash.service.UserService;
import com.capaub.FlashCash.service.form.AddCashCbForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/addCashWithCb")
    @ResponseBody
    public ModelAndView showIbanForm() {
        return new ModelAndView("addCashCbForm", "addCashCbForm", new AddCashCbForm());
    }
    @PostMapping("/addCashWithCb")
    public ModelAndView processAddIban(@ModelAttribute("addCashCbForm") AddCashCbForm addCashCbForm) {
        userAccountService.addCashWithCb(addCashCbForm);
        return new ModelAndView("/account", "user", sessionService.sessionUser());
    }

    @GetMapping("/withdrawToIban")
    @ResponseBody
    public ModelAndView showWithdrawForm(Model model) {
        model.addAttribute("withdrawForm", sessionService.sessionUser().getUserAccount());
        return new ModelAndView("withdrawForm");
    }
    @PostMapping("/withdrawToIban")
    public ModelAndView processWithdrawToIban(@ModelAttribute("withdrawForm") UserAccount userAccountUpdated) {
        userAccountService.withdrawToIban(userAccountUpdated);
        return new ModelAndView("/account", "user", sessionService.sessionUser());
    }

}