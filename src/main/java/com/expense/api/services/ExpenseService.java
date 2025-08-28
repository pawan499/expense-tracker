package com.expense.api.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.expense.api.dtos.expensedtos.ExpenseRequestDto;
import com.expense.api.dtos.expensedtos.ExpenseResponseDto;
import com.expense.api.entities.Expense;
import com.expense.api.entities.User;
import com.expense.api.repositories.ExpenseRepository;
@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repo;

    @Autowired
    private UserService userService;

    public ExpenseResponseDto createExpense(ExpenseRequestDto dto){
        User user=userService.getCurrentAuthenticatedUser();
        Expense ex=new Expense();
        ex.setAmount(dto.getAmount());
        ex.setDescription(dto.getDescription());
        ex.setType(dto.getType());
        ex.setCreatedDate(new Date());
        ex.setUser(user);
        Expense createdExp=repo.save(ex);
        ExpenseResponseDto resDto=new ExpenseResponseDto();
        resDto.setId(createdExp.getId());
        resDto.setAmount(createdExp.getAmount());
        resDto.setDescription(createdExp.getDescription());
        resDto.setCreatedDate(createdExp.getCreatedDate());
        resDto.setType(ex.getType());
        return resDto;
    }


    public List<ExpenseResponseDto> getAllExpenses(){
        User user=userService.getCurrentAuthenticatedUser();
        List<Expense> list=repo.findAllByUser(user);
        List<ExpenseResponseDto> res=new ArrayList<>();
        for(Expense e:list){
            ExpenseResponseDto dto=new ExpenseResponseDto();
             dto.setId(e.getId());
             dto.setAmount(e.getAmount());
             dto.setDescription(e.getDescription());
             dto.setCreatedDate(e.getCreatedDate());
             dto.setType(e.getType());
             res.add(dto);
           
        }
        return res;
    }


}
