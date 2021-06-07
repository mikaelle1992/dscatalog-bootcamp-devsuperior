package com.devsuperior.dscatalog.services;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exceptions.DatabaseException;
import com.devsuperior.dscatalog.services.exceptions.ResouceNotFoundException;

@Service //ela registra a class como um componente que vai participar do sistema de injeção de dependencias automatizados do spring
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)//operações que é somente  leitura
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest){
		Page<Category> list =repository.findAll(pageRequest);
		
		return list.map(x ->new CategoryDTO(x));		
	}

	@Transactional(readOnly = true)
	public CategoryDTO finById(Long id) {
		Optional<Category> obj = repository.findById(id);
		Category entity = obj.orElseThrow(() -> new ResouceNotFoundException("Entity not found"));
		//.orElseThrow permite definir uma chamade de exception
		
		return new CategoryDTO(entity);
	}
	
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity= repository.save(entity);
		
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO update(Long id,CategoryDTO dto ) {
		try {
		Category entity = repository.findById(id).get();
		entity.setName(dto.getName());
		entity= repository.save(entity);
		return new CategoryDTO(entity);
		
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
