package com.example.music.repositories;

import com.example.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    User findAllByUserId(Long userId);

    User findByUsername(String username);
}
