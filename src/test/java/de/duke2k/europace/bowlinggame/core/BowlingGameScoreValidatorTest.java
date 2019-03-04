package de.duke2k.europace.bowlinggame.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

public class BowlingGameScoreValidatorTest {

	private BowlingGameScoreValidator validator;

	@Before
	public void setUp() {
		validator = new BowlingGameScoreValidator();
	}

	@Test
	public void validate_KeineWerte() {
		try {
			validator.validate("");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Es wurden nicht genau 20 Werte (bei 10 Frames und evtl. Bonuswurf) geliefert!", e.getMessage());
		}
	}

	@Test
	public void validate_UngueltigeWerte() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,x,y,5,5,10,7,2,0,4,5");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Es sind nur Ziffern 0-9 oder '-' zulässig!", e.getMessage());
		}
	}

	@Test
	public void validate_ZuGrosserWertEinWurf() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,11,0,5,5,10,7,2,0,4,5");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Ungültige Anzahl umgeworfener Pins bei Frame 6!", e.getMessage());
		}
	}

	@Test
	public void validate_ZuKleinerWert() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,10,0,5,5,10,-7,2,0,4,5");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Ungültige Anzahl umgeworfener Pins bei Frame 8!", e.getMessage());
		}
	}

	@Test
	public void validate_ZuGrosserWertBeidenWuerfeAddiert() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,9,2,5,5,10,7,2,0,4,5");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Ungültige Anzahl umgeworfener Pins bei Frame 6!", e.getMessage());
		}
	}

	@Test
	public void validate_StrikeAmEnde_BonusFehlt() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,8,0,5,5,10,-,2,0,10,-");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Es wurden nicht genau 21 Werte (bei 10 Frames und evtl. Bonuswurf) geliefert!", e.getMessage());
		}
	}

	@Test
	public void validate_SpareAmEnde_BonusFehlt() {
		try {
			validator.validate("5,5,10,-,8,1,6,2,10,-,8,0,5,5,10,-,2,0,4,6");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException e) {
			assertEquals("Es wurden nicht genau 21 Werte (bei 10 Frames und evtl. Bonuswurf) geliefert!", e.getMessage());
		}
	}

	@Test
	public void validate_Ok() throws InvalidScoreException {
		Score score = validator.validate("5,5,10,-,8,1,6,2,10,-,8,0,5,5,10,-,2,0,4,5");
		assertEquals(Pair.of(5, 5), score.getFrameScore(0));
		assertEquals(Pair.of(10, 0), score.getFrameScore(1));
		assertEquals(Pair.of(8, 1), score.getFrameScore(2));
		assertEquals(Pair.of(6, 2), score.getFrameScore(3));
		assertEquals(Pair.of(10, 0), score.getFrameScore(4));
		assertEquals(Pair.of(8, 0), score.getFrameScore(5));
		assertEquals(Pair.of(5, 5), score.getFrameScore(6));
		assertEquals(Pair.of(10, 0), score.getFrameScore(7));
		assertEquals(Pair.of(2, 0), score.getFrameScore(8));
		assertEquals(Pair.of(4, 5), score.getFrameScore(9));
	}

	@Test
	public void validate_Ok_SpareAmEnde() throws InvalidScoreException {
		Score score = validator.validate("5,5,10,-,8,1,6,2,10,-,8,0,5,5,10,-,2,0,4,6,7");
		assertEquals(Pair.of(5, 5), score.getFrameScore(0));
		assertEquals(Pair.of(10, 0), score.getFrameScore(1));
		assertEquals(Pair.of(8, 1), score.getFrameScore(2));
		assertEquals(Pair.of(6, 2), score.getFrameScore(3));
		assertEquals(Pair.of(10, 0), score.getFrameScore(4));
		assertEquals(Pair.of(8, 0), score.getFrameScore(5));
		assertEquals(Pair.of(5, 5), score.getFrameScore(6));
		assertEquals(Pair.of(10, 0), score.getFrameScore(7));
		assertEquals(Pair.of(2, 0), score.getFrameScore(8));
		assertEquals(Pair.of(4, 6), score.getFrameScore(9));
		assertEquals(Pair.of(7, 0), score.getFrameScore(10));
	}

	@Test
	public void validate_Ok_StrikeAmEnde() throws InvalidScoreException {
		Score score = validator.validate("5,5,10,-,8,1,6,2,10,-,8,0,5,5,10,-,2,0,10,-,7");
		assertEquals(Pair.of(5, 5), score.getFrameScore(0));
		assertEquals(Pair.of(10, 0), score.getFrameScore(1));
		assertEquals(Pair.of(8, 1), score.getFrameScore(2));
		assertEquals(Pair.of(6, 2), score.getFrameScore(3));
		assertEquals(Pair.of(10, 0), score.getFrameScore(4));
		assertEquals(Pair.of(8, 0), score.getFrameScore(5));
		assertEquals(Pair.of(5, 5), score.getFrameScore(6));
		assertEquals(Pair.of(10, 0), score.getFrameScore(7));
		assertEquals(Pair.of(2, 0), score.getFrameScore(8));
		assertEquals(Pair.of(10, 0), score.getFrameScore(9));
		assertEquals(Pair.of(7, 0), score.getFrameScore(10));
	}
}