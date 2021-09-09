package animal_chess.core.model.piece;

import java.util.List;

import animal_chess.core.model.Color;
import animal_chess.core.model.Square;

public class Lion extends Piece {

  public Lion(Color color) {
    super(color);
  }

  @Override
  public List<Square> range(int i, int j) {
    Elephant e = new Elephant(this.getColor());
    Giraffe g = new Giraffe(this.getColor());

    List<Square> squares = e.range(i, j);
    squares.addAll(g.range(i, j));
    return squares;
  }

  @Override
  public String toString() {
    return this.getColor() == Color.BLACK ? "L" : "l";
  }
}
