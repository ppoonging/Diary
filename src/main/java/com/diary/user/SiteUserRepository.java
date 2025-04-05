package com.diary.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface SiteUserRepository extends JpaRepository<SiteUser,Integer> {


    Optional<SiteUser> findByUsername(String username);
    Optional<SiteUser> findByEmail(String email);
}
