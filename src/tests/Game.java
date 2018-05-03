package tests;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Board;
import model.Color;
import model.Move;
import model.Square;
import model.piece.Piece;
import model.player.MonteCarloPlayer;
import model.player.Player;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;

	// main panel
	private JPanel mainPanel;
	private JPanel whiteCapturePanel;
	private JPanel blackCapturePanel;
	private JPanel boardPanel;
	private JButton[][] squareButtons;

	// game info
	private Player player;
	private Board board;
	private Object from; // Square or Piece

	public Game() {
		initUI();
		newGame();
	}

	/*
	 * view: init
	 */

	private void initUI() {
		// main panel
		whiteCapturePanel = new JPanel(new GridLayout(1, 6));
		blackCapturePanel = new JPanel(new GridLayout(1, 6));
		initSquareButtons();
		initBoardPanel();
		initMainPanel();
		getContentPane().add(mainPanel, BorderLayout.CENTER);

		// frame
		setSize(300, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initMainPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(whiteCapturePanel, BorderLayout.NORTH);
		panel.add(boardPanel, BorderLayout.CENTER);
		panel.add(blackCapturePanel, BorderLayout.SOUTH);
		mainPanel = panel;
	}

	private void initBoardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Board.ROWS, Board.COLS));

		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				panel.add(squareButtons[i][j]);
			}
		}
		boardPanel = panel;
	}

	private void initSquareButtons() {
		JButton[][] btns = new JButton[Board.ROWS][Board.COLS];
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				final int row = i;
				final int col = j;

				JButton btn = new JButton();
				btn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						squareButtonEvent(row, col);
					}
				});
				btns[i][j] = btn;
			}
		}
		squareButtons = btns;
	}

	/*
	 * view: update
	 */

	private void updateView() {
		updateSquareButtons();
		updateCapturePanel(blackCapturePanel, Color.BLACK);
		updateCapturePanel(whiteCapturePanel, Color.WHITE);
	}

	private void updateSquareButtons() {
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.COLS; j++) {
				Piece piece = board.pieceAt(i, j);
				String text = (piece == null) ? "" : piece.toString();
				squareButtons[i][j].setText(text);
			}
		}
	}

	private void updateCapturePanel(JPanel panel, Color color) {
		List<Piece> captures = board.getCaptures(color);
		Collections.sort(captures);
		panel.removeAll();

		for (final Piece piece : captures) {
			JButton btn = new JButton(piece.toString());

			btn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					captureButtonEvent(piece);
				}
			});
			panel.add(btn);
		}
	}

	/*
	 * control
	 */

	private void newGame() {
		player = new Player(Color.BLACK);
		board = new Board();
		from = null;
		updateView();
	}

	private void squareButtonEvent(int i, int j) {
		player.setBoard(board);

		if (from == null) {
			Piece piece = board.pieceAt(i, j);
			if (player.isAlly(piece)) {
				from = new Square(i, j);
			}
		} else {
			Square toSquare = new Square(i, j);
			Move move = null;
			if (from instanceof Square) {
				move = new Move((Square) from, toSquare);
			} else {
				move = new Move((Piece) from, toSquare);
			}

			if (player.validate(move)) {
				board.update(move);
				System.out.println(move);
				computerPlay();
			}
			from = null;
		}
		updateView();
	}

	private void captureButtonEvent(Piece p) {
		player.setBoard(board);
		if (player.isAlly(p) && board.getCaptures().contains(p)) {
			from = p;
		}
		updateView();
	}

	private void computerPlay() {
		Player com = new MonteCarloPlayer(Color.opposite(player.getColor()));
		com.setBoard(board);
		Move move = com.findMove();

		if (move == null) {
			System.out.println("computer resign\n");
		} else {
			board.update(move);
			System.out.println(move);
		}
		updateView();
	}

	public static void main(String[] args) {
		Game ex = new Game();
		ex.setVisible(true);
	}
}
