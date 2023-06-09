package es.mdfe.gestionpreguntas.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ArgumentNotValidExceptionHandler {
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	 @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ExceptionHandler(ConstraintViolationException.class)
	    public Map<String, String> handleValidationExceptions2(
	    		ConstraintViolationException ex) {
	        Map<String, String> errors = new HashMap<>();
	        ex.getConstraintViolations().forEach((violation) -> {
	            String fieldName = violation.getPropertyPath().toString();
	            String errorMessage = violation.getMessage();
	            errors.put(fieldName, errorMessage);
	        });
	        return errors;
	    }
//	    @ExceptionHandler(DataIntegrityViolationException.class)
//	    @ResponseStatus(HttpStatus.BAD_REQUEST)
//	    public String handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
//	        return "Error de integridad de datos: " + ex.getMessage();
//	    }
}