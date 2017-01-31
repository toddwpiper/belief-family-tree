package bftcore.entity;

import bftcore.entity.tree.Node;
import bftcore.enumeration.TheisticTypeEnum;

public interface Belief extends Node {


	public String getNotes();

	public String getPatriarch5();

	public String getPatriarch4();

	public String getPatriarch3();

	public String getPatriarch2();

	public String getPatriarch1();

	public String getNameOfEternalLaw();

	public String getNameOfGod();

	public String getEndPeriod();

	public String getStartPeriod();

	public String getMultipleWorship();

	public String getSingleSource();

	public TheisticTypeEnum getTheisticTypeEnum();

	public TheisticTypeEnum getTheisticType();

	public String getGodsAreEqualToUniverse();

	public String getAllGodsAreEqual();

	public String getNumberOfGods();

}
