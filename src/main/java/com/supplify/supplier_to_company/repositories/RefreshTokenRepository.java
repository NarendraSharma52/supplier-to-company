package com.supplify.supplier_to_company.repositories;

import com.supplify.supplier_to_company.models.RefreshToken;
import com.supplify.supplier_to_company.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);
}
