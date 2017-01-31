package bftcore.entity.testdata;

import bftcore.entity.tree.InheritanceTree;
import bftcore.entity.tree.Node;
import bftcore.entity.tree.impl.InheritanceTreeImpl;
import bftcore.exception.ValidationException;

public class InheritanceTreeTestData {

	public static InheritanceTree getMultifurcatedTree(int generations,
			int branches) throws ValidationException {
		InheritanceTree tree = new InheritanceTreeImpl();

		Node node1 = BeliefTestData.getReligionTestData("R1");
		tree.addNode(node1);

		createMultifurcatedGenerations(tree, node1, generations - 1, branches);

		return tree;
	}

	private static void createMultifurcatedGenerations(InheritanceTree tree,
			Node parent, int generations, int branches)
			throws ValidationException {

		for (int i = 0; i < branches; i++) {
			Node node1 = BeliefTestData.getReligionTestData(parent.getName()
					+ (i + 1));
			
			tree.addNode(node1);
			
			tree.addParentRelationship(parent, node1);

			if (generations > 0) {
				createMultifurcatedGenerations(tree, node1, generations - 1,
						branches);
			}
		}
	}
}
