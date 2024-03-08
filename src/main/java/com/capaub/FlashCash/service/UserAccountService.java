package com.capaub.FlashCash.service;

import com.capaub.FlashCash.entity.SocialLink;
import com.capaub.FlashCash.entity.User;
import com.capaub.FlashCash.entity.UserAccount;
import com.capaub.FlashCash.repository.SocialLinkRepository;
import com.capaub.FlashCash.repository.UserRepository;
import com.capaub.FlashCash.service.form.AddCashCbForm;
import com.capaub.FlashCash.service.form.FriendList;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserAccountService {
    private final UserRepository userRepository;
    private final SessionService sessionService;
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
        socialLinkRepository.save(socialLink);
    }

    public List<FriendList> showMyFriends(User user) {
        List<SocialLink> socialLinks = socialLinkRepository.findAllByUser1(user).get();
        List<FriendList> friendsData = new ArrayList<>();
        socialLinks.forEach(socialLink -> {
            FriendList friendList = new FriendList();
            User friend = socialLink.getUser2();
            friendList.setFriendName(friend.getFirstName());
            friendList.setFriendMail(friend.getEmail());
            friendsData.add(friendList);
        });
        return friendsData;
    }
}