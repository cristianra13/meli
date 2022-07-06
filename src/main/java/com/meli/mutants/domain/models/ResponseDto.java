package com.meli.mutants.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto implements Serializable {
  public static final long serialVersionUID = 1L;

  @Id
  private String id;
  @Indexed
  private boolean isMutant;
  private RequestDto dna;
  private int statusCode;
  private boolean isError;
  private String message;
  private String reason;
}
