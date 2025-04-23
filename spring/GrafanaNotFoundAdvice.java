package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class GrafanaNotFoundAdvice {

  @ExceptionHandler(GrafanaNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  String grafanaNotFoundHandler(GrafanaNotFoundException ex) {
    return ex.getMessage();
  }
}