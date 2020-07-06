package model.player;

import java.util.List;

import model.Board;
import model.Color;
import model.Move;
import sim.MonteCarlo;

public class MonteCarloPlayer extends Player {

	public static final int NUM_MATCHES = 1000;

	public MonteCarloPlayer(Color color) {
		super(color);
	}

	@Override
	public Move findMove() {
		List<Move> moves = findMoves();
		if (moves.isEmpty()) {
			return null;
		}
		Move bestMove = moves.get(0);
		int maxWins = 0;

		for (Move move : moves) {
			Board b = new Board(getBoard());

			MonteCarlo mc = new MonteCarlo(b, Color.opposite(getColor()));
			mc.simulate(NUM_MATCHES);

			int wins = mc.getWins(getColor());
			if (wins > maxWins) {
				bestMove = move;
				maxWins = wins;
			}
		}
		return bestMove;
	}
}
