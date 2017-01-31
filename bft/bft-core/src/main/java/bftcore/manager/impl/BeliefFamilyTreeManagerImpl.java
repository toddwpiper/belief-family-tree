package bftcore.manager.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import bftcore.entity.Belief;
import bftcore.entity.tree.InheritanceTree;
import bftcore.entity.tree.impl.InheritanceTreeImpl;
import bftcore.enumeration.ValidationMessageEnum;
import bftcore.exception.ValidationException;
import bftcore.manager.BeliefFamilyTreeManager;
import bftcore.render.InheritanceTreeRenderer;
import bftcore.util.StringUtil;

public class BeliefFamilyTreeManagerImpl implements BeliefFamilyTreeManager {
	private InheritanceTree inheritanceTree = new InheritanceTreeImpl();

	public void addBelief(Belief belief) throws ValidationException {

		if (belief == null) {
			throw new IllegalArgumentException("Belief is null");
		}

		validation(belief);

		this.inheritanceTree.addNode(belief);
	}

	public void addParentRelationship(Belief parent, Belief child)
			throws ValidationException {

		if (child == null) {
			throw new IllegalArgumentException("Child Belief is null");
		}

		if (parent == null) {
			throw new IllegalArgumentException("Parent Belief is null. Child ["+child.getName()+"]");
		}
		
		validation(parent, child);

		this.inheritanceTree.addParentRelationship(parent, child);
	}

	public void validation(Belief belief) throws ValidationException {

		if (belief == null) {
			throw new IllegalArgumentException("Belief is null");
		}

		/*
		 * Validate name
		 */
		if (StringUtil.isEmpty(belief.getName())) {
			throw new ValidationException(
					ValidationMessageEnum.VALUE_IS_MISSING, "Name");
		}

	}

	public void validation(Belief parent, Belief child)
			throws ValidationException {

		validation(child);
		
		validation(parent);

		/*
		 * Validate parents
		 */

		if (parent == null) {
			throw new IllegalArgumentException("Parent belief is null");
		}

		if (!inheritanceTree.hasNode(parent)) {
			throw new ValidationException(
					ValidationMessageEnum.PARENT_NOT_FOUND,
					parent.getName());

		}
		
		if (!inheritanceTree.hasNode(child)) {
			throw new ValidationException(
					ValidationMessageEnum.CHILD_NOT_FOUND,
					parent.getName());

		}
		
		if(parent.equals(child))
		{
			throw new ValidationException(
					ValidationMessageEnum.CHILD_IS_PARENT,
					child.getName());
		}
	}

	public Belief getBeliefByName(String name) {
		return (Belief)this.inheritanceTree.getNodeByName(name);
	}

	public void renderTreeAsSVG(String fileName) throws IOException {
		InheritanceTreeRenderer renderer = new InheritanceTreeRenderer(this.inheritanceTree);
		byte[] svgBinary = renderer.renderAsSVG();
		
		File svgFile = new File(fileName);
		
		FileOutputStream fos = new FileOutputStream(svgFile);
		fos.write(svgBinary);
		fos.close();
		
	}
	
	
}
