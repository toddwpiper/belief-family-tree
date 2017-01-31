package com.elementalprime.bft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CONCEPT")
public class Concept {
	
	@Id
	@Column(name = "CONCEPT_ID")
	private Integer id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "COMMENT_TEXT")
	private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Concept [id=" + id + ", name=" + name + ", comment=" + comment + "]";
	}
	
	
	

}
