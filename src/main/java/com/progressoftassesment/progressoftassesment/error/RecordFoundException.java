package com.progressoftassesment.progressoftassesment.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FOUND)
public class RecordFoundException extends RuntimeException {

    public RecordFoundException(String message) {
        super(message);
    }
}
