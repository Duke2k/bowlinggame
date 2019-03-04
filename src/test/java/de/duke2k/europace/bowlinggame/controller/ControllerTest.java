package de.duke2k.europace.bowlinggame.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.duke2k.europace.bowlinggame.core.BowlingGameScoreService;
import de.duke2k.europace.bowlinggame.core.InvalidScoreException;

@RunWith(SpringRunner.class)
@WebMvcTest(Controller.class)
public class ControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BowlingGameScoreService scoreService;

	@Before
	public void setUp() throws InvalidScoreException {
		when(scoreService.getTotalScore("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1"))
				.thenReturn(20);
		when(scoreService.getTotalScore("-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1"))
				.thenThrow(new InvalidScoreException("Testexception"));
	}

	@Test
	public void getTotalScore() throws Exception {
		String contentAsString = mockMvc.perform(get(ControllerRoutes.TOTAL_SCORE + "?" +
				ControllerRoutes.SCORE_PARAMETER + "=1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1"))
				.andExpect(status().isOk())
				.andReturn().getResponse().getContentAsString();
		assertEquals("20", contentAsString);
		verify(scoreService).getTotalScore("1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
	}

	@Test
	public void getTotalScore_ExceptionFliegt() throws Exception {
		String contentAsString = mockMvc.perform(get(ControllerRoutes.TOTAL_SCORE + "?" +
				ControllerRoutes.SCORE_PARAMETER + "=-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1"))
				.andExpect(status().isBadRequest())
				.andReturn().getResponse().getContentAsString();
		assertEquals("{\"Ungültige Punkte\":\"Testexception\"}", contentAsString);
		verify(scoreService).getTotalScore("-1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1");
	}
}