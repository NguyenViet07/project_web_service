package com.example.music.service;

import com.example.music.dto.ResponsePageDTO;
import com.example.music.dto.request.UserRequest;
import com.example.music.dto.response.UserViewDto;
import com.example.music.model.Role;
import com.example.music.model.User;
import com.example.music.model.UserPrinciple;
import com.example.music.repositories.RoleRepository;
import com.example.music.repositories.UserRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import com.example.music.untils.RepositoryUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

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

        return new UserPrinciple(user.getUserId(), user.getUsername(), user.getPassword(), Arrays.asList(new SimpleGrantedAuthority(user.getRoles().getName())));
    }

    @Transient
    public ResponseResult saveOrUpdate(User user) {
        try {
            if (user == null || user.getUsername() == null || user.getPassword() == null || user.getRoleId() == null) {
                return new ResponseResult(ResponseCode.ERR_INPUT);
            }
            if (user.getUsername() != null) {
                User userOld = userRepository.findByUsername(user.getUsername());
                if (userOld != null) return new ResponseResult(ResponseCode.ERROR_USER_EXIST);
            }
            if (user.getRoleId() == 3) {
                user.setIsSinger(0);
                user.setIsSinger(1);
            } else if (user.getRoleId() == 2){
                user.setIsSinger(1);
                user.setIsActive(0);
                if (user.getCompany() == null || user.getAddress() == null || user.getIdentityCard() == null) {
                    return new ResponseResult(ResponseCode.ERR_INPUT);
                }
            } else if (user.getRoleId() == 1) {
                user.setIsSinger(1);
                user.setIsActive(1);
            } else return new ResponseResult(ResponseCode.ERROR);

            Role role = roleRepository.findAllById(user.getRoleId());
            user.setRoles(role);
            String passDecode = passwordEncoder.encode(user.getPassword());
            user.setPassword(passDecode);

            userRepository.save(user);
            return ResponseResult.success(null);
        } catch (Exception ex) {
            ResponseCode responseCode = ResponseCode.ERROR;
            responseCode.setMessage(ex.getMessage());
            return new ResponseResult(responseCode);
        }

    }

    public ResponseResult activeUser(String username) {
        User user = userRepository.findByUsername(username);
        Integer isActive = user.getIsActive();
        if (isActive == 1) {
            user.setIsActive(0);
        } else if (isActive == 0) {
            user.setIsActive(1);
        } else return ResponseResult.success(ResponseCode.ERROR);
        userRepository.save(user);
        return ResponseResult.success(null);
    }


    public ResponseResult resetPassUser(Long userId) {
        User user = userRepository.findAllByUserId(userId);
        return ResponseResult.success(null);
    }

    public ResponseResult findByUserName(String userName) {
        return ResponseResult.success(userRepository.findByUsername(userName));
    }

    public ResponseResult getListUser(UserRequest user) {
        PageRequest page = PageRequest.of(user.getPage() - 1, user.getSize());

        Page<Object[]> pageCustomer = userRepository.getListUser(user.getUsername(), 1l, page);

        return getListFileSign(pageCustomer);
    }

    public ResponseResult getListFileSign(Page<Object[]> currentPage) {
        if (currentPage != null) {
            List<UserViewDto> rs = new ArrayList<>();
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            UserViewDto tinyItem;
            int index;
            for (Object[] row : currentPage.getContent()) {
                index = 0;
                tinyItem = new UserViewDto();
                tinyItem.setUserId(RepositoryUtil.getLongFromBigInteger(row[index++]));
                tinyItem.setAddress(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setCompany(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setIdentityCard(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setUsername(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setName(RepositoryUtil.getStringByValue(row[index++]));
                tinyItem.setIsSinger(RepositoryUtil.getIntegerByValue(row[index++]));
                tinyItem.setIsActive(RepositoryUtil.getIntegerByValue(row[index++]));
                tinyItem.setCreateDate(RepositoryUtil.getTimestampToString(row[index++], dateFormat));
                tinyItem.setUpdateDate(RepositoryUtil.getTimestampToString(row[index++], dateFormat));
                tinyItem.setRoleName(RepositoryUtil.getStringByValue(row[index++]));
                rs.add(tinyItem);
            }
            ResponsePageDTO responsePageBean = new ResponsePageDTO();
            responsePageBean.setContent(rs);
            responsePageBean.setTotalPages(currentPage.getTotalPages());
            responsePageBean.setTotalElements(currentPage.getTotalElements());
            return ResponseResult.success(responsePageBean);
        }
        return ResponseResult.success(null);
    }
}
