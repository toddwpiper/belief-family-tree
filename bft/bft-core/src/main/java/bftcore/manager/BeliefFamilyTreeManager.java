package bftcore.manager;

import java.io.IOException;

import bftcore.entity.Belief;
import bftcore.exception.ValidationException;

public interface BeliefFamilyTreeManager {

	void addBelief(Belief belief) throws ValidationException;

	Belief getBeliefByName(String name);

	void addParentRelationship(Belief parent, Belief belief) throws ValidationException;

	void renderTreeAsSVG(String string) throws IOException;

}
