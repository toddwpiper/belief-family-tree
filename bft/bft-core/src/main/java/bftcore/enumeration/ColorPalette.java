package bftcore.enumeration;

import java.awt.Color;
import java.awt.Paint;

import bftcore.entity.Belief;
import bftcore.entity.tree.Node;

public class ColorPalette {

	public static Color NAVY_BLUE = new Color(0, 0, 100);
	public static Color WHITE = new Color(255, 255, 255);
	public static Color LIGHT_CYAN = new Color(175, 238, 238);

	public static final Paint COLOR_TEXT = Color.BLUE;
	public static final Paint COLOR_CELL = LIGHT_CYAN;
	public static final Paint COLOR_LINE = LIGHT_CYAN;
	public static final Paint BACK_GROUND = NAVY_BLUE;

	public static Color getCellColor(Node parent) {

		Belief belief = (Belief) parent;
		TheisticTypeEnum theisticType = belief.getTheisticType();
		
		if(theisticType == null)
		{
			return Color.MAGENTA;
		}

		Color color = null;
		
		switch (theisticType) {
		case DUALISM: {
			color = Color.GREEN;
			break;
		}
		
		case HENOTHEISM: {
			color = Color.YELLOW;
			break;
		}

		case MONOLATRISM: {
			color = Color.GRAY;
			break;
		}

		case MONOTHEISM: {
			color = LIGHT_CYAN;
			break;
		}

		case POLYTHEISM: {
			color = Color.ORANGE;
			break;
		}

		case NONTHEISM: {
			color = WHITE;
			break;
		}

		}
		
		

		return color;
	}

}
