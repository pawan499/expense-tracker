package com.expense.api.dtos.expensedtos;
import org.springframework.beans.factory.annotation.Value;

import com.expense.api.enums.ExpenseType;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseRequestDto {
    private Long id;

    @NotNull(message="Amount is required")
    @Min(value =1,message = "Amount shoul be greate than 0")
    private Double amount;

    
    @NotNull(message = "Expense type is required")
    private ExpenseType type;

    @NotNull(message = "Description is required")
    private String description;
}
