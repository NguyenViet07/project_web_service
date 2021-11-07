package com.example.music.repositories;

import com.example.music.dto.IUserDTO;
import com.example.music.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Objects;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = " SELECT u.username, u.name, u.image as avatar, r.name as roleName " +
            " FROM users u INNER JOIN role r ON u.id_role = r.id " +
            " WHERE username = :userName ", nativeQuery = true)
    IUserDTO findUserDTOByUserName(@Param("userName")String userName);

    @Query(value = " SELECT u.user_id, u.address, u.company, u.identity_card as identityCard, " +
            " u.username, u.name, u.is_singger, u.is_active, " +
            " u.created, u.updated, r.name as roleName " +
            " FROM users u INNER JOIN role r ON u.id_role = r.id " +
            " WHERE (:userName is null or u.username like %:userName%) " +
            " AND r.id =:roleId ",
            countQuery = " SELECT u.user_id, u.address, u.company, u.identity_card as identityCard, u.username, u.name, u.is_singger, u.is_active, u.created, u.updated , r.name as roleName " +
                    " FROM users u INNER JOIN role r ON u.id_role = r.id " +
                    " WHERE (:userName is null or u.username like %:userName%) " +
                    " r.id =:roleId ", nativeQuery = true)
    Page<Object[]> getListUser(@Param("userName")String userName, @Param("roleId")Long roleId, Pageable page);

    User findAllByUserId(Long userId);

    User findByUsername(String username);
}
