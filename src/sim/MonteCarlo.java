package sim;

import java.util.List;

import model.Board;
import model.Color;
import model.Move;
import model.player.Player;

public class MonteCarlo {
	private Board board;
	private Color turn;
	private int blackWins;
	private int whiteWins;

	public MonteCarlo(Board board, Color turn) {
		this.board = board;
		this.turn = turn;
	}

	public int getWins(Color color) {
		return (color == Color.BLACK) ? blackWins : whiteWins;
	}

	public void simulate(int numMatches) {
		for (int i = 0; i < numMatches; i++) {
			if (match() == Color.BLACK) {
				blackWins++;
			} else {
				whiteWins++;
			}
		}
	}

	private Color match() {
		Board b = new Board(board);
		Player player = new Player(turn);

		while (true) {
			player.setBoard(b);
			Move move = player.findMove();

			if (move == null) {
				return Color.opposite(player.getColor());
			}
			b.update(move);
			player = player.opponent();
		}
	}

	// test
	public static void main(String[] args) {
		Board baseBoard = new Board();

		Player player = new Player(Color.BLACK);
		player.setBoard(baseBoard);
		List<Move> moves = player.findMoves();

		for (Move move : moves) {
			Board b = new Board(baseBoard);

			MonteCarlo mc = new MonteCarlo(b, Color.WHITE);
			mc.simulate(1000);

			System.out.println(move + ":" + mc.getWins(Color.BLACK) + ","
					+ mc.getWins(Color.WHITE));
		}
	}
}
