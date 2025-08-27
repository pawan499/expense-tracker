package com.expense.api.dtos.userdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequestDto {
    private  Long id;
    private String name;
    private String email;
    private String mobile;
    private String password;
}
