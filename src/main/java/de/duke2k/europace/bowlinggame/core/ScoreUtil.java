package de.duke2k.europace.bowlinggame.core;

import org.apache.commons.lang3.tuple.Pair;

class ScoreUtil {

	private ScoreUtil() {
	}

	static boolean isStrike(Pair<Integer, Integer> frameScore) {
		return frameScore.getLeft() == 10;
	}

	static boolean isSpare(Pair<Integer, Integer> frameScore) {
		return !isStrike(frameScore) && frameScore.getLeft() + frameScore.getRight() == 10;
	}
}
