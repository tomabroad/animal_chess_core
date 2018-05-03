package model.player;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import model.Board;
import model.Color;
import model.Move;
import model.Square;
import model.piece.Lion;
import model.piece.Piece;

public class Player {
	private Color color;
	private Board board;

	public Player(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public Player opponent() {
		Player op = new Player(Color.opposite(color));
		op.setBoard(board);
		return op;
	}

	public boolean validate(Move move) {
		List<Move> moves = findMoves();
		return moves.contains(move);
	}

	public Move findMove() {
		List<Move> moves = findMoves();
		if (moves.isEmpty()) {
			return null;
		}
		int index = new Random().nextInt(moves.size());
		return moves.get(index);
	}

	public List<Move> findMoves() {
		List<Move> moves = findOnboardMoves();
		moves = removeCheckedMoves(moves);

		if (!isChecked()) {
			moves.addAll(findDropMoves());
		}
		return moves;
	}

	private List<Move> findOnboardMoves() {
		List<Move> moves = new LinkedList<Move>();

		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				Piece p = board.pieceAt(i, j);

				if (isAlly(p)) {
					List<Square> nexts = p.range(i, j);
					Square from = new Square(i, j);

					for (Square to : nexts) {
						Move move = new Move(from, to);
						if (board.validate(move)) {
							moves.add(move);
						}
					}
				}
			}
		}
		return moves;
	}

	public boolean isAlly(Piece p) {
		return p != null && p.getColor() == color;
	}

	private List<Move> removeCheckedMoves(List<Move> moves) {
		List<Move> ret = new LinkedList<Move>();
		Player op = opponent();

		for (Move move : moves) {
			Board b = new Board(board);
			b.update(move);
			op.setBoard(b);

			if (!op.checks()) {
				ret.add(move);
			}
		}
		return ret;
	}

	private boolean isChecked() {
		Player op = opponent();
		return op.checks();
	}

	private boolean checks() {
		if (board.isLionAtFinalRank(color)) {
			return true;
		}
		List<Move> moves = findOnboardMoves();

		for (Move move : moves) {
			Piece toPiece = board.pieceAt(move.getTo());
			if (isOpponentLion(toPiece)) {
				return true;
			}
		}
		return false;
	}

	private boolean isOpponentLion(Piece piece) {
		return piece instanceof Lion && !isAlly(piece);
	}

	private List<Move> findDropMoves() {
		List<Move> ret = new LinkedList<Move>();
		List<Piece> pieces = board.getCaptures(color);
		if (pieces.isEmpty()) {
			return ret;
		}

		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				if (board.pieceAt(i, j) == null) {
					for (Piece piece : pieces) {
						ret.add(new Move(piece, new Square(i, j)));
					}
				}
			}
		}
		return ret;
	}

}
