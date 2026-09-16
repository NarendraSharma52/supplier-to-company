package com.supplify.supplier_to_company.repositories;

import com.supplify.supplier_to_company.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    public User findByEmail(String email);

    public List<User> findByCreatedAtBetween(LocalDate startDate,
                                                LocalDate endDate);
    public Page<User> findAll(Pageable pageable);
}
