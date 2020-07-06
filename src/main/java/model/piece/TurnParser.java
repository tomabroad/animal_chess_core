package model.piece;

import model.Color;

public class TurnParser {
	public static Color toColor(String str) {
		if (str.equals("black")) {
			return Color.BLACK;
		} else {
			return Color.WHITE;
		}
	}
}
