package com.game.entity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PlayerBadRequestException extends RuntimeException {

        private static final long serialVersionUID = 2701569702621294192L;

        public PlayerBadRequestException() {
        }
}
