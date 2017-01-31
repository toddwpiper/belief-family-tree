package bftcore.render;

import junit.framework.Assert;

import org.junit.Test;

import bftcore.entity.testdata.InheritanceTreeTestData;
import bftcore.entity.tree.InheritanceTree;
import bftcore.exception.ValidationException;

public class InheritanceTreeRendererTest {
	
	@Test
	public void testText() throws ValidationException
	{
		/*
		 * Get test data
		 */
		InheritanceTree tree = InheritanceTreeTestData.getMultifurcatedTree(4, 4);
		
		InheritanceTreeRenderer renderer = new InheritanceTreeRenderer(tree);
		String textRender = renderer.renderAsText();
		
		Assert.assertNotNull(textRender);
		
		System.out.println(textRender);
	}
	
	@Test
	public void testSVG() throws ValidationException
	{
		/*
		 * Get test data
		 */
		InheritanceTree tree = InheritanceTreeTestData.getMultifurcatedTree(4, 4);
		
		InheritanceTreeRenderer renderer = new InheritanceTreeRenderer(tree);
		byte[] textRender = renderer.renderAsSVG();
		
		Assert.assertNotNull(textRender);
	}
}
