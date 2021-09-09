package animal_chess.model.piece;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import animal_chess.core.model.Color;
import animal_chess.core.model.Square;
import animal_chess.core.model.piece.Hen;
import junit.framework.Assert;

public class HenTest {

  @Test
  public void testRangeBlack() {
    Hen whiteHen = new Hen(Color.BLACK);
    String expected = "[[1,1], [1,2], [1,3], [2,1], [2,3], [3,2]]";

    List<Square> ls = whiteHen.range(2, 2);
    Collections.sort(ls);
    String actual = ls.toString();

    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testRangeWhite() {
    Hen whiteHen = new Hen(Color.WHITE);
    String expected = "[[1,2], [2,1], [2,3], [3,1], [3,2], [3,3]]";

    List<Square> ls = whiteHen.range(2, 2);
    Collections.sort(ls);
    String actual = ls.toString();

    Assert.assertEquals(expected, actual);
  }

}
