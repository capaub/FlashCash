package com.capaub.FlashCash.controller;

import com.capaub.FlashCash.service.ContactService;
import com.capaub.FlashCash.service.SessionService;
import com.capaub.FlashCash.service.form.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {

    private final ContactService contactService;
    private final SessionService sessionService;

    public ContactController(ContactService contactService, SessionService sessionService) {
        this.contactService = contactService;
        this.sessionService = sessionService;
    }

    @GetMapping("/contactUs")
    @ResponseBody
    public ModelAndView showIbanForm(Model model) {
        model.addAttribute("userMail", sessionService.sessionUser().getEmail());
        model.addAttribute("contactForm", new ContactForm());
        return new ModelAndView("contactForm");
    }
    @PostMapping("/contactUs")
    public ModelAndView processAddIban(@ModelAttribute("contactForm") ContactForm contactForm) {
        contactService.sendMessage(contactForm);
        return new ModelAndView("/account", "user", sessionService.sessionUser());
    }
}