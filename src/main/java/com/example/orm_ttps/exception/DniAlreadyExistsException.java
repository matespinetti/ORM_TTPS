package com.example.orm_ttps.exception;

public class DniAlreadyExistsException extends RuntimeException{
    public DniAlreadyExistsException(String dni) {
        super("El dni: " + dni + " ya esta registrado en el sistema.");
    }
}
