package com.devsuperior.dscatalog.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResouceNotFoundException;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	private long exintingId;
	private long nonExintingId;
	private long dependentId;
	
	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExintingId = 1000L;
		dependentId= 4L;
		
		Mockito.doNothing().when(repository).deleteById(exintingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExintingId);// configuração do Mockito
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	
	@Test
	public void deleteShowldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(()->{
			service.delete(exintingId);
			
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(exintingId);
	}
	
	@Test
	public void deleteShowldDoNothingResouceNotFoundExceptionWhenIdDoesNotExists() {
		
		
		Assertions.assertThrows(ResouceNotFoundException.class,()->{
			service.delete(nonExintingId);
			
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(nonExintingId);
	}

	@Test
	public void deleteShowldDoNothingDatabaseExceptionWhenIdDoesNotExists() {
		
		
		Assertions.assertThrows(DatabaseException.class,()->{
			service.delete(dependentId);
			
		});
		Mockito.verify(repository, Mockito.times(1)).deleteById(dependentId);
	}
	
}





