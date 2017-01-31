package com.elementalprime.bft.jpa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON")
public class Person {
	
	@Id
	@Column(name = "PERSON_ID")
	private Integer id;
	
	@Column(name = "PERSON_NAME")
	private String personName;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "")
	private List<Reference> references;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", personName=" + personName + "]";
	}
	
	
}
