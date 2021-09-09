package model;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import model.piece.Chick;
import model.piece.Elephant;
import model.piece.Giraffe;
import model.piece.Hen;
import model.piece.Lion;
import model.piece.Piece;

public class Board {
  public static final int ROWS = 4;
  public static final int COLS = 3;

  private Piece[][] pieces;
  private List<Piece> captures;

  public Board() {
    initPieces();
    captures = new LinkedList<Piece>();
  }

  public Board(Board original) {
    pieces = new Piece[ROWS][COLS];
    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLS; j++) {
        pieces[i][j] = original.pieces[i][j];
      }
    }

    captures = new LinkedList<Piece>(original.captures);
  }

  public Board(Piece[][] pieces, List<Piece> captures) {
    this.pieces = pieces;
    this.captures = captures;
  }

  private void initPieces() {
    pieces = new Piece[ROWS][COLS];
    pieces[0][0] = new Giraffe(Color.WHITE);
    pieces[0][1] = new Lion(Color.WHITE);
    pieces[0][2] = new Elephant(Color.WHITE);
    pieces[1][0] = null;
    pieces[1][1] = new Chick(Color.WHITE);
    pieces[1][2] = null;
    pieces[2][0] = null;
    pieces[2][1] = new Chick(Color.BLACK);
    pieces[2][2] = null;
    pieces[3][0] = new Elephant(Color.BLACK);
    pieces[3][1] = new Lion(Color.BLACK);
    pieces[3][2] = new Giraffe(Color.BLACK);
  }

  public Piece[][] getPieces() {
    return pieces;
  }

  public List<Piece> getCaptures() {
    return captures;
  }

  public List<Piece> getCaptures(Color color) {
    List<Piece> ret = new LinkedList<Piece>();
    for (Piece p : captures) {
      if (p.getColor() == color) {
        ret.add(p);
      }
    }
    return ret;
  }

  public Piece pieceAt(int i, int j) {
    return pieces[i][j];
  }

  public Piece pieceAt(Square s) {
    return pieceAt(s.getI(), s.getJ());
  }

  public static boolean inside(int i, int j) {
    return 0 <= i && i < ROWS && 0 <= j && j < COLS;
  }

  public boolean validate(Move move) {
    return validateFrom(move) && validateTo(move);
  }

  private boolean validateFrom(Move move) {
    if (move.isDrop()) {
      // no captures
      if (!captures.contains(move.getDroppingPiece())) {
        return false;
      }
    } else {
      Square from = move.getFrom();

      // outside OR no piece
      if (!inside(from.getI(), from.getJ()) || pieceAt(from) == null) {
        return false;
      }
    }
    return true;
  }

  private boolean validateTo(Move move) {
    Square to = move.getTo();

    // outside
    if (!inside(to.getI(), to.getJ())) {
      return false;
    }
    Piece toPiece = pieceAt(to);

    if (move.isDrop()) {
      // dropping on a piece
      if (toPiece != null) {
        return false;
      }
    } else {
      Square from = move.getFrom();
      Piece fromPiece = pieceAt(from);

      // attacking ally OR out of range
      if (toPiece != null && toPiece.getColor() == fromPiece.getColor()
          || !fromPiece.range(from.getI(), from.getJ()).contains(to)) {
        return false;
      }
    }
    return true;
  }

  public void update(Move move) {
    if (!validate(move)) {
      throw new RuntimeException("Illegal move: " + move);
    }
    Square to = move.getTo();
    Piece piece = null;

    if (move.isDrop()) {
      piece = move.getDroppingPiece();
      captures.remove(piece);
    } else {
      Square from = move.getFrom();
      piece = pieceAt(from);
      pieces[from.getI()][from.getJ()] = null;

      capturePiece(to);
      piece = promotion(piece, to.getI());
    }
    pieces[to.getI()][to.getJ()] = piece;
  }

  private void capturePiece(Square s) {
    Piece piece = pieceAt(s);
    if (piece == null) {
      return;
    }
    piece = piece.switchColor();

    // demotion
    if (piece instanceof Hen) {
      piece = new Chick(piece.getColor());
    }
    captures.add(piece);
  }

  private Piece promotion(Piece p, int i) {
    if (p instanceof Chick && finalRank(p.getColor()) == i) {
      p = new Hen(p.getColor());
    }
    return p;
  }

  private int finalRank(Color color) {
    return (color == Color.BLACK) ? 0 : Board.ROWS - 1;
  }

  public boolean isLionAtFinalRank(Color color) {
    int i = finalRank(color);

    for (int j = 0; j < Board.COLS; j++) {
      Piece p = pieceAt(i, j);
      if (p instanceof Lion && p.getColor() == color) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "Board [pieces=" + Arrays.toString(pieces) + ", captures=" + captures + "]";
  }
}
