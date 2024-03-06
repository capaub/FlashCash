package com.capaub.FlashCash.repository;

import com.capaub.FlashCash.entity.SocialLink;
import com.capaub.FlashCash.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialLinkRepository extends JpaRepository<SocialLink, Integer> {
    Optional<List<SocialLink>> findAllByUser1(User user);
}