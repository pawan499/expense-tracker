package com.expense.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.api.dtos.expensedtos.ExpenseRequestDto;
import com.expense.api.dtos.expensedtos.ExpenseResponseDto;
import com.expense.api.repositories.ExpenseRepository;

@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository repo;

    public ExpenseResponseDto createExpense(ExpenseRequestDto dto){
        return null;
    }

}
