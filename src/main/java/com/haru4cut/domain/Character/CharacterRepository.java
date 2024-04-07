package com.haru4cut.domain.Character;

import com.haru4cut.domain.user.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Characters, Long> {
    Optional<Characters> findByUsers(Users users);
}
