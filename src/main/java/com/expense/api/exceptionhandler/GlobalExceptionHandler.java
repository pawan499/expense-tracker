package com.expense.api.exceptionhandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.expense.api.responseclass.ApiError;
import com.expense.api.responseclass.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        ApiResponse<?> res = new ApiResponse<>();

        List<ApiError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> {
            ApiError error = new ApiError();
            error.setField(err.getField());
            error.setMessage(err.getDefaultMessage());
            errors.add(error);

        });
        res.setSuccess(false);
        res.setData(null);
        res.setStatus(HttpStatus.BAD_REQUEST.value());
        res.setErrors(errors);
        res.setMessage("Please fix all validation!");
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRunTimeException(RuntimeException ex) {
        ApiResponse<?> res = new ApiResponse<>();

        List<ApiError> errors = new ArrayList<>();
        ApiError error = new ApiError();

        error.setField("N/A");
        error.setMessage(ex.getMessage());
        errors.add(error);
        res.setSuccess(false);
        res.setData(null);
        res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        res.setErrors(errors);
        res.setMessage("somehting went wrong!");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
    }
}
