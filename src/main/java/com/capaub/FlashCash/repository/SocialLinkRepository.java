package com.capaub.FlashCash.repository;

import com.capaub.FlashCash.entity.SocialLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialLinkRepository extends JpaRepository<SocialLink, Integer> {
}
