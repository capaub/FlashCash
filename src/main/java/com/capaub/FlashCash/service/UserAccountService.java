package com.capaub.FlashCash.service;

import com.capaub.FlashCash.entity.SocialLink;
import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.repository.SocialLinkRepository;
import com.capaub.FlashCash.repository.UserRepository;
import com.capaub.FlashCash.service.form.AddCashCbForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserAccountService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final SessionService sessionService;
    @Autowired
    private final SocialLinkRepository socialLinkRepository;

    public UserAccountService(UserRepository userRepository, SessionService sessionService, SocialLinkRepository socialLinkRepository) {
        this.userRepository = userRepository;
        this.sessionService = sessionService;
        this.socialLinkRepository = socialLinkRepository;
    }

    public void updateIban(UserAccount userAccountUpdated) {
        UserAccount userAccountRecovered = sessionService.sessionUser().getUserAccount();
        userAccountRecovered.setIban1(userAccountUpdated.getIban1());
        userAccountRecovered.setIban2(userAccountUpdated.getIban2());
        userAccountRecovered.setIban3(userAccountUpdated.getIban3());
        userAccountRecovered.setIban4(userAccountUpdated.getIban4());
        userAccountRecovered.setIban5(userAccountUpdated.getIban5());
        User user = sessionService.sessionUser();
        user.setUserAccount(userAccountRecovered);
        userRepository.save(user);
    }

    public void addCashWithCb(AddCashCbForm addCashCbForm) {
        UserAccount userAccountRecovered = sessionService.sessionUser().getUserAccount();
        userAccountRecovered.setAmount(userAccountRecovered.getAmount() + addCashCbForm.getAmount());
        User user = sessionService.sessionUser();
        user.setUserAccount(userAccountRecovered);
        userRepository.save(user);
    }

    public void withdrawToIban(UserAccount userAccountUpdated) {
        UserAccount userAccountRecovered = sessionService.sessionUser().getUserAccount();
        userAccountRecovered.setAmount(userAccountRecovered.getAmount() - userAccountUpdated.getAmount());
        User user = sessionService.sessionUser();
        user.setUserAccount(userAccountRecovered);
        userRepository.save(user);
    }

    public void addAFriend(String userMail) {
        User userAddAFriend = sessionService.sessionUser();

        User friend = userRepository.findUserByEmail(userMail).get();

        SocialLink socialLink = new SocialLink();

        socialLink.setUser1(userAddAFriend);
        socialLink.setUser2(friend);

        List<SocialLink> socialLinks = userAddAFriend.getSocialLinks();
        socialLinkRepository.save(socialLink);

/*        userAddAFriend.setSocialLinks(socialLinks);

        userRepository.save(userAddAFriend);*/

    }
}