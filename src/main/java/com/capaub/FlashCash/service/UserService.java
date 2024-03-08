package com.capaub.FlashCash.service;

import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.repository.UserRepository;
import com.capaub.FlashCash.service.form.SignUpForm;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registration(SignUpForm form) {
        User user = new User();
        UserAccount userAccount = new UserAccount();
        userAccount.setAmount(0.0);
        user.setUserAccount(userAccount);
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        userRepository.save(user);
    }
}