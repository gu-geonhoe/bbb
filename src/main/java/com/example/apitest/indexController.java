package com.example.apitest;

import com.example.apitest.User.entity.User;
import com.example.apitest.User.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController

public class indexController {


    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    private final UserService userService;

    public indexController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@Valid @RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("ROLE_USER");
        userService.createUser(user);
        Map<String ,String > map = new HashMap<>();
        map.put("message","Success");


        return new ResponseEntity<>(map, HttpStatus.CREATED);// map 대신에 로그인한 유저 정보 반환
    }
}
