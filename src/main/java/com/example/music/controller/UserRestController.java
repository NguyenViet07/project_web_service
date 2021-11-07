package com.example.music.controller;


import com.example.music.dto.request.UserRequest;
import com.example.music.dto.response.UserDataDto;
import com.example.music.model.User;
import com.example.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity createUser(@RequestBody UserRequest userRequest){
        return new ResponseEntity(userService.saveOrUpdate(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/all-user/info")
    public ResponseEntity findByUserName(@RequestBody UserRequest user){
        return new ResponseEntity(userService.findByUserName(user.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/admin/active-user")
    public ResponseEntity activeUser(@RequestBody UserRequest user){
        return new ResponseEntity(userService.activeUser(user.getUsername()), HttpStatus.OK);
    }

    @PostMapping("/admin/get-list-user")
    public ResponseEntity findAllUser(@RequestBody UserRequest user) {
        return new ResponseEntity(userService.getListUser(user), HttpStatus.OK);
    }
}
