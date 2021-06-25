package com.devsuperior.dscatalog.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.tests.Factory;

@DataJpaTest
public class ProductRepositoryTests {
	
	
	
	@Autowired
	private ProductRepository repository;
	
	private long exintingId;
	private long nonExintingId;
	private long countProduct;
	
	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExintingId = 1000L;
		countProduct = 25L;
	}
	
	@Test
	public void saveShoulPersisWithAutoincrementWhenIdsNull () {
		Product product = Factory.createProduct();
		product.setId(null);
		product = repository.save(product);
		
		Assertions.assertNotNull(product.getId());
		Assertions.assertEquals(product.getId(), countProduct+1);
		
	}
	
	@Test 
	public void retunEmptyShoulOptionalWhenIdDoesNotExists() {
		
		
		repository.findById(nonExintingId);
		
		Optional<Product> result = repository.findById(nonExintingId);
		Assertions.assertFalse(result.isPresent());
		
	}
	
	@Test 
	public void retunNotEmptyShoulOptionalWhenIdExists() {
		

		repository.findById(exintingId);
		
		Optional<Product> result = repository.findById(exintingId);
		Assertions.assertTrue(result.isPresent());
		
	}
	
	@Test 
	public void deleteShoulDeleteObjectWhenIdDoesNotExists() {
		

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
