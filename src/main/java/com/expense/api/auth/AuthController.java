package com.expense.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.expense.api.dtos.userdto.LoginRequestDto;
import com.expense.api.dtos.userdto.LoginResponseDto;
import com.expense.api.dtos.userdto.UserDto;
import com.expense.api.entities.User;
import com.expense.api.repositories.UserRepository;
import com.expense.api.responseclass.ApiError;
import com.expense.api.responseclass.ApiResponse;
import com.expense.api.security.jwt.JwtUtil;
import com.expense.api.security.redis.JwtBlacklistService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManagar;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklistService jwtBlacklistService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto req) {
        ApiResponse<LoginResponseDto> res = new ApiResponse<>();
        try {
            Authentication auth = authManagar
                    .authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
            String username = auth.getName();
            User user = userRepository.findByEmail(username).get();
            String token = jwtUtil.generateToken(username);
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setName(user.getName());
            userDto.setEmail(user.getEmail());
            userDto.setMobile(user.getMobile());
            LoginResponseDto loginRes = new LoginResponseDto();
            loginRes.setToken(token);
            loginRes.setUser(userDto);
            res.setData(loginRes);
            res.setMessage("Login Successful");
            res.setSuccess(true);
            res.setStatus(200);
            return ResponseEntity.ok(res);

        } catch (Exception e) {
            res.setData(null);
            res.setMessage("Invalid Credentials");
            res.setSuccess(false);
            res.setStatus(401);
            res.getErrors().add(new ApiError("Email/Password", "Invalid Email or Password"));
            return ResponseEntity.status(401).body(res);
        }

    }

@PostMapping("/logout")
@PreAuthorize("isAuthenticated()")
public ResponseEntity<ApiResponse<Object>> logout(HttpServletRequest req) {
    ApiResponse<Object> response = new ApiResponse<>();
    try {
        String authHeader = req.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage("Authorization header missing or invalid");
            return ResponseEntity.badRequest().body(response);
        }

        String token = authHeader.substring(7);

        if(!jwtUtil.isTokenValid(token, jwtUtil.extractUserName(token))) {
            response.setSuccess(false);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Invalid or expired token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        long expirationMillis = jwtUtil.getRemainingExpiration(token);
        if(expirationMillis > 0){
            jwtBlacklistService.blacklistToken(token, expirationMillis);
            response.setSuccess(true);
            response.setStatus(HttpStatus.OK.value());
            response.setMessage("Logged out successfully");
            response.setData(null);
            return ResponseEntity.ok(response);
        } else {
            response.setSuccess(false);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setMessage("Token already expired");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    } catch(Exception e){
        // Logging recommended
        response.setSuccess(false);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setMessage("Something went wrong");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}

}
