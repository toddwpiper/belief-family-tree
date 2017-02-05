package com.elementalprime.bft.jpa.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.springframework.beans.BeanUtils;

import com.elementalprime.bft.jpa.enums.BeliefType;
import com.elementalprime.bft.jpa.enums.DivinityEquality;
import com.elementalprime.bft.jpa.enums.WorshipType;

@Entity
@Table(name = "BELIEF", schema = "bft")
public class Belief implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "BELIEF_HERITAGES", schema = "bft", joinColumns = @JoinColumn(name = "BELIEF_ID", referencedColumnName = "BELIEF_ID"), inverseJoinColumns = @JoinColumn(name = "HERITAGE_ID", referencedColumnName = "HERITAGE_ID"))
	private List<Heritage> heritages;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BELIEF_REFERENCES", schema = "bft", joinColumns = @JoinColumn(name = "BELIEF_ID", referencedColumnName = "BELIEF_ID"), inverseJoinColumns = @JoinColumn(name = "REFERENCE_ID", referencedColumnName = "REFERENCE_ID"))
	private List<Reference> references;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BELIEF_ADHERENCES", schema = "bft", joinColumns = @JoinColumn(name = "BELIEF_ID", referencedColumnName = "BELIEF_ID"), inverseJoinColumns = @JoinColumn(name = "ADHERENCE_ID", referencedColumnName = "ADHERENCE_ID"))
	private List<Adherence> adherences;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BELIEF_CONCEPTS", schema = "bft", joinColumns = @JoinColumn(name = "BELIEF_ID", referencedColumnName = "BELIEF_ID"), inverseJoinColumns = @JoinColumn(name = "CONCEPT_ID", referencedColumnName = "CONCEPT_ID"))
	private List<Concept> concepts;

	/*
	 * Religion Details
	 */
	@Column(name = "WORSHIP_TYPE")
	@Enumerated(EnumType.STRING)
	private WorshipType worshipType;

	@Column(name = "DIVINE_LAW_NAME")
	private String divineLawName;

	@Column(name = "DIVINITY_EQUALITY_TYPE")
	@Enumerated(EnumType.STRING)
	private DivinityEquality divinityEquality;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "BELIEF_DIVINITIES", schema = "bft", joinColumns = @JoinColumn(name = "BELIEF_ID", referencedColumnName = "BELIEF_ID"), inverseJoinColumns = @JoinColumn(name = "DIVINITY_ID", referencedColumnName = "DIVINITY_ID"))
	private List<Divinity> divinities;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BeliefType getBeliefType() {
		return beliefType;
	}

	public void setBeliefType(BeliefType beliefType) {
		this.beliefType = beliefType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Calendar getStartingPeriod() {
		return startingPeriod;
	}

	public void setStartingPeriod(Calendar startingPeriod) {
		this.startingPeriod = startingPeriod;
	}

	public Calendar getEndingPeriod() {
		return endingPeriod;
	}

	public void setEndingPeriod(Calendar endingPeriod) {
		this.endingPeriod = endingPeriod;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public List<Heritage> getHeritages() {
		return heritages;
	}

	public void setHeritages(List<Heritage> heritages) {
		this.heritages = heritages;
	}

	public List<Reference> getReferences() {
		return references;
	}

	public void setReferences(List<Reference> references) {
		this.references = references;
	}

	public List<Adherence> getAdherences() {
		return adherences;
	}

	public void setAdherenceAspect(List<Adherence> adherences) {
		this.adherences = adherences;
	}

	public List<Concept> getConcepts() {
		return concepts;
	}

	public void setConcepts(List<Concept> concepts) {
		this.concepts = concepts;
	}

	public String getDivineLawName() {
		return divineLawName;
	}

	public void setDivineLawName(String divineLawName) {
		this.divineLawName = divineLawName;
	}

	public DivinityEquality getDivinityEquality() {
		return divinityEquality;
	}

	public void setDivinityEquality(DivinityEquality divinityEquality) {
		this.divinityEquality = divinityEquality;
	}

	public List<Divinity> getDivinities() {
		return divinities;
	}

	public void setDivinities(List<Divinity> divinities) {
		this.divinities = divinities;
	}

	@Override
	public String toString() {
		return "Belief [id=" + id + ", beliefType=" + beliefType + ", name=" + name + ", startingPeriod="
				+ startingPeriod + ", endingPeriod=" + endingPeriod + ", commentText=" + commentText + ", worshipType="
				+ worshipType + ", divineLawName=" + divineLawName + ", divinityEquality=" + divinityEquality
				+ ", divinities=" + divinities + "]";
	}

	public Belief() {
		 
	}
	
	public Belief(Belief belief) {
		 
		BeanUtils.copyProperties(belief, this);
	}
	
	
}
