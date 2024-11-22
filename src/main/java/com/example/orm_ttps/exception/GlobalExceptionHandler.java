package com.example.orm_ttps.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse("EMAIL_YA_EXISTENTE", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(DniAlreadyExistsException.class)
    public ResponseEntity<Object> handleDniAlreadyExistsException(DniAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse("DNI_YA_EXISTENTE", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse("RECURSO_NO_ENCONTRADO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex){
        ErrorResponse errorResponse = new ErrorResponse("ACCESO_DENEGADO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse("USUARIO_NO_ENCONTRADO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(CustomExpiredJwtException.class)
    public ResponseEntity<Object> handleExpiredJwtException(CustomExpiredJwtException ex){
        System.out.println("ENTREE");
        ErrorResponse errorResponse = new ErrorResponse("TOKEN_EXPIRADO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse("RECURSO_YA_EXISTENTE", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MenuComponentFailedTypeException.class)
    public ResponseEntity<Object> handleMenuComponentFailedTypeException(MenuComponentFailedTypeException ex){
        ErrorResponse errorResponse = new ErrorResponse("TIPO_MENU_COMPONENTE_INCORRECTO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(EmptyMenuException.class)
    public ResponseEntity<Object> handleEmptyMenuException(EmptyMenuException ex){
        ErrorResponse errorResponse = new ErrorResponse("MENU_VACIO", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }





}
