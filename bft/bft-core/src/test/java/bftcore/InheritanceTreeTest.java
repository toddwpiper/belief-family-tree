package bftcore;

import java.util.Collection;

import junit.framework.Assert;

import org.junit.Test;

import bftcore.entity.tree.InheritanceTree;
import bftcore.entity.tree.Node;
import bftcore.entity.tree.impl.InheritanceTreeImpl;
import bftcore.entity.tree.impl.NodeImpl;
import bftcore.exception.ValidationException;

public class InheritanceTreeTest {

	@Test
	public void testAddNode() throws Exception{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();

		/*
		 * Test Data
		 */
		Node node = new NodeImpl("Node");

		tree.addNode(node);

		/*
		 * Verify
		 */
		Node retrieved = tree.getNodeByName(node.getName());

		Assert.assertNotNull(retrieved);
		Assert.assertEquals(node, retrieved);
	}

	@Test
	public void testAddNodeParent() throws Exception{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();

		/*
		 * Test Data
		 */
		Node parent = new NodeImpl("parent");
		tree.addNode(parent);

		Node child = new NodeImpl("child");
		tree.addNode(child);
		tree.addParentRelationship(parent, child);

		/*
		 * Verify
		 */
		Collection<Node> retrievedList = tree.getParents(child);

		Assert.assertEquals(1, retrievedList.size());

		Node retrieved = retrievedList.iterator().next();
		Assert.assertNotNull(retrieved);
		Assert.assertEquals(parent, retrieved);
	}

	@Test
	public void testAddNodeMultipleParents() throws Exception{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();

		/*
		 * Test Data
		 */
		Node parent1 = new NodeImpl("parent1");
		tree.addNode(parent1);

		Node parent2 = new NodeImpl("parent2");
		tree.addNode(parent2);

		Node child = new NodeImpl("child");
		tree.addNode(child);
		tree.addParentRelationship(parent1, child);
		tree.addParentRelationship(parent2, child);

		/*
		 * Verify
		 */
		Collection<Node> retrievedList = tree.getParents(child);

		Assert.assertEquals(2, retrievedList.size());

		boolean found1 = false;
		boolean found2 = false;

		for (Node retrieved : retrievedList) {
			Assert.assertNotNull(retrieved);

			if (parent1.equals(retrieved)) {
				found1 = true;
			}

			if (parent2.equals(retrieved)) {
				found2 = true;
			}

		}

		Assert.assertTrue(found1);
		Assert.assertTrue(found2);
	}
	
	@Test
	public void testGetGeneration() throws Exception
	{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();

		/*
		 * Test Data
		 */
		Node gen1A = new NodeImpl("gen1A"); //gen 1
		tree.addNode(gen1A);
		Assert.assertEquals(1, tree.getGeneration(gen1A));

		Node gen1B = new NodeImpl("gen1B"); //gen 1
		tree.addNode(gen1B);
		Assert.assertEquals(1, tree.getGeneration(gen1B));

		Node gen1A2A = new NodeImpl("gen1A2A"); //gen 2
		tree.addNode(gen1A2A);
		tree.addParentRelationship(gen1A, gen1A2A);
		Assert.assertEquals(2, tree.getGeneration(gen1A2A));
		
		Node gen1AB2B = new NodeImpl("gen1AB2B"); //gen 2
		tree.addNode(gen1AB2B);
		tree.addParentRelationship(gen1A, gen1AB2B);
		tree.addParentRelationship(gen1B, gen1AB2B);
		Assert.assertEquals(2, tree.getGeneration(gen1AB2B));
		
		Node gen1A2A3A = new NodeImpl("gen1A2A3A"); //gen 3
		tree.addNode(gen1A2A3A);
		tree.addParentRelationship(gen1A2A, gen1A2A3A);
		Assert.assertEquals(3, tree.getGeneration(gen1A2A3A));
		
		Node gen1A3A = new NodeImpl("gen1A3A"); //gen 4
		tree.addNode(gen1A3A);
		tree.addParentRelationship(gen1A2A3A, gen1A3A);
		tree.addParentRelationship(gen1B, gen1A3A);
		Assert.assertEquals(4, tree.getGeneration(gen1A3A));		
	}
	
	@Test
	public void testIAmMyOwnFather() throws Exception
	{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();
		
		Node gen1A = new NodeImpl("gen1A"); //gen 1
		tree.addNode(gen1A);
		Assert.assertEquals(1, tree.getGeneration(gen1A));
		
		try
		{
			tree.addParentRelationship(gen1A, gen1A);
			Assert.fail("validaionException not thrown");
		}
		catch (ValidationException e) {
			// TODO: handle exception
		}
		
	}
	
	@Test
	public void testIAmMyOwnGrandFather() throws Exception
	{
		/*
		 * Tree
		 */
		InheritanceTree tree = new InheritanceTreeImpl();
		
		Node gen1A = new NodeImpl("gen1A"); //gen 1
		tree.addNode(gen1A);
		Assert.assertEquals(1, tree.getGeneration(gen1A));
		
		Node gen2A = new NodeImpl("gen2A"); //gen 2
		tree.addNode(gen2A);
		Assert.assertEquals(1, tree.getGeneration(gen2A));
		tree.addParentRelationship(gen1A, gen2A);
		
		try
		{
			tree.addParentRelationship(gen2A, gen1A);
			Assert.fail("validaionException not thrown");
		}
		catch (ValidationException e) {
			// TODO: handle exception
		}
		
	}

}
