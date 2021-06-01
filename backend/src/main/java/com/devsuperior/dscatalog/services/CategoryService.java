package com.devsuperior.dscatalog.services;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;

@Service //ela registra a class como um componente que vai participar do sistema de injeção de dependencias automatizados do spring
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	@Transactional(readOnly = true)//operações que é somente  leitura
	public List<CategoryDTO> findAll(){
		List <Category> list =repository.findAll(); 
		
		return list.stream().map(x ->new CategoryDTO(x)).collect(Collectors.toList());
		
		

		
	}
}
