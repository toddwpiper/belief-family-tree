package bftcore.entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

import com.elementalprime.bft.jpa.enums.BeliefTypeEnum;

import bftcore.entity.impl.BeliefImpl;
import bftcore.enumeration.ValidationMessageEnum;
import bftcore.exception.ValidationException;
import bftcore.manager.BeliefFamilyTreeManager;
import bftcore.manager.impl.BeliefFamilyTreeManagerImpl;
import bftcore.util.StringUtil;

public class Executable {

	private static int TOKEN_INDEX_NAME;
	private static int TOKEN_INDEX_BELIEF_TYPE;
	// S_SRC NUM_GODS MULTI_WSHP TEMP_GOD ALL_GODS_EQUAL EQUAL UNIVERSE

	private static int TOKEN_INDEX_SINGLE_SOURCE;
	private static int TOKEN_INDEX_NUMBER_OF_GODS;
	private static int TOKEN_INDEX_MULTIPLE_WORSHIP;
	private static int TOKEN_INDEX_TEMPORARY_GOD;
	private static int TOKEN_INDEX_ALL_GODS_EQUAL;
	private static int TOKEN_INDEX_GODS_EQUAL_TO_UNIVERSE;
	private static int TOKEN_INDEX_PARENT_1;
	private static int TOKEN_INDEX_PARENT_2;
	private static int TOKEN_INDEX_PARENT_3;
	private static int TOKEN_INDEX_PARENT_4;
	private static int TOKEN_INDEX_PARENT_5;
	private static int TOKEN_INDEX_START_PERIOD;
	private static int TOKEN_INDEX_END_PERIOD;
	private static int TOKEN_INDEX_NAME_OF_GOD;
	private static int TOKEN_INDEX_NAME_OF_ETERNAL_LAW;
	private static int TOKEN_INDEX_PATRIARCH_1;
	private static int TOKEN_INDEX_PATRIARCH_2;
	private static int TOKEN_INDEX_PATRIARCH_3;
	private static int TOKEN_INDEX_PATRIARCH_4;
	private static int TOKEN_INDEX_PATRIARCH_5;
	private static int TOKEN_INDEX_NOTES;

	// private static String inputFilename =
	// "./src/main/resources/Belief Family Tree - Sheet1.csv";
	private static String inputFilename = "C:/Users/Family/Dropbox/Belief Family Tree v0.4.csv";
	private static String outputFilename = "C:/Users/Family/Dropbox/BeliefFamilyTree.svg";
	// private static String outputFilename =
	// "C:/Users/Family/Downloads/BeliefFamilyTree.svg";

