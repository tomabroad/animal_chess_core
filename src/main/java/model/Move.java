package model;

import model.piece.Piece;

/**
 * immutable
 *
 */
public class Move implements Comparable<Move> {
	private Piece droppingPiece;
	private Square from, to;

	public Move(Piece droppingPiece, Square to) {
		this.droppingPiece = droppingPiece;
		this.from = null;
		this.to = to;
	}

	public Move(Square from, Square to) {
		this.droppingPiece = null;
		this.from = from;
		this.to = to;
	}

	public Piece getDroppingPiece() {
		return droppingPiece;
	}

	public Square getFrom() {
		return from;
	}

	public Square getTo() {
		return to;
	}

	public boolean isDrop() {
		return droppingPiece != null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((droppingPiece == null) ? 0 : droppingPiece.hashCode());
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((to == null) ? 0 : to.hashCode());
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
		Move other = (Move) obj;
		if (droppingPiece == null) {
			if (other.droppingPiece != null)
				return false;
		} else if (!droppingPiece.equals(other.droppingPiece))
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String str = (isDrop() ? droppingPiece.toString() : from.toString());
		return "{'from':" + str + ",'to':" + to + "}";
	}

	@Override
	public int compareTo(Move o) {
		if (isDrop() && o.isDrop()) {
			if (droppingPiece.equals(o.droppingPiece)) {
				return droppingPiece.compareTo(o.droppingPiece);
			}
			return to.compareTo(o.to);
		}
		if (isDrop()) {
			return -1;
		}
		if (o.isDrop()) {
			return 1;
		}
		// no drop
		if (from.equals(o.from)) {
			return to.compareTo(o.to);
		}
		return from.compareTo(o.from);
	}
}
