package dev.cironeto.todospring.exception;

public class DataIntegrityException extends RuntimeException {

    public DataIntegrityException(String message){
        super(message);
    }
}