	static {
		int index = 0;

		TOKEN_INDEX_NAME = index++;
		TOKEN_INDEX_BELIEF_TYPE = index++;
		TOKEN_INDEX_SINGLE_SOURCE = index++;
		TOKEN_INDEX_NUMBER_OF_GODS = index++;
		TOKEN_INDEX_MULTIPLE_WORSHIP = index++;
		TOKEN_INDEX_TEMPORARY_GOD = index++;
		TOKEN_INDEX_ALL_GODS_EQUAL = index++;
		TOKEN_INDEX_GODS_EQUAL_TO_UNIVERSE = index++;
		TOKEN_INDEX_PARENT_1 = index++;
		TOKEN_INDEX_PARENT_2 = index++;
		TOKEN_INDEX_PARENT_3 = index++;
		TOKEN_INDEX_PARENT_4 = index++;
		TOKEN_INDEX_PARENT_5 = index++;
		TOKEN_INDEX_START_PERIOD = index++;
		TOKEN_INDEX_END_PERIOD = index++;
		TOKEN_INDEX_NAME_OF_GOD = index++;
		TOKEN_INDEX_NAME_OF_ETERNAL_LAW = index++;
		TOKEN_INDEX_PATRIARCH_1 = index++;
		TOKEN_INDEX_PATRIARCH_2 = index++;
		TOKEN_INDEX_PATRIARCH_3 = index++;
		TOKEN_INDEX_PATRIARCH_4 = index++;
		TOKEN_INDEX_PATRIARCH_5 = index++;
		TOKEN_INDEX_NOTES = index++;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Executable().run(args);

		} catch (Throwable e) {
			e.printStackTrace(System.err);
		}
	}

	public void run(String[] args) throws Throwable {
		BeliefFamilyTreeManager bftManager = new BeliefFamilyTreeManagerImpl();

		File inputCSV = new File(inputFilename);

		FileReader fr;
		BufferedReader br;

		fr = new FileReader(inputCSV);
		br = new BufferedReader(fr);
		br.readLine();

		String line = null;
		int lineNumber = 0;

		while ((line = br.readLine()) != null) {

			lineNumber++;

			if (StringUtil.isEmpty(line)) {
				continue;
			}

			try {

				createBelief(bftManager, line);

			} catch (Throwable e) {
				new RuntimeException("[" + lineNumber + "] ", e).printStackTrace(System.err);
			}
		}

		br.close();
		fr.close();
		fr = new FileReader(inputCSV);
		br = new BufferedReader(fr);
		br.readLine(); // remove column names

		while ((line = br.readLine()) != null) {
			try {

				createRelationships(bftManager, line);

			} catch (Throwable e) {
				new RuntimeException("[" + lineNumber + "] ", e).printStackTrace(System.err);
			}
		}

		bftManager.renderTreeAsSVG(outputFilename);

	}

	private static void createBelief(BeliefFamilyTreeManager bftManager,
			String line) throws ValidationException {

		try {
			Belief belief = assembleBeliefFromCSVRow(line);
			bftManager.addBelief(belief);
		} catch (Throwable e) {
			throw new RuntimeException(" LINE [" + line + "] ", e);
		}
	}

	private static void createRelationships(BeliefFamilyTreeManager bftManager,
			String line) throws ValidationException {

		try {
			String beliefName = getValueFromCSVLine(line, TOKEN_INDEX_NAME);
			Set<String> parentNames = getParentNamesFromCSVLine(line);

			Belief belief = bftManager.getBeliefByName(beliefName);

			for (String parentName : parentNames) {
				if (!StringUtil.isEmpty(parentName)) {
					Belief parent = bftManager.getBeliefByName(parentName);

					if (parent == null) {
						throw new IllegalStateException(
								ValidationMessageEnum.PARENT_NOT_FOUND + "["
										+ parentName + "]");
					}

					bftManager.addParentRelationship(parent, belief);
				}
			}
		} catch (Throwable e) {
			throw new RuntimeException("[" + line + "] ", e);
		}
	}

	private static Set<String> getParentNamesFromCSVLine(String line) {
		String[] tokens = line.split(",");

		Set<String> parentNames = new HashSet<String>();

		if (tokens.length > TOKEN_INDEX_PARENT_1) {
			String parent = tokens[TOKEN_INDEX_PARENT_1].trim();
			parentNames.add(parent);
		}

		if (tokens.length > TOKEN_INDEX_PARENT_2) {
			String parent = tokens[TOKEN_INDEX_PARENT_2].trim();
			parentNames.add(parent);
		}

		if (tokens.length > TOKEN_INDEX_PARENT_3) {
			String parent = tokens[TOKEN_INDEX_PARENT_3].trim();
			parentNames.add(parent);
		}

		if (tokens.length > TOKEN_INDEX_PARENT_4) {
			String parent = tokens[TOKEN_INDEX_PARENT_4].trim();
			parentNames.add(parent);
		}

		if (tokens.length > TOKEN_INDEX_PARENT_5) {
			String parent = tokens[TOKEN_INDEX_PARENT_5].trim();
			parentNames.add(parent);
		}

		return parentNames;
	}

	private static String getValueFromCSVLine(String line, int fieldIndex) {
		String[] tokens = line.split(",");
		
		String name = null;
		
		if(tokens != null && tokens.length > fieldIndex)
		{
				name = tokens[fieldIndex].trim();
		}

		return name;
	}

	private static Belief assembleBeliefFromCSVRow(String line) {

		String NAME = getValueFromCSVLine(line, TOKEN_INDEX_NAME);
		String BELIEF_TYPE = getValueFromCSVLine(line, TOKEN_INDEX_BELIEF_TYPE);
		String SINGLE_SOURCE = getValueFromCSVLine(line,
				TOKEN_INDEX_SINGLE_SOURCE);
		String NUMBER_OF_MAJOR_GODS = getValueFromCSVLine(line,
				TOKEN_INDEX_NUMBER_OF_GODS);
		String MULTIPLE_WORSHIP = getValueFromCSVLine(line,
				TOKEN_INDEX_MULTIPLE_WORSHIP);
		String TEMPORARY_GOD = getValueFromCSVLine(line,
				TOKEN_INDEX_TEMPORARY_GOD);
		String ALL_GODS_EQUAL = getValueFromCSVLine(line,
				TOKEN_INDEX_ALL_GODS_EQUAL);
		String GODS_EQUAL_TO_UNIVERSE = getValueFromCSVLine(line,
				TOKEN_INDEX_GODS_EQUAL_TO_UNIVERSE);
		// String PARENT_1 = getValueFromCSVLine(line, TOKEN_INDEX_PARENT_1);
		// String PARENT_2 = getValueFromCSVLine(line, TOKEN_INDEX_PARENT_2);
		// String PARENT_3 = getValueFromCSVLine(line, TOKEN_INDEX_PARENT_3);
		// String PARENT_4 = getValueFromCSVLine(line, TOKEN_INDEX_PARENT_4);
		// String PARENT_5 = getValueFromCSVLine(line, TOKEN_INDEX_PARENT_5);
		String START_PERIOD = getValueFromCSVLine(line,
				TOKEN_INDEX_START_PERIOD);
		String END_PERIOD = getValueFromCSVLine(line, TOKEN_INDEX_END_PERIOD);
		String NAME_OF_GOD = getValueFromCSVLine(line, TOKEN_INDEX_NAME_OF_GOD);
		String NAME_OF_ETERNAL_LAW = getValueFromCSVLine(line,
				TOKEN_INDEX_NAME_OF_ETERNAL_LAW);
		String PATRIARCH_1 = getValueFromCSVLine(line, TOKEN_INDEX_PATRIARCH_1);
		String PATRIARCH_2 = getValueFromCSVLine(line, TOKEN_INDEX_PATRIARCH_2);
		String PATRIARCH_3 = getValueFromCSVLine(line, TOKEN_INDEX_PATRIARCH_3);
		String PATRIARCH_4 = getValueFromCSVLine(line, TOKEN_INDEX_PATRIARCH_4);
		String PATRIARCH_5 = getValueFromCSVLine(line, TOKEN_INDEX_PATRIARCH_5);
		String NOTES = getValueFromCSVLine(line, TOKEN_INDEX_NOTES);

		BeliefTypeEnum beliefType = getBeliefType(BELIEF_TYPE);

		Belief belief = new BeliefImpl(NAME, beliefType, SINGLE_SOURCE,
				NUMBER_OF_MAJOR_GODS, MULTIPLE_WORSHIP, TEMPORARY_GOD,
				ALL_GODS_EQUAL, GODS_EQUAL_TO_UNIVERSE, START_PERIOD,
				END_PERIOD, NAME_OF_GOD, NAME_OF_ETERNAL_LAW, PATRIARCH_1,
				PATRIARCH_2, PATRIARCH_3, PATRIARCH_4, PATRIARCH_5, NOTES);

		return belief;
	}

	private static BeliefTypeEnum getBeliefType(String beliefTypeValue) {
		if (StringUtil.isEmpty(beliefTypeValue)) {
			return null;
		}

		return BeliefTypeEnum.valueOf(beliefTypeValue);
	}

}
