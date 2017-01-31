package bftcore.render;

import java.awt.Color;

import junit.framework.Assert;

import org.junit.Test;

public class ImageGridTest {
	
	@Test
	public void testToString()
	{
		String[][] cellTextArray = {
		{"1a","1b","1c","1d","1e"},
		{"2a","2b","2c","2d","2e"},
		{"3a","3b","3c","3d","3e"},
		{"4a","4b","4c","4d","4e"},
		};
		
		ImageGrid grid = new ImageGrid();
		
		for (int rowIndex = 0; rowIndex < cellTextArray.length; rowIndex++) {
			String[] row = cellTextArray[rowIndex];
			
			for (int columnIndex = 0; columnIndex < row.length; columnIndex++) {
				String cellText = row[columnIndex];
				
				grid.setCellText(columnIndex, rowIndex, cellText, Color.CYAN);
			}			
		}
		
		
		StringBuffer expectedsb = new StringBuffer();
		expectedsb.append("1a\t1b\t1c\t1d\t1e\n");
		expectedsb.append("2a\t2b\t2c\t2d\t2e\n");
		expectedsb.append("3a\t3b\t3c\t3d\t3e\n");
		expectedsb.append("4a\t4b\t4c\t4d\t4e\n");
		
		String expected = expectedsb.toString();
		String actual = grid.toString();
		
		Assert.assertTrue(expected.equals(actual));
		Assert.assertEquals(expected, actual);
	}

}
