package com.meli.mutants.domain.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class HttpResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING,
    pattern = "dd-MM-yyyy HH:mm:ss",
    timezone = "America/Bogota")
  private Date timestamp;
  private int httpStatusCode; // 200, 201, 400, 500....
  private HttpStatus httpStatus;
  private String reason;
  private String message;

  public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
    this.timestamp = new Date();
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.reason = reason;
    this.message = message;
  }
}
