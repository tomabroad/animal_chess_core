package model.player;

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
				"[{'from':[2,1],'to':[1,1]}, {'from':[3,1],'to':[2,0]}, {'from':[3,1],'to':[2,2]}, {'from':[3,2],'to':[2,2]}]");

		// remove checked move (after black chick captures white chick)
		b.update(new Move(new Square(2, 1), new Square(1, 1)));
		white.setBoard(b);
		assertFindMoves(white,
				"[{'from':[0,1],'to':[1,0]}, {'from':[0,1],'to':[1,1]}, {'from':[0,1],'to':[1,2]}, {'from':[0,2],'to':[1,1]}]");

		// add drop (after white elephant captures black chick)
		b.update(new Move(new Square(0, 2), new Square(1, 1)));
		black.setBoard(b);
		assertFindMoves(black,
				"[{'from':C,'to':[0,2]}, {'from':C,'to':[1,0]}, {'from':C,'to':[1,2]}, {'from':C,'to':[2,0]}, {'from':C,'to':[2,1]}, {'from':C,'to':[2,2]}, {'from':[3,0],'to':[2,1]}, {'from':[3,1],'to':[2,1]}, {'from':[3,2],'to':[2,2]}]");
	}

	private void assertFindMoves(Player player, String expected) {
		List<Move> moves = player.findMoves();
		Collections.sort(moves);
		String actual = moves.toString();
		Assert.assertEquals(expected, actual);
	}
}
