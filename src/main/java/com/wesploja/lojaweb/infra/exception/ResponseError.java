package com.wesploja.lojaweb.infra.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseError(String message, HttpStatus http, LocalDateTime time) {
}
