package animal_chess.core.model.piece;

import java.util.LinkedList;
import java.util.List;

import animal_chess.core.model.Color;
import animal_chess.core.model.Square;

public class Giraffe extends Piece {

  public Giraffe(Color color) {
    super(color);
  }

  @Override
  public List<Square> range(int i, int j) {
    List<Square> squares = new LinkedList<Square>();
    squares.add(new Square(i - 1, j + 0)); // up
    squares.add(new Square(i + 1, j + 0)); // down
    squares.add(new Square(i + 0, j + 1)); // right
    squares.add(new Square(i + 0, j - 1)); // left
    return squares;
  }

  @Override
  public String toString() {
    return this.getColor() == Color.BLACK ? "G" : "g";
  }

}
