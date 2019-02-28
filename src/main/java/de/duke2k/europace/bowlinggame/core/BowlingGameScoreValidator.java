package de.duke2k.europace.bowlinggame.core;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Component;

@Component
class BowlingGameScoreValidator {

	@Nonnull
	Score validate(String rawScore) throws InvalidScoreException {
		String[] splittedRawScore = StringUtils.split(rawScore, ',');
		if (splittedRawScore.length != Score.NUMBER_OF_FRAMES * 2) {
			throw new InvalidScoreException("Es wurden nicht genau " +
					Score.NUMBER_OF_FRAMES * 2 + " Werte (bei " +
					Score.NUMBER_OF_FRAMES + " Frames) geliefert!");
		}
		if (!Arrays.stream(splittedRawScore).allMatch(rs -> StringUtils.containsOnly(rs, "-0123456789"))) {
			throw new InvalidScoreException("Es sind nur Ziffern 0-9 oder '-' zulässig!");
		}
		Score result = new Score();
		for (int i = 0; i < splittedRawScore.length; i += 2) {
			Integer left = Integer.valueOf(splittedRawScore[i]);
			Integer right = Integer.valueOf(StringUtils.equals(splittedRawScore[i + 1], "-") ? "0" : splittedRawScore[i + 1]);
			if (left < 0 || left > 10 || right < 0 || right > 10 || left + right > 10) {
				throw new InvalidScoreException("Ungültige Anzahl umgeworfener Pins bei Frame " + i / 2 + "!");
			}
			result.addFrameScore(new ImmutablePair<>(left, right));
		}
		return result;
	}
}
