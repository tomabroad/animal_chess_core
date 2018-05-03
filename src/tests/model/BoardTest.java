package tests.model;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import model.Board;
import model.Color;
import model.Move;
import model.Square;
import model.piece.Chick;
import model.piece.Piece;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoardTest {

	@Test
	public void testUpdate() {
		Board board = new Board();

		// invalid
		try {
			Move move = new Move(new Square(4, 1), new Square(1, 1));
			board.update(move);
			Assert.fail("No exception");
		} catch (RuntimeException e) {
			String expected = "Illegal move: {(4,1)>(1,1)}";
			String actual = e.getMessage();
			Assert.assertEquals(expected, actual);
		}

		// on board: capture
		Move move = new Move(new Square(2, 1), new Square(1, 1));
		board.update(move);
		List<Piece> actual = board.getCaptures();
		List<Piece> expected = new LinkedList<Piece>();
		expected.add(new Chick(Color.BLACK));
		Assert.assertEquals(expected, actual);

		// TODO: add pattern
		// drop
		// move
		// - capture
		// -- demotion
		// - promotion
		// - other
	}

	@Test
	public void testValidateMove() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Board board = new Board();

		ValidationCase[] onboardCases = {
				// valid
				new ValidationCase(2, 1, 1, 1, true),
				new ValidationCase(3, 1, 2, 2, true),
				// from: outside, no piece
				new ValidationCase(4, 1, 1, 1, false),
				new ValidationCase(2, 0, 1, 1, false),
				// to: outside, attacking ally, out of range
				new ValidationCase(2, 1, 4, 1, false),
				new ValidationCase(3, 0, 2, 1, false),
				new ValidationCase(2, 1, 1, 0, false), };
		for (ValidationCase testCase : onboardCases) {
			Move move = testCase.getMove();
			boolean actual = board.validate(move);
			Assert.assertEquals(testCase.getExpected(), actual);
		}

		// update board: black chick captures white chick
		board.update(new Move(new Square(2, 1), new Square(1, 1)));

		ValidationCase[] dropCases = {
				// valid
				new ValidationCase(new Chick(Color.BLACK), 1, 0, true),
				// from: no capture
				new ValidationCase(new Chick(Color.WHITE), 1, 0, false),
				// to: outside, dropping on a piece
				new ValidationCase(new Chick(Color.BLACK), 4, 0, false),
				new ValidationCase(new Chick(Color.BLACK), 0, 0, false),
		//
		};
		for (ValidationCase testCase : dropCases) {
			Move move = testCase.getMove();
			boolean actual = board.validate(move);
			Assert.assertEquals(testCase.getExpected(), actual);
		}
	}

	private class ValidationCase {
		private Move move;
		private boolean expected;

		public ValidationCase(int i1, int j1, int i2, int j2, boolean expected) {
			this.move = new Move(new Square(i1, j1), new Square(i2, j2));
			this.expected = expected;
		}

		public ValidationCase(Piece piece, int i2, int j2, boolean expected) {
			this.move = new Move(piece, new Square(i2, j2));
			this.expected = expected;
		}

		public Move getMove() {
			return move;
		}

		public boolean getExpected() {
			return expected;
		}
	}
}
