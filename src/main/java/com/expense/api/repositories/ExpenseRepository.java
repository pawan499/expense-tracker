package com.expense.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense.api.entities.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense,Long> {
    
}
