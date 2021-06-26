package com.devsuperior.dscatalog.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResouceNotFoundException;
import com.devsuperior.dscatalog.tests.Factory;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests {
	
	@InjectMocks
	private ProductService service;
	
	@Mock
	private ProductRepository repository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	private long exintingId;
	private long nonExintingId;
	private long dependentId;
	private PageImpl<Product> page;//PageImpl é um tipo concreto que representa uma pagina de dados
	private Product product;
	private Category category;
	private ProductDTO productDTO;
	
	@BeforeEach
	void setUp() throws Exception {
		exintingId = 1L;
		nonExintingId = 1000L;
		dependentId= 4L;
		product = Factory.createProduct();
		category = Factory.createCategory();
		page = new PageImpl<>(List.of(product));
		productDTO = Factory.createProductDTO();
		
		
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);// quando nao for void que retorna alguma coisa , primeiro vem o whwn e depois a ação 
		// lembrar que tem que ser o findAll que recebe o Page
		
		Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(product);
		
		Mockito.when(repository.findById(exintingId)).thenReturn(Optional.of(product));
		Mockito.when(repository.findById(nonExintingId )).thenReturn(Optional.empty());
		
		Mockito.when(repository.getOne(exintingId)).thenReturn(product);
		Mockito.when(repository.getOne(nonExintingId )).thenThrow(EntityNotFoundException.class);
		
		Mockito.when(categoryRepository.getOne(exintingId)).thenReturn(category);
		Mockito.when(categoryRepository.getOne(nonExintingId )).thenThrow(EntityNotFoundException.class);
		
		Mockito.doNothing().when(repository).deleteById(exintingId);// quando o metodo for void primeiro coloca a ação(doNothing()) depois o  When
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(nonExintingId);// configuração do Mockito
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(dependentId);
	}
	
	
	
	@Test 
	public void UpadateshowlReturnProductDTOWhenIdExists() {
		 
		
		ProductDTO obj = service.update(exintingId,productDTO);
		Assertions.assertNotNull(obj);
		
		Mockito.verify(repository, Mockito.times(1)).getOne(exintingId);
	}
	
	@Test 
	public void UpdateshowlThrowEntityNotFoundExceptionWhenIdDoesNotExists() {
		 
		
		Assertions.assertThrows(ResouceNotFoundException.class,()->{
			service.update(nonExintingId,productDTO);
				
		});
	
		Mockito.verify(repository, Mockito.times(1)).getOne(nonExintingId);
	}
	
	@Test 
	public void findByIdshowlReturnProductDTOWhenIdExists() {
		
		ProductDTO obj = service.findById(exintingId);

		Assertions.assertNotNull(obj);
		
		Mockito.verify(repository, Mockito.times(1)).findById(exintingId);
	}
	
	@Test 
	public void findByIdshowlThrowResouceNotFoundExceptionWhenIdDoesNotExists() {
		
		Assertions.assertThrows(ResouceNotFoundException.class,()->{
			 service.findById(nonExintingId);
				
		});
	
		Mockito.verify(repository, Mockito.times(1)).findById(nonExintingId);
	}
	
	
	@Test
	public void findAllPageShowlReturnPage() {
		Pageable pageable = PageRequest.of(0, 10);
		
		Page<ProductDTO> result = service.findAllPaged(pageable);
		Assertions.assertNotNull(result);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
		
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





