package de.duke2k.europace.bowlinggame.core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.Pair;

class Score {

	static final int NUMBER_OF_FRAMES = 10;

	private List<Pair<Integer, Integer>> frameScores;

	Score() {
		frameScores = new ArrayList<>(NUMBER_OF_FRAMES);
	}

	@Nullable
	Pair<Integer, Integer> getFrameScore(@Nonnegative int position) {
		return frameScores.get(position);
	}

	void addFrameScore(@Nonnull Pair<Integer, Integer> frameScore) {
		frameScores.add(frameScore);
	}

	@Nonnull
	Stream<Pair<Integer, Integer>> stream() {
		return frameScores.stream();
	}
}
