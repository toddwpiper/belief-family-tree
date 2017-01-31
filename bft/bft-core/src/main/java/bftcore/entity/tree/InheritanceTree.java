package bftcore.entity.tree;

import java.util.Collection;
import java.util.Set;

import bftcore.exception.ValidationException;

public interface InheritanceTree {

	void addNode(Node node) throws ValidationException;

	Node getNodeByName(String name);

	void addParentRelationship(Node parent, Node child) throws ValidationException;

	Set<Node> getParents(Node child);

	boolean hasNode(Node node);

	Set<Node> getGenerationOfNodes(int i);
	
	int getGeneration(Node node);

	int getMaxNumberNodesPerGeneration();

	int getNumberOfGenerations();

	Set<Node> getChildren(Node parent, int i);

	Set<Node> getChildrenByPrimaryParent(Node parent);

	Collection<Node> getNodes();
}
