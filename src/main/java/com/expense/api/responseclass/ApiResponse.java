package com.expense.api.responseclass;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private Date timestamp =new Date();
    private String message;
    private int status;
    private boolean success;
    private T data;
    private List<ApiError> errors=new ArrayList<>();
}
