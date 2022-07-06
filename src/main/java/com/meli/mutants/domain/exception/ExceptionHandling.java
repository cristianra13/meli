package com.meli.mutants.domain.exception;

import com.meli.mutants.domain.models.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestControllerAdvice
@Slf4j
public class ExceptionHandling implements ErrorController {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<HttpResponse> dnaSequenceEmpty() {
    return createHttpResponse(FORBIDDEN, ConstantsException.DNA_SEQUENCE_EMPTY);
  }

  @ExceptionHandler(InvalidDnaSequenceException.class)
  public ResponseEntity<HttpResponse> invalidSequenceEmpty() {
    return createHttpResponse(FORBIDDEN, ConstantsException.DNA_SEQUENCE_INVALID);
  }

  @ExceptionHandler(IsNotMutantException.class)
  public ResponseEntity<HttpResponse> isNotMuttantException() {
    return createHttpResponse(FORBIDDEN, ConstantsException.DNA_IS_NOT_A_MUTANT);
  }

  @ExceptionHandler(DnaSequenceEmptyOrNullException.class)
  public ResponseEntity<HttpResponse> dnaSequenceEmptyOrNullException() {
    return createHttpResponse(FORBIDDEN, ConstantsException.DNA_SEQUENCE_EMPTY);
  }

  /**
   * Metodo de respuesta general, encargado de proveer
   * una firma generica para cada método de capura de
   * excepciones
   *
   * @param httpStatus
   * @param message
   * @return ResponseEntity<HttpResponse>
   */
  private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
    HttpResponse httpResponse =
      new HttpResponse(httpStatus.value(),
        httpStatus,
        httpStatus.getReasonPhrase().toUpperCase(),
        message.toUpperCase());
    //return new ResponseEntity<>(httpResponse, httpStatus);
    return ResponseEntity.status(httpStatus).body(httpResponse);
  }

}
