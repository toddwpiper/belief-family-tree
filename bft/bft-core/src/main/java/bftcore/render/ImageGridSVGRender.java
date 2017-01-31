package bftcore.render;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import bftcore.enumeration.ColorPalette;
import bftcore.render.ImageGrid.ImageCell;

public class ImageGridSVGRender {
	
	public static byte[] render(ImageGrid grid) throws IOException {

		DOMImplementation dom = GenericDOMImplementation.getDOMImplementation();
		Document doc = dom.createDocument(null, "svg", null);
		SVGGraphics2D g2d = new SVGGraphics2D(doc);

		// SVGGraphics2D g2d = new SVGGraphics2D(ctx, false);

		generateSVGImage(g2d, grid);

		// Write the generated SVG document to a file
		try {
			FileWriter file = new FileWriter("out.svg");

			PrintWriter writer = new PrintWriter(file);
			g2d.stream(writer);
			writer.close();
		} catch (IOException ioe) {
			System.err.println("IO problem: " + ioe.toString());
		}

		// Finally, stream out SVG to the standard output using
		// UTF-8 encoding.
		boolean useCSS = false; // we want to use CSS style attributes

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer bosw = new OutputStreamWriter(baos, "UTF-8");
		g2d.stream(bosw, useCSS);
		byte[] svgBinary = baos.toByteArray();
		baos.close();

		return svgBinary;
	}

	private static void generateSVGImage(SVGGraphics2D g2d, ImageGrid grid) {

		int rectWidth = 300;
		int rectHeight = 20;
		int rectXPadding = 60;
		int rectYPadding = 10;

		int cellHeight = rectHeight + (rectYPadding * 2);
		int cellWidth = rectWidth + (rectXPadding * 2);

		int numberOfColumns = grid.getNumberOfColumns();
		int numberOfRows = grid.getNumberOfRows();

		int canvasHeight = (numberOfRows + 3) * cellHeight;
		int canvasWidth = (numberOfColumns + 3) * cellWidth;

		/*
		 * background
		 */
		g2d.setPaint(ColorPalette.BACK_GROUND);
		g2d.fill(new Rectangle2D.Double(0, 0, canvasWidth, canvasHeight));

		/*
		 * Cells
		 */

		Set<Integer> rowIds = grid.getRowIds();

		for (Integer rowId : rowIds) {

			Map<Integer, ImageCell> row = grid.getRow(rowId);

			Set<Integer> columnIds = row.keySet();

			for (Integer columnId : columnIds) {

				int xIndex = columnId;
				int yIndex = rowId;

				// for (int yIndex = 0; yIndex < yCellSize; yIndex++) {
				// for (int xIndex = 0; xIndex < xCellSize; xIndex++) {

				if (!grid.isCellEmpty(xIndex, yIndex)) {
					int x = (xIndex * cellWidth) + rectXPadding;
					int y = (yIndex * cellHeight) + rectYPadding;
					
					ImageCell cell = grid.getCell(xIndex, yIndex); 

					Shape rectangle = new Rectangle2D.Double(x, y, rectWidth,
							rectHeight);
					g2d.setPaint(cell.getCellColor());
					g2d.fill(rectangle);
					g2d.setPaint(ColorPalette.COLOR_TEXT);
					// g2d.draw(rectangle);
					// g2d.translate(x, y);
					g2d.drawString(cell.getCellText(), (x + 5),
							(y + (rectHeight / 6 * 5)));
				}
			}
		}

		List<ImageGrid.CellRelationship> relationships = grid
				.getRelationships();

		for (ImageGrid.CellRelationship cellRelationship : relationships) {

			int x1 = (cellRelationship.getParentXIndex() * cellWidth)
					+ rectWidth + rectXPadding;
			int y1 = (cellRelationship.getParentYIndex() * cellHeight)
					+ (rectHeight / 2) + rectYPadding;
			int x2 = (cellRelationship.getxIndex() * cellWidth) + rectXPadding;
			int y2 = (cellRelationship.getyIndex() * cellHeight)
					+ (rectHeight / 2) + rectYPadding;

			ImageCell cell = grid.getCell(cellRelationship.getParentXIndex(), cellRelationship.getParentYIndex());
			
			g2d.setPaint(cell.getCellColor());
			g2d.drawLine(x1, y1, x2, y2);
		}

		g2d.setSVGCanvasSize(new Dimension(canvasWidth, canvasHeight));

	}

}
