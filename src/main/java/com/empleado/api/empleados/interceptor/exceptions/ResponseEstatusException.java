package com.empleado.api.empleados.interceptor.exceptions;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.empleado.api.empleados.interceptor.exceptions.model.ErrorDetails;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ResponseEstatusException extends ResponseEntityExceptionHandler{
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
	    log.error("ConstraintViolationException: " + ex.getMessage());
	    Map<String, List<String>> mapErrores = new HashMap<>();
	    for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
	        mapErrores.put(violation.getPropertyPath().toString(), Arrays.asList(violation.getMessage()));
	    }
	    ErrorDetails errores = ErrorDetails.builder()
	            .status(HttpStatus.BAD_REQUEST)
	            .errors(mapErrores)
	            .message("Error en la validación de los datos")
	            .build();
	    return ResponseEntity.badRequest().body(errores);
	}

	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<Object>handleNosuchElement(RuntimeException ex, WebRequest request){
		log.error("IllegalArgumentException: "+ex.getMessage());
		 ;
		Map<String, List<String>>mapErrores = new HashMap<>(); 
		mapErrores.put("NoSuchElementException",Arrays.asList("Parametro incorrecto ",ex.getMessage()));
        ErrorDetails errores = ErrorDetails.builder()
        		.status(HttpStatus.BAD_REQUEST)
        		.errors(mapErrores)
        		.message("Error en Api Empleados").build();

		return ResponseEntity.badRequest().body(errores);
	} 
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request){
		log.error("handleMethodArgumentNotValid: "+ex.getMessage());

		List<FieldError>fieldErrorsList = ex.getBindingResult().getFieldErrors();
		Map<String, List<String>>mapErrores =  fieldErrorsList
											.stream()
											.collect(Collectors.groupingBy(x->x.getField(),Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));
        ErrorDetails errores = ErrorDetails.builder().status(HttpStatus.BAD_REQUEST).errors(mapErrores).message("Error en el request body").build();

		return ResponseEntity.badRequest().body(errores);
	}
	
	@ExceptionHandler(value = {IllegalArgumentException.class,IllegalStateException.class})
	protected ResponseEntity<Object>handleConflict(RuntimeException ex, WebRequest request){
		log.error("IllegalArgumentException: "+ex.getMessage());
		String bodyOfResponse = "Argumento incorrecto"+ex.getMessage();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}
	
}
