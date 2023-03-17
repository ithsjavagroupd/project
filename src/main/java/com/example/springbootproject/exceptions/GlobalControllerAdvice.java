package com.example.springbootproject.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

@ControllerAdvice
    @ResponseBody
    public class GlobalControllerAdvice {
        @ExceptionHandler(ResponseStatusException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        public String handleNoSuchElementException(){
            return "Invalid id, no such element";
        }
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String badRequestException(){
        return "Invalid input";
    }



}
