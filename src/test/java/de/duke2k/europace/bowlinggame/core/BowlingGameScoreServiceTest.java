package de.duke2k.europace.bowlinggame.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class BowlingGameScoreServiceTest {

	private BowlingGameScoreService scoreService;

	@Before
	public void setUp() {
		scoreService = new BowlingGameScoreService(new BowlingGameScoreValidator());
	}

	@Test
	public void getTotalScore_Ok() throws InvalidScoreException {
		assertEquals(0, scoreService.getTotalScore("0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0"));
		assertEquals(81, scoreService.getTotalScore("5,4,8,1,0,7,2,5,7,2,5,1,8,0,7,1,0,9,3,6"));
		assertEquals(112, scoreService.getTotalScore("5,5,8,1,0,7,2,5,10,-,5,1,8,0,7,1,0,10,3,7,5"));
		assertEquals(119, scoreService.getTotalScore("5,5,8,1,0,7,2,5,10,-,5,1,8,0,7,1,0,10,10,-,5"));
		assertEquals(155, scoreService.getTotalScore("5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5,5"));
		assertEquals(300, scoreService.getTotalScore("10,-,10,-,10,-,10,-,10,-,10,-,10,-,10,-,10,-,10,-,10"));
	}

	@Test
	public void getTotalScore_ExceptionWirdHochgereicht() {
		try {
			scoreService.getTotalScore("-1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
			fail("Erwartete Exception wurde nicht geworfen!");
		} catch (InvalidScoreException ignored) {
		}
	}
}