package com.example.workerservice.model.exception;

public class ConsumeTaskException extends RuntimeException{
    public ConsumeTaskException(String message) {
        super(message);
    }
}
