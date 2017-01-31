package bftcore.entity.testdata;

import java.util.ArrayList;
import java.util.List;

import bftcore.entity.Belief;
import bftcore.entity.impl.BeliefImpl;
import bftcore.enumeration.BeliefTypeEnum;
import bftcore.enumeration.TheisticTypeEnum;

public class BeliefTestData {

	public static Belief getReligionTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = new BeliefImpl(BeliefTypeEnum.R, name,
				TheisticTypeEnum.MONOTHEISM, comments);

		return religion;
	}

	public static Belief getConceptualDivisionTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = new BeliefImpl(BeliefTypeEnum.C, name, TheisticTypeEnum.MONOTHEISM, comments);

		return religion;
	}

	public static Belief getSocietyTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = new BeliefImpl(BeliefTypeEnum.S, name, TheisticTypeEnum.MONOTHEISM, comments);

		return religion;
	}

}
