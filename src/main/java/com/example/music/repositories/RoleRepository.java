package com.example.music.repositories;

import com.example.music.model.Role;
import com.example.music.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    Role findAllById(Long id);

}
