package animal_chess.model.piece;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import animal_chess.core.model.Color;
import animal_chess.core.model.Square;
import animal_chess.core.model.piece.Lion;
import junit.framework.Assert;

public class LionTest {

  @Test
  public void testRange() {
    Lion lion = new Lion(Color.BLACK);
    String expected = "[[1,1], [1,2], [1,3], [2,1], [2,3], [3,1], [3,2], [3,3]]";

    List<Square> ls = lion.range(2, 2);
    Collections.sort(ls);
    String actual = ls.toString();

    Assert.assertEquals(expected, actual);
  }

}
