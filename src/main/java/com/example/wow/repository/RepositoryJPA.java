package com.example.wow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.wow.model.UserJPA;

@Repository
public interface RepositoryJPA extends JpaRepository<UserJPA, Integer> {
    List<UserJPA> findByName(String name);
    UserJPA findByEmail(String email);

    @Query("SELECT u FROM user_jpa u WHERE u.name LIKE %:name%")
    List<UserJPA> findByNameContaining(@Param("name") String name);

    @Query(value = "SELECT * FROM user WHERE email LIKE %:domain%", nativeQuery = true)
    List<UserJPA> findByEmailDomain(@Param("domain") String domain);
}
