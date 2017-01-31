package bftcore.entity.testdata;

import java.util.ArrayList;
import java.util.List;

import bftcore.entity.Belief;

public class BeliefTestData {

	public static Belief getReligionTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = null;//new BeliefImpl();

		return religion;
	}

	public static Belief getConceptualDivisionTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = null;//new BeliefImpl(BeliefTypeEnum.C, name, TheisticTypeEnum.MONOTHEISM, comments);

		return religion;
	}

	public static Belief getSocietyTestData(String name) {

		List<String> comments = new ArrayList<String>();
		comments.add("Comment");

		Belief religion = null;//new BeliefImpl(BeliefTypeEnum.S, name, TheisticTypeEnum.MONOTHEISM, comments);

		return religion;
	}

}
