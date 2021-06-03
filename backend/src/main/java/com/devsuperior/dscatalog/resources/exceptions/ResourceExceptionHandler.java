package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.EntityNotFoundException;

@ControllerAdvice //permite que a classe intercept alguma excetion que acontecer na camada de resources e tratar aqui
public class ResourceExceptionHandler {
		
		@ExceptionHandler(EntityNotFoundException.class)// sempre que acontecer alguma exception do tipo EntityNotFoundException(class) o tratamento sera direcionado para esse metodo 
		 public ResponseEntity<StandardError> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
			 StandardError err = new StandardError();
			 err.setTimestamp(Instant.now());// now: metodo que pego o horario atual
			 err.setStatus(HttpStatus.NOT_FOUND.value());//.value : converter para int
			 err.setError("Resource not found");
			 err.setMessage(e.getMessage());
			 err.setPath(request.getRequestURI());
			 
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
}
}