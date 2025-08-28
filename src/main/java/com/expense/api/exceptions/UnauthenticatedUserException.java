package com.expense.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthenticatedUserException extends RuntimeException{
    public UnauthenticatedUserException() {
        super("User is not authenticated");
    }

    public UnauthenticatedUserException(String message) {
        super(message);
    }
}
