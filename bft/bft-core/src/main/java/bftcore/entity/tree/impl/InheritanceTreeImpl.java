package bftcore.entity.tree.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bftcore.entity.tree.InheritanceTree;
import bftcore.entity.tree.Node;
import bftcore.enumeration.ValidationMessageEnum;
import bftcore.exception.ValidationException;

public class InheritanceTreeImpl implements InheritanceTree {
	private Map<String, Node> nodeMap = new HashMap<String, Node>();
	private Map<Node, Set<Node>> parentMap = new HashMap<Node, Set<Node>>();
	private Map<Node, Set<Node>> childMap = new HashMap<Node, Set<Node>>();
	private Map<Integer, Set<Node>> generations = new HashMap<Integer, Set<Node>>();

	public void addNode(Node node) {

		if (node == null) {
			throw new IllegalArgumentException("Node is null");
		}

		if (!nodeMap.containsKey(node)) {
			nodeMap.put(node.getName(), node);
		}

		if (!parentMap.containsKey(node)) {
			parentMap.put(node, new HashSet<Node>());
		}

		/*
		 * Add to new gen
		 */
		if (!this.generations.containsKey(1)) {
			this.generations.put(1, new HashSet<Node>());
		}

		Set<Node> afterGenerationSet = this.generations.get(1);
		afterGenerationSet.add(node);
	}

	public Node getNodeByName(String name) {
		return this.nodeMap.get(name);
	}

	public void addParentRelationship(Node parent, Node child)
			throws ValidationException {

		if (parent == null) {
			throw new IllegalArgumentException("Parent is null");
		}

		if (child == null) {
			throw new IllegalArgumentException("Child is null");
		}

		/*
		 * Parent Check
		 */
		if (!this.nodeMap.containsKey(parent.getName())
				|| !this.nodeMap.get(parent.getName()).equals(parent)) {
			throw new ValidationException(
					ValidationMessageEnum.PARENT_NOT_FOUND, parent.getName());
		}

		/*
		 * Child Check
		 */
		if (!this.nodeMap.containsKey(child.getName())
				|| !this.nodeMap.get(child.getName()).equals(child)) {
			throw new ValidationException(
					ValidationMessageEnum.CHILD_NOT_FOUND, child.getName());
		}

		/*
		 * Time Paradox Check
		 */
		if (child.equals(parent)) {
			throw new ValidationException(
					ValidationMessageEnum.CHILD_IS_PARENT, child.getName());
		}

		/*
		 * Incest Check
		 */
		if (isChildAnAncestor(parent, child)) {
			throw new ValidationException(
					ValidationMessageEnum.CHILD_IS_ANCESTOR, child.getName());
		}

		if (parent != null) {
			Set<Node> parentSet = parentMap.get(child);
			parentSet.add(parent);

			if (!childMap.containsKey(parent)) {
				childMap.put(parent, new HashSet<Node>());
			}

			Set<Node> childSet = childMap.get(parent);
			childSet.add(child);
		}

		updateGeneration(child);

	}

	private void updateGeneration(Node node) {

		/*
		 * Get after gen
		 */
		int afterGeneration = getGeneration(node);

		/*
		 * remove from old gen
		 */

		Set<Integer> generationKeys = this.generations.keySet();

		for (Integer generationKey : generationKeys) {

			Set<Node> generation = this.generations.get(generationKey);

			if (generation.contains(node) && generationKey != afterGeneration) {
				generation.remove(node);
			}

		}

		/*
		 * Add to new gen
		 */
		if (!this.generations.containsKey(afterGeneration)) {
			this.generations.put(afterGeneration, new HashSet<Node>());
		}

		Set<Node> afterGenerationSet = this.generations.get(afterGeneration);
		afterGenerationSet.add(node);

		/*
		 * Now handle the next generation of children
		 */
		Set<Node> children = this.childMap.get(node);

		if (children != null) {
			for (Node child : children) {
				updateGeneration(child);
			}
		}

	}

	private boolean isChildAnAncestor(Node parent, Node child) {

		if (parent == null) {
			throw new IllegalArgumentException("Parent is null");
		}

		if (child == null) {
			throw new IllegalArgumentException("Child is null");
		}

		Set<Node> grandParents = this.getParents(parent);

		boolean found = false;

		for (Node grandParent : grandParents) {
			if (child.equals(grandParent)) {
				found = true;
			} else {
				found = isChildAnAncestor(grandParent, child);
			}

			if (found) {
				break;
			}
		}

		return found;
	}

	public Set<Node> getParents(Node child) {
		return this.parentMap.get(child);
	}

	public Set<Node> getChildren(Node parent) {
		return this.childMap.get(parent);
	}

	public boolean hasNode(Node node) {
		return this.nodeMap.containsValue(node);
	}

	public int getNumberOfGenerations() {
		return generations.size();
	}

	public int getGeneration(Node node) {
		if (node == null) {
			throw new IllegalArgumentException("node is null");
		}

		if (!this.nodeMap.containsValue(node)) {
			throw new IllegalArgumentException("Node is not in map ["
					+ node.getName() + "]");
		}

		int maxParentGeneration = 0;
		
		/*
		 * Parent Map
		 */
		if (this.parentMap.containsKey(node)) {

			Set<Node> parents = this.parentMap.get(node);

			for (Node parent : parents) {

				int parentGeneration = getGeneration(parent);

				if (parentGeneration > maxParentGeneration) {
					maxParentGeneration = parentGeneration;
				}
			}
		}

		return maxParentGeneration +1;
	}

	public Set<Node> getGenerationOfNodes(int generationIndex) {

		return this.generations.get(generationIndex);
	}

	public int getMaxNumberNodesPerGeneration() {

		int max = 0;

		Collection<Set<Node>> generations = this.generations.values();

		for (Set<Node> generation : generations) {

			if (generation.size() > max) {
				max = generation.size();
			}
		}

		return max;
	}

	public Set<Node> getChildren(Node parent, int generation) {

		Set<Node> generationChildren = new HashSet<Node>();

		Set<Node> allChildren = this.getChildren(parent);

		for (Node child : allChildren) {

			if (getGeneration(child) == generation) {
				generationChildren.add(child);
			}
		}

		return generationChildren;
	}

	public Set<Node> getChildrenByPrimaryParent(Node parent) {

		int parentGeneration = getGeneration(parent);

		Set<Node> generationChildren = new HashSet<Node>();

		Set<Node> allChildren = this.getChildren(parent);

		if (allChildren != null) {

			for (Node child : allChildren) {

				int childGeneration = getGeneration(child);

				if (childGeneration == (parentGeneration + 1)) {
					generationChildren.add(child);
				}
			}
		}

		return generationChildren;
	}

	public Collection<Node> getNodes() {
		return this.nodeMap.values();
	}

}
