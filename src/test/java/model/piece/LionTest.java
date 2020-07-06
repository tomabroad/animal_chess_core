package model.piece;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;
import model.Color;
import model.Square;
import model.piece.Lion;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
