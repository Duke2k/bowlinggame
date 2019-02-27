package de.duke2k.europace.bowlinggame.core;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Ungültige Punkte")
public class InvalidScoreException extends Exception {

	InvalidScoreException(String message) {
		super(message);
	}
}
