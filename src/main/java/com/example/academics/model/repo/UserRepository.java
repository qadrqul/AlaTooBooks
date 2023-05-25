package com.example.academics.model.repo;

import com.example.academics.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findUsersByUsername(String username);
    Users findByUsername(String username);
}
