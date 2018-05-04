package sim;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import model.Board;
import model.Color;
import model.Move;
import model.player.Player;
import util.KeyUtil;

public class All {

	private String filePath;

	public All(String filePath) {
		this.filePath = filePath;
	}

	public void run() throws IOException {
		PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(
				filePath)));

		HashSet<String> allKeys = new HashSet<String>();
		LinkedList<String> uncomputedKeys = new LinkedList<String>();

		String key = KeyUtil.key(new Board(), Color.BLACK);
		allKeys.add(key);
		uncomputedKeys.add(key);

		while (!uncomputedKeys.isEmpty()) {
			// parse
			key = uncomputedKeys.poll();
			Board board = KeyUtil.parseBoard(key);
			Color color = KeyUtil.parseColor(key);

			// get moves
			Player player = new Player(color);
			player.setBoard(board);
			List<Move> moves = player.findMoves();

			// output
			for (Move move : moves) {
				Board b = new Board(board);
				b.update(move);
				String newKey = KeyUtil.key(b, Color.opposite(color));

				if (!allKeys.contains(newKey)) {
					allKeys.add(newKey);
					uncomputedKeys.add(newKey);
				}
			}
			// done
			writer.println(key);
		}
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		System.out.println("start");
		String filePath = args[0];
		new All(filePath).run();
		System.out.println("done");
	}
}
