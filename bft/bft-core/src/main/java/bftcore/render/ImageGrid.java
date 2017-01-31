package bftcore.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bftcore.util.StringUtil;

public class ImageGrid {

	private Map<Integer, Map<Integer, ImageCell>> cellTextMap = new HashMap<Integer, Map<Integer, ImageCell>>();
	private List<CellRelationship> relationships = new ArrayList<ImageGrid.CellRelationship>();
	private Map<String, GridCoordinate> coordinateMap = new HashMap<String, GridCoordinate>();
	private int cellUsedCount;

	public void setCellText(int xCell, int yCell, String cellText, Color color) {

		if (!isCellEmpty(xCell, yCell)) {
			throw new IllegalArgumentException("Cell already has a value");
		}

		if (!this.cellTextMap.containsKey(yCell)) {
			this.cellTextMap.put(yCell, new HashMap<Integer, ImageCell>());
		}

		Map<Integer, ImageCell> row = this.cellTextMap.get(yCell);

		if (!row.containsKey(xCell)) {
			
			ImageCell cell = new ImageCell(cellText, color, xCell, yCell);
			
			row.put(xCell, cell);
			this.coordinateMap.put(cellText, new GridCoordinate(xCell, yCell));
		}

		cellUsedCount++;
	}

	public int getNumberOfColumns() {

		int numberOfColumns = 0;

		Collection<Map<Integer, ImageCell>> rows = this.cellTextMap.values();

		for (Map<Integer, ImageCell> row : rows) {
			if (row.size() > numberOfColumns) {
				numberOfColumns = row.size();
			}
		}

		return numberOfColumns;
	}

	public int getNumberOfRows() {
		return this.cellTextMap.values().size();
	}

	public ImageCell getCell(int xIndex, int yIndex) {
		
		Map<Integer, ImageCell> row = cellTextMap.get(yIndex);
		
		if(row == null)
		{
			return null;
		}

		return cellTextMap.get(yIndex).get(xIndex);
	}

	public boolean isCellEmpty(int xIndex, int yIndex) {

		if (yIndex > this.cellTextMap.size()) {
			return true;
		}

		boolean found = (this.cellTextMap.containsKey(yIndex) && this.cellTextMap
				.get(yIndex).containsKey(xIndex));

		return !found;
	}

	@Override
	public String toString() {

		StringBuffer sb = new StringBuffer();

		List<Integer> sortedRowKeys = new ArrayList<Integer>();

		sortedRowKeys.addAll(this.cellTextMap.keySet());

		Collections.sort(sortedRowKeys);

		for (Integer rowIndex : sortedRowKeys) {

			Map<Integer, ImageCell> row = this.cellTextMap.get(rowIndex);

			List<Integer> sortedColumnKeys = new ArrayList<Integer>();

			sortedColumnKeys.addAll(row.keySet());

			Collections.sort(sortedColumnKeys);

			StringBuffer rowText = new StringBuffer();

			for (Integer columnIndex : sortedColumnKeys) {

				String cellText = row.get(columnIndex).getCellText();

				if (rowText.length() > 0) {
					rowText.append("\t");
				}

				if (StringUtil.isEmpty(cellText)) {
					rowText.append("-");
				} else {
					rowText.append(cellText);
				}
			}

			sb.append(rowText);
			sb.append("\n");
		}

		return sb.toString();
	}

	public void addRelationship(int parentXIndex, int parentYIndex, int xIndex,
			int yIndex) {
		this.relationships.add(new CellRelationship(parentXIndex, parentYIndex,
				xIndex, yIndex));
	}

	public class CellRelationship {

		private int parentXIndex;
		private int parentYIndex;
		private int xIndex;
		private int yIndex;

		public CellRelationship(int parentXIndex, int parentYIndex, int xIndex,
				int yIndex) {
			this.parentXIndex = parentXIndex;
			this.parentYIndex = parentYIndex;
			this.xIndex = xIndex;
			this.yIndex = yIndex;
		}

		public int getParentXIndex() {
			return parentXIndex;
		}

		public int getParentYIndex() {
			return parentYIndex;
		}

		public int getxIndex() {
			return xIndex;
		}

		public int getyIndex() {
			return yIndex;
		}

	}

	public List<CellRelationship> getRelationships() {
		return this.relationships;
	}

	public GridCoordinate getNodeCoordinates(String cellText) {
		if (!this.coordinateMap.containsKey(cellText)) {
			return null;
		}

		return this.coordinateMap.get(cellText);
	}

	public Collection<Map<Integer, ImageCell>> getRows() {
		return this.cellTextMap.values();
	}

	public Set<Integer> getRowIds() {
		return this.cellTextMap.keySet();
	}

	public Map<Integer, ImageCell> getRow(Integer rowId) {
		return this.cellTextMap.get(rowId);
	}

	public int getCellUsedCount() {
		return cellUsedCount;
	}

	public class ImageCell {
		private String cellText;
		private Color cellColor;
		private int xCell;
		private int yCell;

		public ImageCell(String cellText, Color cellColor, int xCell, int yCell) {
			super();
			this.cellText = cellText;
			this.cellColor = cellColor;
			this.xCell = xCell;
			this.yCell = yCell;
		}

		public String getCellText() {
			return cellText;
		}

		public Color getCellColor() {
			return cellColor;
		}

		public int getxCell() {
			return xCell;
		}

		public int getyCell() {
			return yCell;
		}
		
		

	}

}
