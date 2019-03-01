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
		String[] splittedRawScore = validateCharactersIn(rawScore);
		Score result = validateNumbersOfPinsIn(splittedRawScore);
		validateNumberOfFramesIncludingBonusIn(splittedRawScore, result);
		return result;
	}

	private String[] validateCharactersIn(String rawScore) throws InvalidScoreException {
		String[] splittedRawScore = StringUtils.split(rawScore, ',');
		if (!Arrays.stream(splittedRawScore).allMatch(rs -> StringUtils.containsOnly(rs, "-0123456789"))) {
			throw new InvalidScoreException("Es sind nur Ziffern 0-9 oder '-' zulässig!");
		}
		return splittedRawScore;
	}

	private Score validateNumbersOfPinsIn(String[] splittedRawScore) throws InvalidScoreException {
		Score result = new Score();
		for (int i = 0; i < splittedRawScore.length; i += 2) {
			Integer left = Integer.valueOf(splittedRawScore[i]);
			Integer right;
			if (i + 1 < splittedRawScore.length) {
				right = Integer.valueOf(StringUtils.equals(splittedRawScore[i + 1], "-") ? "0" : splittedRawScore[i + 1]);
			} else {
				right = 0;
			}
			if (left < 0 || left > Score.NUMBER_OF_PINS || right < 0 || right > Score.NUMBER_OF_PINS || left + right > Score.NUMBER_OF_PINS) {
				throw new InvalidScoreException("Ungültige Anzahl umgeworfener Pins bei Frame " + (i / 2 + 1) + "!");
			}
			result.addFrameScore(new ImmutablePair<>(left, right));
		}
		return result;
	}

	private void validateNumberOfFramesIncludingBonusIn(String[] splittedRawScore, Score result) throws InvalidScoreException {
		Pair<Integer, Integer> lastFrame = result.getFrameScore(Score.NUMBER_OF_FRAMES - 1);
		int bonusCount = ScoreUtil.isSpare(lastFrame) || ScoreUtil.isStrike(lastFrame) ? 1 : 0;
		if (splittedRawScore.length != Score.NUMBER_OF_FRAMES * 2 + bonusCount) {
			throw new InvalidScoreException("Es wurden nicht genau " +
					(Score.NUMBER_OF_FRAMES * 2 + bonusCount) + " Werte (bei " +
					Score.NUMBER_OF_FRAMES + " Frames und evtl. Bonuswurf) geliefert!");
		}
	}
}
