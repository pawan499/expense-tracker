package com.expense.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expense.api.dtos.expensedtos.ExpenseRequestDto;
import com.expense.api.dtos.expensedtos.ExpenseResponseDto;
import com.expense.api.responseclass.ApiResponse;
import com.expense.api.services.ExpenseService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api/v1/expense")
public class ExpenseController {
 
    @Autowired
    private ExpenseService service;

    @PostMapping("/create")
    public ResponseEntity<?> generateExpense(@Valid @RequestBody ExpenseRequestDto dto) {
        ExpenseResponseDto response = service.createExpense(dto);
        ApiResponse<ExpenseResponseDto> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Expense created successfully");
        apiResponse.setStatus(200);
        apiResponse.setSuccess(true);
        apiResponse.setData(response);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllExpense() {
        List<ExpenseResponseDto> response = service.getAllExpenses();
        ApiResponse<List<ExpenseResponseDto>> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Data retrieved successfully");
        apiResponse.setStatus(200);
        apiResponse.setSuccess(true);
        apiResponse.setData(response);
        return ResponseEntity.ok(apiResponse);
    }
}
