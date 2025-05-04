package com.tajutechgh.ems.repository;

import com.tajutechgh.ems.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByUsername(String username);
    public Optional<User> findByUsernameOrEmail(String username, String email);
    public boolean existsByEmail(String email);
    public boolean existsByUsername(String username);
}
