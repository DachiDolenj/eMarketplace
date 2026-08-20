package com.example.emmarketplace.repository;

import com.example.emmarketplace.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u WHERE u.email = :login OR u.username = :login")
    User findByEmailOrUsername(@Param("login") String login);

}
