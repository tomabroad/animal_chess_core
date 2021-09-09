package animal_chess.core.model.piece;

import java.util.LinkedList;
import java.util.List;

import animal_chess.core.model.Color;
import animal_chess.core.model.Square;

public class Chick extends Piece {

  public Chick(Color color) {
    super(color);
  }

  @Override
  public List<Square> range(int i, int j) {
    if (this.getColor() == Color.BLACK) {
      i--;
    } else {
      i++;
    }
    List<Square> squares = new LinkedList<Square>();
    squares.add(new Square(i, j));
    return squares;
  }

  @Override
  public String toString() {
    return this.getColor() == Color.BLACK ? "C" : "c";
  }
}
