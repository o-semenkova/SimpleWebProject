package com.lohika.simpleweb.rest;

import com.lohika.simpleweb.exceptions.BasicException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.logging.Logger;

@ControllerAdvice
public class RestExceptionHandler {

  private static final Logger log = Logger.getLogger(RestExceptionHandler.class.getName());

  public RestExceptionHandler() {
    log.info("Creating default REST exceptions handler...\n\n\n");
  }

  @ExceptionHandler(value = { BasicException.class })
  protected ResponseEntity<Object> handleBasicException(final BasicException ex, final WebRequest request) {
    return new ResponseEntity<Object>(new ErrorObject(ex.getMessage()), new HttpHeaders(), HttpStatus.OK);
  }


  @ExceptionHandler(value = { AuthenticationException.class })
  protected ResponseEntity<Object> handleAuthException(final AuthenticationException ex, final WebRequest request) {
    return new ResponseEntity<Object>(new ErrorObject(ex.getMessage(), true), new HttpHeaders(), HttpStatus.OK);
  }


  public class ErrorObject {

    private String error;
    private boolean auth;

    public ErrorObject() {
      // constructor for serializer
    }

    public ErrorObject(String message) {
      this.error = message;
      this.auth = false;
    }

    public ErrorObject(String message, boolean isAuthProblem) {
      this.error = message;
      this.auth = isAuthProblem;
    }

    public String getError() {
      return error;
    }

    public void setError(String error) {
      this.error = error;
    }

    public boolean isAuth() {
      return auth;
    }

    public void setAuth(boolean auth) {
      this.auth = auth;
    }
  }
}
