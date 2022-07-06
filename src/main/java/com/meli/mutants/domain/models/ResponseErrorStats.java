package com.meli.mutants.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorStats implements Serializable {
  private static final long serialVersionUID = 1L;

  private int statusCode;
  private boolean isError;
  private String message;
  private String reason;
}
