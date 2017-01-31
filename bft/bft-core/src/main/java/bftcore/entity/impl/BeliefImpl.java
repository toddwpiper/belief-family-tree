package bftcore.entity.impl;

import com.elementalprime.bft.jpa.enums.BeliefTypeEnum;

import bftcore.entity.Belief;
import bftcore.entity.tree.Node;
import bftcore.entity.tree.impl.NodeImpl;
import bftcore.enumeration.TheisticTypeEnum;

public class BeliefImpl extends NodeImpl implements Belief {

	private BeliefTypeEnum beliefType;
	private TheisticTypeEnum theisticTypeEnum;
	private String singleSource;
	private String multipleWorship;
	private String temporaryGod;
	private String allGodsAreEqual;
	private String godsAreEqualToUniverse;
	private String startPeriod;
	private String endPeriod;
	private String nameOfGod;
	private String nameOfEternalLaw;
	private String patriarch1;
	private String patriarch2;
	private String patriarch3;
	private String patriarch4;
	private String patriarch5;
	private String notes;
	private String numberOfGods;

	public BeliefImpl(String name, BeliefTypeEnum beliefType,
			String singleSource, String numberOfGods, String multipleWorship,
			String temporaryGod, String allGodsAreEqual,
			String godsAreEqualToUniverse, String startPeriod,
			String endPeriod, String nameOfGod, String nameOfEternalLaw,
			String patriarch1, String patriarch2, String patriarch3,
			String patriarch4, String patriarch5, String note) {

		super(name);

		this.singleSource = singleSource;
		this.numberOfGods = numberOfGods;
		this.multipleWorship = multipleWorship;
		this.temporaryGod = temporaryGod;
		this.allGodsAreEqual = allGodsAreEqual;
		this.godsAreEqualToUniverse = godsAreEqualToUniverse;
		this.startPeriod = startPeriod;
		this.endPeriod = endPeriod;
		this.nameOfGod = nameOfGod;
		this.nameOfEternalLaw = nameOfEternalLaw;
		this.patriarch1 = patriarch1;
		this.patriarch2 = patriarch2;
		this.patriarch3 = patriarch3;
		this.patriarch4 = patriarch4;
		this.patriarch5 = patriarch5;
		this.notes = note;

		try {
			this.theisticTypeEnum = TheisticTypeEnum.calculateType(
					singleSource, numberOfGods, multipleWorship, temporaryGod,
					allGodsAreEqual, godsAreEqualToUniverse);
		} catch (IllegalStateException e) {
			throw new IllegalStateException("Belief [" + name + "]", e);
		}
	}

	public BeliefTypeEnum getBeliefType() {
		return beliefType;
	}

	public int compareTo(Node compare) {

		int comparison = 0;

		if (this.theisticTypeEnum != null
				&& ((Belief) compare).getTheisticType() != null) {
			comparison = this.theisticTypeEnum.compareTo(theisticTypeEnum);
		} else if (this.theisticTypeEnum != null
				&& ((Belief) compare).getTheisticType() == null) {
			comparison = 1;
		} else {
			comparison = -1;
		}

		if (comparison == 0) {
			comparison = super.compareTo(compare);
		}

		return comparison;
	}

	public TheisticTypeEnum getTheisticType() {

		return this.theisticTypeEnum;
	}

	public TheisticTypeEnum getTheisticTypeEnum() {
		return theisticTypeEnum;
	}

	public String getSingleSource() {
		return singleSource;
	}

	public String getMultipleWorship() {
		return multipleWorship;
	}

	public String getStartPeriod() {
		return startPeriod;
	}

	public String getEndPeriod() {
		return endPeriod;
	}

	public String getNameOfGod() {
		return nameOfGod;
	}

	public String getNameOfEternalLaw() {
		return nameOfEternalLaw;
	}

	public String getPatriarch1() {
		return patriarch1;
	}

	public String getPatriarch2() {
		return patriarch2;
	}

	public String getPatriarch3() {
		return patriarch3;
	}

	public String getPatriarch4() {
		return patriarch4;
	}

	public String getPatriarch5() {
		return patriarch5;
	}

	public String getNotes() {
		return notes;
	}

	public String getNumberOfGods() {
		return numberOfGods;
	}

	public String getAllGodsAreEqual() {
		return allGodsAreEqual;
	}

	public String getGodsAreEqualToUniverse() {
		return godsAreEqualToUniverse;
	}

	public String getTemporaryGod() {
		return temporaryGod;
	}

}
