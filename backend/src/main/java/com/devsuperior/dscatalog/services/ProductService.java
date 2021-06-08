package com.devsuperior.dscatalog.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.ProductDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.entities.Product;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.repositories.ProductRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResouceNotFoundException;

@Service //ela registra a class como um componente que vai participar do sistema de injeção de dependencias automatizados do spring
public class ProductService {
	
	@Autowired
	private ProductRepository repository;
	

	
	@Transactional(readOnly = true)//operações que é somente  leitura
	public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
		Page<Product> list =repository.findAll(pageRequest);
		
		return list.map(x ->new ProductDTO(x,x.getCategories()));		
	}

	@Transactional(readOnly = true)
	public ProductDTO finById(Long id) {
		Optional<Product> obj = repository.findById(id);
		
		Product entity = obj.orElseThrow(() -> new ResouceNotFoundException("Entity not found"));
		
		//.orElseThrow permite definir uma chamade de exception
		
		return new ProductDTO(entity, entity.getCategories());
	}
	
	@Transactional
	public ProductDTO insert(ProductDTO dto) {
		Product entity = new Product();
		//entity.setName(dto.getName());
	
		entity= repository.save(entity);
		
		return new ProductDTO(entity);
	}

	@Transactional
	public ProductDTO update(Long id,ProductDTO dto ) {
		try {
		Product entity = repository.findById(id).get();
		//entity.setName(dto.getName());
		entity= repository.save(entity);
		return new ProductDTO(entity);
		
		}
		catch(ResouceNotFoundException e){
			throw new ResouceNotFoundException("Id not found"+ id);
		}
		
	}

	// nao é necessario usar o @Transactional
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResouceNotFoundException("Id not found"+ id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		
	
	}
	
	}

	
}
