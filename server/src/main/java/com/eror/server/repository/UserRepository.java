package com.eror.server.repository;


import com.eror.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    User findByEmail(String email);

    User findUserByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select u from User u join fetch u.roles where u.id=:id")
    User findUserById(@Param("id") Long id);

    @Query(value = "select u from User u left join fetch u.roles")
    List<User> findAll();

    @Query(value = "select u from User u left join fetch u.roles")
    List<User> findAllUsers();
}
