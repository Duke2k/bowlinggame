package de.duke2k.europace.bowlinggame.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

public class ScoreUtilTest {

	@Test
	public void isStrike() {
		assertTrue(ScoreUtil.isStrike(Pair.of(10, 0)));
		assertFalse(ScoreUtil.isStrike(Pair.of(0, 10)));
		assertFalse(ScoreUtil.isStrike(Pair.of(5, 5)));
	}

	@Test
	public void isSpare() {
		assertTrue(ScoreUtil.isSpare(Pair.of(0, 10)));
		assertFalse(ScoreUtil.isSpare(Pair.of(10, 0)));
		assertTrue(ScoreUtil.isSpare(Pair.of(5, 5)));
		assertFalse(ScoreUtil.isSpare(Pair.of(5, 4)));
	}
}