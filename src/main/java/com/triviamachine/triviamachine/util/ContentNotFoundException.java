package com.triviamachine.triviamachine.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Content Not Found")
public class ContentNotFoundException extends RuntimeException {
}
