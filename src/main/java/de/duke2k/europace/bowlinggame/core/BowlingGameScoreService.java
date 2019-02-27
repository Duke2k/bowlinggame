package de.duke2k.europace.bowlinggame.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BowlingGameScoreService {

	private BowlingGameScoreValidator validator;

	@Autowired
	public BowlingGameScoreService(BowlingGameScoreValidator validator) {
		this.validator = validator;
	}

	public int getTotalScore(String rawScore) throws InvalidScoreException {
		Score score = validator.validate(rawScore);
		return score.stream()
				.mapToInt(p -> p.getLeft() + p.getRight())
				.sum();
	}
}
