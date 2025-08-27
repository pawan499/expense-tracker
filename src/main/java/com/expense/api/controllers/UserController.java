package com.expense.api.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.expense.api.dtos.userdto.UserDto;
import com.expense.api.dtos.userdto.UserRegisterRequestDto;
import com.expense.api.responseclass.ApiError;
import com.expense.api.responseclass.ApiResponse;
import com.expense.api.services.UserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRegisterRequestDto user){
        user.setPassword(encoder.encode(user.getPassword()));
        ApiResponse<UserDto> response=new ApiResponse<>();
        response.setTimestamp(new Date());
       try{
        UserDto createdUser=userService.createUser(user);
        response.setData(createdUser);
        response.setMessage("User created successfully");
        response.setStatus(201);
        response.setSuccess(true);
        response.setErrors(null);
        return ResponseEntity.status(201).body(response);
       }
       catch(IllegalArgumentException e){
        response.setData(null);
        response.setMessage("User creation failed");
        response.setStatus(400);
        response.setSuccess(false);
        ApiError error=new ApiError();
        error.setField("email");
        error.setMessage(e.getMessage());
        response.getErrors().add(error);
        return ResponseEntity.badRequest().body(response);
       }
       catch(Exception e){
        response.setData(null);
        response.setMessage("User creation failed");
        response.setStatus(500);
        response.setSuccess(false);
        ApiError error=new ApiError();
        error.setField("internal");
        error.setMessage(e.getMessage() != null ? e.getMessage() : "Internal server error");
        response.getErrors().add(error);
        return ResponseEntity.status(500).body(response);
       }
    }
    
}