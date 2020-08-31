package com.company;

public class ValidationException extends RuntimeException{
    //todo - change to checked Exception, update references

    public ValidationException(String message){
        super(message);
    }
}
