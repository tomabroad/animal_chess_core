package animal_chess.model.piece;

import org.junit.Test;

import animal_chess.core.model.Color;
import animal_chess.core.model.piece.Chick;
import animal_chess.core.model.piece.Elephant;
import animal_chess.core.model.piece.Giraffe;
import animal_chess.core.model.piece.Piece;
import junit.framework.Assert;

public class PieceTest {

  @Test
  public void testCompareTo() {
    Object[][] testCases = {
        // same
        { new Chick(Color.BLACK), new Chick(Color.BLACK), 0 },
        // different color
        { new Chick(Color.BLACK), new Chick(Color.WHITE), -1 }, { new Chick(Color.WHITE), new Chick(Color.BLACK), 1 },
        // different piece
        { new Chick(Color.BLACK), new Elephant(Color.BLACK), -2 },
        { new Elephant(Color.BLACK), new Chick(Color.BLACK), 2 },
        { new Chick(Color.BLACK), new Giraffe(Color.BLACK), -4 },
        // different color & piece
        { new Chick(Color.BLACK), new Elephant(Color.WHITE), -1 },
        { new Chick(Color.WHITE), new Elephant(Color.BLACK), 1 }, };

    for (Object[] testCase : testCases) {
      Piece p1 = (Piece) testCase[0];
      Piece p2 = (Piece) testCase[1];
      int expected = (int) testCase[2];

      int actual = p1.compareTo(p2);
      Assert.assertEquals(expected, actual);
    }
  }

}
