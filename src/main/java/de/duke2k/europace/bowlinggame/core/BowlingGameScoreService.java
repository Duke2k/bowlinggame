package de.duke2k.europace.bowlinggame.core;

import java.util.Iterator;

import org.apache.commons.lang3.tuple.Pair;
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
		int result = 0;
		boolean lastFrameWasSpare = false;
		boolean lastFrameWasStrike = false;
		boolean lastTwoFramesWereStrikes = false;
		int count = 0;
		Iterator<Pair<Integer, Integer>> iterator = score.iterator();
		while (iterator.hasNext()) {
			Pair<Integer, Integer> currentFrame = iterator.next();
			result += currentFrame.getLeft();
			result += currentFrame.getRight();
			if (lastFrameWasSpare) {
				result += currentFrame.getLeft();
				lastFrameWasSpare = false;
			}
			if (lastFrameWasStrike) {
				result += currentFrame.getLeft();
				if (!ScoreUtil.isStrike(currentFrame)) {
					result += currentFrame.getRight();
				} else {
					lastTwoFramesWereStrikes = true;
				}
				lastFrameWasStrike = false;
			}
			if (lastTwoFramesWereStrikes && count < Score.NUMBER_OF_FRAMES) {
				result += currentFrame.getLeft();
				lastTwoFramesWereStrikes = false;
			}
			if (ScoreUtil.isSpare(currentFrame)) {
				lastFrameWasSpare = true;
			}
			if (ScoreUtil.isStrike(currentFrame)) {
				lastFrameWasStrike = true;
			}
			count++;
		}
		return result;
	}
}
