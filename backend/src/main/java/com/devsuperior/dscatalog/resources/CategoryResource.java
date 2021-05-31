package com.devsuperior.dscatalog.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscatalog.entities.Category;

@RestController //configurar a class para um controlador
@RequestMapping(value ="/categories")// annotation para a rota
public class CategoryResource {// implementa o controlador REST

	
	@GetMapping
	public ResponseEntity< List<Category>>findAll(){
		//responseEntity ira encapisular uma resposta Http;
		List<Category> list = new ArrayList<>();
		list.add(new Category(1L, "Books"));
		list.add(new Category(2L, "Electonics"));
		
		return ResponseEntity.ok().body(list);
		//.ok:resposta 200, requisição realizado com sucesso
		// body:para definir o corpo da resposta
		
		
	}
	
}
