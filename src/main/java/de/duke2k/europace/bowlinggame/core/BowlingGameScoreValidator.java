package de.duke2k.europace.bowlinggame.core;

import java.util.Arrays;

import javax.annotation.Nonnull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
class BowlingGameScoreValidator {

	@Nonnull
	Score validate(String rawScore) throws InvalidScoreException {
		String[] splittedRawScore = StringUtils.split(rawScore, ',');
		if (!Arrays.stream(splittedRawScore).allMatch(rs -> StringUtils.containsOnly(rs, "-0123456789"))) {
			throw new InvalidScoreException("Es sind nur Ziffern 0-9 oder '-' zulässig!");
		}
		Score result = new Score();
		for (int i = 0; i < splittedRawScore.length; i += 2) {
			Integer left = Integer.valueOf(splittedRawScore[i]);
			Integer right = Integer.valueOf(StringUtils.equals(splittedRawScore[i + 1], "-") ? "0" : splittedRawScore[i + 1]);
			if (left < 0 || left > 10 || right < 0 || right > 10 || left + right > 10) {
				throw new InvalidScoreException("Ungültige Anzahl umgeworfener Pins bei Frame " + (i / 2 + 1) + "!");
			}
			result.addFrameScore(new ImmutablePair<>(left, right));
		}
		Pair<Integer, Integer> lastFrame = result.getFrameScore(Score.NUMBER_OF_FRAMES - 1);
		int bonusCount = ScoreUtil.isSpare(lastFrame) ? 1 : (ScoreUtil.isStrike(lastFrame) ? 2 : 0);
		if (splittedRawScore.length != Score.NUMBER_OF_FRAMES * 2 + bonusCount) {
			throw new InvalidScoreException("Es wurden nicht genau " +
					Score.NUMBER_OF_FRAMES * 2 + bonusCount + " Werte (bei " +
					Score.NUMBER_OF_FRAMES + " Frames und evtl. Bonuswürfen) geliefert!");
		}
		return result;
	}
}
