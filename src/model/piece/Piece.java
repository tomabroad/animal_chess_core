package model.piece;

import java.util.List;

import model.Color;
import model.Square;

/**
 * immutable
 *
 */
public abstract class Piece implements Comparable<Piece> {
	private Color color;

	public Piece(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	/**
	 * TODO: refactor
	 * 
	 * @return
	 */
	public Piece switchColor() {
		Class<?> clazz = this.getClass();
		Color color = Color.opposite(this.color);

		if (clazz == Chick.class) {
			return new Chick(color);
		}
		if (clazz == Elephant.class) {
			return new Elephant(color);
		}
		if (clazz == Giraffe.class) {
			return new Giraffe(color);
		}
		if (clazz == Lion.class) {
			return new Lion(color);
		}
		if (clazz == Hen.class) {
			return new Hen(color);
		}
		throw new RuntimeException("Cannot switch color: " + this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		if (color != other.color)
			return false;
		return true;
	}

	public abstract List<Square> range(int i, int j);

	@Override
	public int compareTo(Piece o) {
		if (color != o.color) {
			return (color == Color.BLACK) ? -1 : 1;
		}
		return getClass().toString().compareTo(o.getClass().toString());
	}
}
