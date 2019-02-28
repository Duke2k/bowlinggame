package de.duke2k.europace.bowlinggame.controller;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import de.duke2k.europace.bowlinggame.core.InvalidScoreException;

@RestControllerAdvice
class ControllerAdvice {

	@ExceptionHandler(InvalidScoreException.class)
	public Object handleInvalidScoreException(InvalidScoreException e) {
		return Pair.of("Ungültige Punkte", e.getMessage());
	}
}
