package com.progressoftassesment.progressoftassesment.error;

import com.progressoftassesment.progressoftassesment.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@Slf4j
    public class GlobalExceptionHandling extends ResponseEntityExceptionHandler {


        @ExceptionHandler(Exception.class)
        public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
            List<String> details = new ArrayList<>();
            details.add(ex.getLocalizedMessage());
            ErrorResponse error = new ErrorResponse("Server Error", HttpStatus.INTERNAL_SERVER_ERROR.toString(),details);
            log.error(error.toString());
            return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    @ExceptionHandler(RecordFoundException.class)
    public final ResponseEntity<Object> handleUserFoundException(RecordFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse("Record Found",HttpStatus.FOUND.toString(), details);
        log.error(error.toString());
        return new ResponseEntity(error, HttpStatus.FOUND);
    }


    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());

        ErrorResponse error = new ErrorResponse("Record Not Found",HttpStatus.NOT_FOUND.toString(), details);
        log.error(error.toString());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
@Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid (MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        ErrorResponse error = new ErrorResponse("Validation Failed",HttpStatus.BAD_REQUEST.toString(), details);
        log.error(error.toString());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}


