package bftcore.render;

public class GridCoordinate {

	int gridX;
	int gridY;

	public GridCoordinate(int gridX, int gridY) {
		super();
		this.gridX = gridX;
		this.gridY = gridY;
	}

	public int getGridX() {
		return gridX;
	}

	public int getGridY() {
		return gridY;
	}

	@Override
	public String toString() {
		return "GridCoordinate [gridX=" + gridX + ", gridY=" + gridY + "]";
	}
	
	

}
