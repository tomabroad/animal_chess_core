package tests.model.player;

import java.util.Collections;
import java.util.List;

import junit.framework.Assert;
import model.Board;
import model.Color;
import model.Move;
import model.Square;
import model.player.Player;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testFindMove() {
		Player player = new Player(Color.BLACK);
		player.setBoard(new Board());

		Move move = player.findMove();
		List<Move> moves = player.findMoves();

		Assert.assertTrue(moves.contains(move));
	}

	@Test
	public void testFindMoves() {
		Player black = new Player(Color.BLACK);
		Player white = new Player(Color.WHITE);
		Board b = new Board();

		// initial move
		black.setBoard(b);
		assertFindMoves(black,
				"[{(2,1)>(1,1)}, {(3,1)>(2,0)}, {(3,1)>(2,2)}, {(3,2)>(2,2)}]");

		// remove checked move (after black chick captures white chick)
		b.update(new Move(new Square(2, 1), new Square(1, 1)));
		white.setBoard(b);
		assertFindMoves(white,
				"[{(0,1)>(1,0)}, {(0,1)>(1,1)}, {(0,1)>(1,2)}, {(0,2)>(1,1)}]");

		// add drop (after white elephant captures black chick)
		b.update(new Move(new Square(0, 2), new Square(1, 1)));
		black.setBoard(b);
		assertFindMoves(black,
				"[{C>(0,2)}, {C>(1,0)}, {C>(1,2)}, {C>(2,0)}, {C>(2,1)}, {C>(2,2)}"
						+ ", {(3,0)>(2,1)}, {(3,1)>(2,1)}, {(3,2)>(2,2)}]");
	}

	private void assertFindMoves(Player player, String expected) {
		List<Move> moves = player.findMoves();
		Collections.sort(moves);
		String actual = moves.toString();
		Assert.assertEquals(expected, actual);
	}
}
