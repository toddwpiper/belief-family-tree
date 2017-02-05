package com.elementalprime.bft.jpa.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PERSON", schema = "bft")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PERSON_ID")
	private Integer id;
	
	@Column(name = "PERSON_NAME")
	private String personName;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "PERSON_REFERENCES", schema="bft", joinColumns = @JoinColumn(name = "PERSON_ID", referencedColumnName = "PERSON_ID"), inverseJoinColumns = @JoinColumn(name = "REFERENCE_ID", referencedColumnName = "REFERENCE_ID"))
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
