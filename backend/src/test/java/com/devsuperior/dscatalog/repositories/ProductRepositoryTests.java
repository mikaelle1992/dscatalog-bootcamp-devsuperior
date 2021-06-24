package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;

@DataJpaTest
public class ProductRepositoryTests {
	
	
	
	@Autowired
	private ProductRepository repository;
	
	private Long exintingId;
	private Long nonExintingId;
	
	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExintingId = 1000L;
	}
	
	@Test 
	public void deleteShoulDeleteObjectWhenIdExists() {
		

		repository.deleteById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertFalse(result.isPresent());
		
	}
	@Test 
	public void deleteShoulthrowEmptyResultDataAccessExceptionObjectWhenIdDoesNotExists() {
		
	
		Assertions.assertThrows(EmptyResultDataAccessException.class, ()->{
			
			repository.deleteById(nonExintingId );

			
		});
		
	}
}
