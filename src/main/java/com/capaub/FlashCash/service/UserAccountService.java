package com.capaub.FlashCash.service;

import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAccountService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SessionService sessionService;

    public UserAccountService(UserRepository userRepository, SessionService sessionService) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
    }

    public void updateIban(UserAccount userAccountUpdated) {
        UserAccount userAccountRecovered = sessionService.SessionUser().getUserAccount();

        userAccountRecovered.setIban1(userAccountUpdated.getIban1());
        userAccountRecovered.setIban2(userAccountUpdated.getIban2());
        userAccountRecovered.setIban3(userAccountUpdated.getIban3());
        userAccountRecovered.setIban4(userAccountUpdated.getIban4());
        userAccountRecovered.setIban5(userAccountUpdated.getIban5());

        User user = sessionService.SessionUser();

        user.setUserAccount(userAccountRecovered);

        userRepository.save(user);
    }
}
