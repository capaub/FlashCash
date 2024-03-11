package com.capaub.FlashCash.service;

import com.capaub.FlashCash.service.form.ContactForm;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    public void sendMessage(ContactForm contactForm) {
        contactForm.getUserMail();
        contactForm.getMessage();
    }
}