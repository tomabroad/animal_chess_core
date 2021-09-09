package model.piece;

import java.util.List;

import model.Color;
import model.Square;

public class Hen extends Piece {

  public Hen(Color color) {
    super(color);
  }

  @Override
  public List<Square> range(int i, int j) {
    Giraffe giraffe = new Giraffe(this.getColor());
    List<Square> squares = giraffe.range(i, j);

    if (this.getColor() == Color.BLACK) {
      i--;
    } else {
      i++;
    }
    squares.add(new Square(i, j - 1));
    squares.add(new Square(i, j + 1));
    return squares;
  }

  @Override
  public String toString() {
    return this.getColor() == Color.BLACK ? "H" : "h";
  }
}
