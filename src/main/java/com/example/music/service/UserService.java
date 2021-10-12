package com.example.music.service;

import com.example.music.model.Role;
import com.example.music.model.User;
import com.example.music.model.UserPrinciple;
import com.example.music.repositories.RoleRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Collection;

@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public String getUserNameByUserId(Long userId) {

        try {
            User user = userRepository.findAllByUserId(userId);
            return user.getUsername();
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrinciple(user.getUserId(), user.getUsername(), user.getPassword(), (Collection<? extends GrantedAuthority>) user.getRoles());
    }

    @Transient
    public ResponseResult saveOrUpdate(User user) {
        if (user == null || user.getUsername() == null || user.getPassword() == null || user.getRoleId() == null) {
            return new ResponseResult(ResponseCode.ERR_INPUT);
        }
        Role role = roleRepository.findAllById(user.getRoleId());
        user.setRoles(role);
        String passDecode = passwordEncoder.encode(user.getPassword());
        user.setPassword(passDecode);
        userRepository.save(user);
        return ResponseResult.success(null);
    }

    public ResponseResult findByUserName(String userName) {
        return ResponseResult.success(userRepository.findByUsername(userName));
    }
}
