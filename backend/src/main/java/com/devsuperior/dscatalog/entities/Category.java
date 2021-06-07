package com.devsuperior.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;





@Entity
@Table(name = "tb_category")// definir o nome da tabela
public class Category implements Serializable{
	/**serializable:padrao da linguagem java para o obj java possa ser convertido em Bytes, para que o obj seja gravada em arquivo
	*,passar nas redes,uma pratica importante.
	*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAT;
	
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAT;
	

	public Category() {
		
	}
	
	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Instant getCreatedAT() {
		return createdAT;
	}

	public Instant getUpdatedAT() {
		return updatedAT;
	}
	
	@PrePersist
	public void prePersist() {
		createdAT = Instant.now();
		}
	
	@PreUpdate
	public void preUpdate() {
		updatedAT= Instant.now();
		}
	
	@Override
	public int hashCode() { // hashCode- comparar se um obj é igual a outro,a comparação rapida porem nao é 100% , por isso usar o equals
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {//
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
