package com.expense.api.services;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.api.dtos.userdto.UserDto;
import com.expense.api.dtos.userdto.UserRegisterRequestDto;
import com.expense.api.entities.User;
import com.expense.api.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserDto createUser(UserRegisterRequestDto user){
            Optional<User> existingUser=userRepository.findByEmail(user.getEmail());
            if(existingUser.isPresent()){
                throw new IllegalArgumentException("Email already exists!");
            }
            User newUser=new User();
            newUser.setName(user.getName());
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setMobile(user.getMobile());
            User savedUser=userRepository.save(newUser);
            UserDto userDto=new UserDto();
            userDto.setId(savedUser.getId());
            userDto.setName(savedUser.getName());
            userDto.setEmail(savedUser.getEmail());
            userDto.setMobile(savedUser.getMobile());
            return userDto;
    }

}
