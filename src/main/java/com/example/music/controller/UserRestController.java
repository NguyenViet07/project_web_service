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
    public ResponseEntity createUser(@RequestBody User user){
        return new ResponseEntity(userService.saveOrUpdate(user), HttpStatus.CREATED);
    }

//    @PostMapping("/find-by-username")
//    public ResponseEntity findByUserName(@RequestBody UserDataDto user){
//        return new ResponseEntity(userService.findByUserName(user.getUserName()), HttpStatus.CREATED);
//    }

    @PostMapping("/admin/get-list-user")
    public ResponseEntity findByUserName(@RequestBody UserRequest user) {
        return new ResponseEntity(userService.getListUser(user), HttpStatus.CREATED);
    }
}
