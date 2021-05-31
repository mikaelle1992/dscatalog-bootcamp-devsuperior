package com.devsuperior.dscatalog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscatalog.entities.Category;

@Repository // os objs da interface categoryRepository passa a ser gerenciado pelo spring 
public interface CategoryRepository extends JpaRepository< Category, Long>{

	
}
