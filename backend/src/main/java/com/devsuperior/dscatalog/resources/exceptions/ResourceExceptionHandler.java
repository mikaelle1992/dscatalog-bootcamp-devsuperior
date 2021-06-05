package com.devsuperior.dscatalog.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResouceNotFoundException;

@ControllerAdvice //permite que a classe intercept alguma excetion que acontecer na camada de resources e tratar aqui
public class ResourceExceptionHandler {
		
		@ExceptionHandler(ResouceNotFoundException.class)// sempre que acontecer alguma exception do tipo EntityNotFoundException(class) o tratamento sera direcionado para esse metodo 
		 public ResponseEntity<StandardError> entityNotFound(ResouceNotFoundException e, HttpServletRequest request){
			HttpStatus status = HttpStatus.NOT_FOUND;
			 StandardError err = new StandardError();
			 err.setTimestamp(Instant.now());// now: metodo que pego o horario atual
			 err.setStatus(status.value());//.value : converter para int
			 err.setError("Resource not found");
			 err.setMessage(e.getMessage());
			 err.setPath(request.getRequestURI());
			 
			 return ResponseEntity.status(status).body(err);
     }
		
		@ExceptionHandler(DatabaseException.class)// sempre que acontecer alguma exception do tipo EntityNotFoundException(class) o tratamento sera direcionado para esse metodo 
		 public ResponseEntity<StandardError> database(DatabaseException e, HttpServletRequest request){
			HttpStatus status = HttpStatus.BAD_REQUEST;
			StandardError err = new StandardError();
			 err.setTimestamp(Instant.now());// now: metodo que pego o horario atual
			 err.setStatus(status.value());//.value : converter para int
			 err.setError("Database exception");
			 err.setMessage(e.getMessage());
			 err.setPath(request.getRequestURI());
			 
			 return ResponseEntity.status(status).body(err);
     }
		
}