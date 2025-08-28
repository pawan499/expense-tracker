package com.expense.api.repositories;

import java.util.List;
import com.expense.api.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.expense.api.entities.Expense;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense,Long> {
     List<Expense> findAllByUser(User user);
}
