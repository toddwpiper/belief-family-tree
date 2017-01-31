package com.elementalprime.bft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "HERITAGE")
public class Heritage {
	
	@Id
	@Column(name = "HERITAGE_ID")
	private Integer id;
	
	@Column(name = "HERITAGE_SEQ")
	private Integer heritageSequence;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_PERSON_ID")
	private Person parentPerson;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_BELIEF_ID")
	private Belief parentBelief;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Person getParentPerson() {
		return parentPerson;
	}

	public void setParentPerson(Person parentPerson) {
		this.parentPerson = parentPerson;
	}

	public Belief getParentBelief() {
		return parentBelief;
	}

	public void setParentBelief(Belief parentBelief) {
		this.parentBelief = parentBelief;
	}

}
