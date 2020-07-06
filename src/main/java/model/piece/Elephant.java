package model.piece;

import java.util.LinkedList;
import java.util.List;

import model.Color;
import model.Square;

public class Elephant extends Piece {

	public Elephant(Color color) {
		super(color);
	}

	@Override
	public List<Square> range(int i, int j) {
		List<Square> squares = new LinkedList<Square>();
		squares.add(new Square(i - 1, j - 1));
		squares.add(new Square(i - 1, j + 1));
		squares.add(new Square(i + 1, j - 1));
		squares.add(new Square(i + 1, j + 1));
		return squares;
	}

	@Override
	public String toString() {
		return this.getColor() == Color.BLACK ? "E" : "e";
	}
}
