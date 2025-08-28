package com.expense.api.dtos.expensedtos;

import java.util.Date;

import com.expense.api.enums.ExpenseType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseResponseDto {
        private Long id;
        private double amount;
        private String description;
        private ExpenseType type;
        private Date createdDate;
}
