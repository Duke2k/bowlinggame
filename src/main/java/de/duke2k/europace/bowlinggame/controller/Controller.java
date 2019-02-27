package de.duke2k.europace.bowlinggame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.duke2k.europace.bowlinggame.core.InvalidScoreException;
import de.duke2k.europace.bowlinggame.core.BowlingGameScoreService;

@RestController
@RequestMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE })
public class Controller {

	private BowlingGameScoreService scoreService;

	@Autowired
	public Controller(BowlingGameScoreService scoreService) {
		this.scoreService = scoreService;
	}

	@GetMapping(path = ControllerRoutes.TOTAL_SCORE)
	public Integer getTotalScore(@RequestParam(value = ControllerRoutes.SCORE_PARAMETER) String rawScore) throws InvalidScoreException {
		return scoreService.getTotalScore(rawScore);
	}
}
