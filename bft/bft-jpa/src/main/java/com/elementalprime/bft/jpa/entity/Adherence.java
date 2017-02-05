package com.elementalprime.bft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.elementalprime.bft.jpa.enums.FaithAspectType;

@Entity
@Table(name = "ADHERENCE", schema="bft")
public class Adherence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADHERENCE_ID")
	private Integer id;
	
	@Column(name = "TYPE_CODE")
	@Enumerated(EnumType.STRING)
	private FaithAspectType faithAspectType;
	
	@Column(name = "COMMENT_TEXT")
	private String comment;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FaithAspectType getFaithAspectType() {
		return faithAspectType;
	}

	public void setFaithAspectType(FaithAspectType faithAspectType) {
		this.faithAspectType = faithAspectType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "AdherenceAspect [id=" + id + ", faithAspectType=" + faithAspectType + ", comment=" + comment + "]";
	}
	
	
}
