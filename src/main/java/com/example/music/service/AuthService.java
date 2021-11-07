package com.example.music.service;


import com.example.music.config.JwtTokenProvider;
import com.example.music.dto.IUserDTO;
import com.example.music.dto.UserInfo;
import com.example.music.dto.request.AuthenticationRequest;
import com.example.music.dto.response.UserDataDto;
import com.example.music.model.Token;
import com.example.music.model.User;
import com.example.music.model.UserPrinciple;
import com.example.music.repositories.UserRepository;
import com.example.music.response.ResponseCode;
import com.example.music.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private TokenService tokenService;

    public ResponseResult login(AuthenticationRequest input) {
        try {
            // Xác thực từ username và password.
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            input.getUsername(),
                            input.getPassword()
                    )
            );

            // Nếu không xảy ra exception tức là thông tin hợp lệ
            // Set thông tin authentication vào Security Context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userRepository.findByUsername(input.getUsername());

            // logout tất cả các nơi đang dùng
            List<Token> listOldToken = tokenService.findAllByUsername(user.getUsername());
            for (Token token: listOldToken) {
                tokenService.deleteToken(token.getValue());
            }

            // Trả về jwt cho người dùng.
            String jwt = tokenProvider.generateToken((UserPrinciple) authentication.getPrincipal());

            // Luu token vào database
            Token tokenObj = new Token();
            tokenObj.setUsername(user.getUsername());
            tokenObj.setUserId(user.getUserId());
            tokenObj.setValue(jwt);
            tokenService.save(tokenObj);

            // lấy thông tin user để trả về
            IUserDTO userObj = userRepository.findUserDTOByUserName(input.getUsername());
            UserDataDto userDataDto = toDTOUser(userObj);
            userDataDto.setToken(jwt);
            return ResponseResult.success(userDataDto);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(ResponseCode.ERROR_AUTH);
        }
    }

    public UserDataDto toDTOUser(IUserDTO iUserDTO) {
        UserDataDto userDataDto = new UserDataDto();

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(iUserDTO.getUsername());
        userInfo.setFullName(iUserDTO.getName());
        userInfo.setAvatar(iUserDTO.getAvatar());
        userDataDto.setUserInfo(userInfo);
        userDataDto.setRoleName(iUserDTO.getRoleName());

        return userDataDto;
    }

    public ResponseResult logout(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (org.springframework.util.StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            tokenService.deleteToken(bearerToken.substring(7));
            return ResponseResult.success("Thành công");
        }
        return new ResponseResult(ResponseCode.ERROR);
    }

}
