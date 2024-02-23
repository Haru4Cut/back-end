package com.haru4cut.domain.user;

import com.haru4cut.domain.oauth2.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByEmail(String email);

    Users findUserById(Long userId);

    Optional<Users> findBySocialIdAndSocialType(String SocialId, SocialType socialType);

}
