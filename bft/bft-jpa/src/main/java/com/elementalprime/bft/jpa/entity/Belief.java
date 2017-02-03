package com.elementalprime.bft.jpa.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.elementalprime.bft.jpa.enums.BeliefType;
import com.elementalprime.bft.jpa.enums.DivinityEquality;
import com.elementalprime.bft.jpa.enums.WorshipType;

@Entity
@Table(name = "BELIEF")
public class Belief {
	
	@Id
	@Column(name = "BELIEF_ID")
	private Integer id;
	
	@Column(name = "BELIEF_TYPE")
	@Enumerated(EnumType.STRING)
	private BeliefType beliefType;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STARTING_PERIOD")
	private Calendar startingPeriod;
	
	@Column(name = "ENDING_PERIOD")
	private Calendar endingPeriod;
	
	@Column(name = "COMMENT_TEXT")
	private String commentText;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "")
	private List<Heritage> heritages;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "")
	private List<Reference> references;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "")
	private List<AdherenceAspect> adherenceAspect;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "")
	private List<Concept> concepts;
	
	/*
	 * Religion Details
	 */
	@Column(name = "WORSHIP_TYPE")
	private WorshipType worshipType;

	@Column(name = "DIVINE_LAW_NAME")
	private String divineLawName;
	
	@Column(name = "DIVINITY_EQUALITY_TYPE")
	private DivinityEquality divinityEquality;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "")
	private List<Divinity> divinities;
}
