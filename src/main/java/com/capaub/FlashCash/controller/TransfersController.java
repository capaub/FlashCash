package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.TransferService;
import com.capaub.FlashCash.service.UserAccountService;
import com.capaub.FlashCash.service.form.AddCashCbForm;
import com.capaub.FlashCash.service.form.TransferForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TransfersController {

    private final TransferService transferService;
    private final SessionService sessionService;
    private final UserAccountService userAccountService;

    public TransfersController(TransferService transferService, SessionService sessionService, UserAccountService userAccountService) {
        this.transferService = transferService;
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
        return new ModelAndView("account", "user", sessionService.sessionUser());
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

    @GetMapping("/transferToFriend/{friendMail}")
    @ResponseBody
    public ModelAndView transferToAFriend(@PathVariable String friendMail, Model model) {
        TransferForm transferForm = new TransferForm();
        transferForm.setMailUserFrom(sessionService.sessionUser().getEmail());
        transferForm.setMailUserTo(friendMail);

        model.addAttribute("transferForm", transferForm);
        model.addAttribute("maxAmount", sessionService.sessionUser().getUserAccount().getAmount());

        return new ModelAndView("/transferForm");
    }
    @PostMapping("/transferToFriend")
    public String processTransferToAFriend(@ModelAttribute("transferForm") TransferForm transferForm) {
        transferService.transferCashToFriend(transferForm);

        return "redirect:/showFriends";
    }
}