package com.ubaid.product_service.handler;

import com.ubaid.product_service.exception.CategoryNotFoundException;
import com.ubaid.product_service.exception.ProductPurchaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public record GlobalExceptionHandler() {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<String> handle(CategoryNotFoundException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMessage());
    }
    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<String> handle(ProductPurchaseException exp){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exp.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>methodArgumentNotFoundException(MethodArgumentNotValidException exp){
        Map<String,String> errors=new HashMap<>();

        exp.getBindingResult().getAllErrors().forEach(error->{
            var fieldName=((FieldError)error).getField();
            var message=error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(errors));
    }
}
