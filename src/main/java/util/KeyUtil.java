package util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import model.Board;
import model.Color;
import model.piece.Chick;
import model.piece.Elephant;
import model.piece.Giraffe;
import model.piece.Hen;
import model.piece.Lion;
import model.piece.Piece;

public class KeyUtil {

	public static String key(Board board, Color color) {
		StringBuilder sb = new StringBuilder();

		// color
		sb.append(color == Color.BLACK ? "B" : "W");

		// on board
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				Piece p = board.pieceAt(i, j);
				sb.append(p == null ? " " : p);
			}
		}
		// capture
		List<Piece> captures = new LinkedList<Piece>(board.getCaptures());
		Collections.sort(captures);
		for (Piece p : captures) {
			sb.append(p);
		}
		return sb.toString();
	}

	public static Color parseColor(String key) {
		return (key.charAt(0) == 'B') ? Color.BLACK : Color.WHITE;
	}

	public static Board parseBoard(String key) {
		int strIndex = 1;

		// on board
		Piece[][] pieces = new Piece[Board.ROWS][Board.COLS];
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				pieces[i][j] = parsePiece(key.charAt(strIndex));
				strIndex++;
			}
		}
		// captures
		List<Piece> captures = new LinkedList<Piece>();
		for (int i = strIndex; i < key.length(); i++) {
			Piece p = parsePiece(key.charAt(strIndex));
			captures.add(p);
		}

		return new Board(pieces, captures);
	}

	private static Piece parsePiece(char c) {
		switch (c) {
		case ' ':
			return null;
		case 'C':
			return new Chick(Color.BLACK);
		case 'c':
			return new Chick(Color.WHITE);
		case 'E':
			return new Elephant(Color.BLACK);
		case 'e':
			return new Elephant(Color.WHITE);
		case 'G':
			return new Giraffe(Color.BLACK);
		case 'g':
			return new Giraffe(Color.WHITE);
		case 'L':
			return new Lion(Color.BLACK);
		case 'l':
			return new Lion(Color.WHITE);
		case 'H':
			return new Hen(Color.BLACK);
		case 'h':
			return new Hen(Color.WHITE);
		}
		throw new RuntimeException("Illegal char: " + c);
	}
}
