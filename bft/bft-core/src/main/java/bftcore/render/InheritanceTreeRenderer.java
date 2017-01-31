package bftcore.render;

import java.awt.Color;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.batik.svggen.SVGGraphics2DIOException;

import bftcore.entity.tree.InheritanceTree;
import bftcore.entity.tree.Node;
import bftcore.enumeration.ColorPalette;

public class InheritanceTreeRenderer {
	
	private ImageGrid grid;

	public InheritanceTreeRenderer(InheritanceTree tree) {
		renderGrid(tree);
	}
	
	public String renderAsText()
	{
		return this.grid.toString();
	}

	@SuppressWarnings({ "unused" })
	private void renderGrid(InheritanceTree tree) {
		
		if (tree == null) {
			throw new IllegalArgumentException("Node is null");
		}
		
		/*
		 * height is equal to 10 pixels per leaf plus a leaf spacing of 10 pixels
		 */
		int maxNumberNodesPerGeneration = tree.getMaxNumberNodesPerGeneration();
		
		/*
		 * width is equal to 50 pixels per leaf plus a leaf spacing of 10 pixels
		 */
		int numberOfGenerations = tree.getNumberOfGenerations();
				
		this.grid = new ImageGrid();
						
		/*
		 * Get Generation 1
		 */
		Set<Node> generationOfNodes = tree.getGenerationOfNodes(1);
		
		if(generationOfNodes == null)
		{
			throw new IllegalStateException("There is no generation [1]");
		}
		
		List<Node> parentList = new ArrayList<Node>();
		parentList.addAll(generationOfNodes);
		
		Collections.sort(parentList);
		
		int xIndex = 0;
		int yIndex = 0;
		Set<Node> nodesAlreadyDone = new HashSet<Node>();
		
		for (Node parent : parentList) {
			
			/*
			 * If cell is empty then set
			 */
			if(grid.isCellEmpty(xIndex, yIndex))
			{
				
				Color color = ColorPalette.getCellColor(parent);
				
				grid.setCellText(xIndex, yIndex, parent.getName(), color);
				
				nodesAlreadyDone.add(parent);
				
				int childYIndex = setChildrenNodes(grid, yIndex, xIndex, tree, parent, nodesAlreadyDone);
				
				if(yIndex == childYIndex)
				{
					yIndex++;
				}
				else
				{
					yIndex = childYIndex;
				}
			}
		}
		
		Collection<Node> nodes = tree.getNodes();
		
		/*
		 * check integrity
		 */
		if(tree.getNodes().size() != grid.getCellUsedCount())
		{
			for (Node node : nodes) {
				if(grid.getNodeCoordinates(node.getName()) == null)
				{
					throw new IllegalStateException("Node not in grid ["+node.getName()+"]");
				}
			}
		}
		
		/*
		 * Add all relationships
		 */
		for (Node node : nodes) {
			
			GridCoordinate nodeCoordinates = grid.getNodeCoordinates(node.getName());
			
			if(nodeCoordinates == null)
			{
				throw new IllegalStateException("No Coordinates found. ["+node.getName()+"]");
			}
			
			Set<Node> parents = tree.getParents(node);
			
			for (Node parent : parents) {
				
				GridCoordinate parentCoordinates = grid.getNodeCoordinates(parent.getName());
				
				if(parentCoordinates == null)
				{
					throw new IllegalStateException("No Coordinates found. ["+parent.getName()+"]");
				}
				
				grid.addRelationship(parentCoordinates.getGridX(), parentCoordinates.getGridY(), nodeCoordinates.getGridX(), nodeCoordinates.getGridY());
			}
		}
		
		
	}

	private int setChildrenNodes(ImageGrid grid, int parentYIndex, int parentXIndex, InheritanceTree tree,
			Node parent, Set<Node> nodesAlreadyDone) {
		
		int xIndex = parentXIndex+1;
		int yIndex = parentYIndex;
		
		/*
		 * Get Children
		 */
		Set<Node> children = tree.getChildrenByPrimaryParent(parent);
		
		List<Node> genList = new ArrayList<Node>();
		
		genList.addAll(children);
		
		/*
		 * Sort based on Parsimony Traits
		 */
		Collections.sort(genList);
		
		for (Node child : genList) {
			
			if(nodesAlreadyDone.contains(child))
			{
				continue;
			}
			
			/*
			 * If cell is empty then set 
			 */
			while(!grid.isCellEmpty(xIndex, yIndex) && yIndex < grid.getNumberOfRows())
			{
				yIndex++;	
			}
			
			
			if(grid.isCellEmpty(xIndex, yIndex))
			{
				Color color = ColorPalette.getCellColor(parent);
				
				grid.setCellText(xIndex, yIndex, child.getName(), color);
				
				nodesAlreadyDone.add(child);
			}
			else
			{
				throw new IllegalStateException("Not enough cells available in generation["+(xIndex+1)+"]");
			}
			
			int childYIndex = setChildrenNodes(grid, yIndex, xIndex, tree, child, nodesAlreadyDone);
			
			if(childYIndex == yIndex)
			{
				yIndex++;
			}
			else
			{
				yIndex = childYIndex;
			}
			
		}
		
		return yIndex;
	}
	
	public byte[] renderAsSVG()
	{
		try {
			return ImageGridSVGRender.render(this.grid);
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		} catch (SVGGraphics2DIOException e) {
			throw new IllegalStateException(e);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}
	
}
