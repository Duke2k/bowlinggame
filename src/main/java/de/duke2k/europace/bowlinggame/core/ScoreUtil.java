package de.duke2k.europace.bowlinggame.core;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

class ScoreUtil {

	private ScoreUtil() {
	}

	static boolean isStrike(@Nullable Pair<Integer, Integer> frameScore) {
		return frameScore != null && frameScore.getLeft() == Score.NUMBER_OF_PINS;
	}

	static boolean isSpare(@Nullable Pair<Integer, Integer> frameScore) {
		return frameScore != null && (!isStrike(frameScore) && frameScore.getLeft() + frameScore.getRight() == Score.NUMBER_OF_PINS);
	}
}
