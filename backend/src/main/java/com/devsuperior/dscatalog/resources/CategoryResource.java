package com.devsuperior.dscatalog.resources;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.dto.CategoryDTO;
import com.devsuperior.dscatalog.services.CategoryService;

@RestController //configurar a class para um controlador
@RequestMapping(value ="/categories")// annotation para a rota
public class CategoryResource {// implementa o controlador REST

	@Autowired
	private CategoryService service;
	
	@GetMapping
	public ResponseEntity< List<CategoryDTO>>findAll(){
		//responseEntity ira encapisular uma resposta Http;
		
		List<CategoryDTO>list = service.findAll();
		return ResponseEntity.ok().body(list);
		//.ok:resposta 200, requisição realizado com sucesso
		// body:para definir o corpo da resposta 		
	}
	

	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoryDTO>findById(@PathVariable Long id){
		
		CategoryDTO dto = service.finById(id);
		return ResponseEntity.ok().body(dto);
		 		
	}
	
}
