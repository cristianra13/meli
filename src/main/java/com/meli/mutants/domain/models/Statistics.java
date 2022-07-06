package com.meli.mutants.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics implements Serializable {

  private static final long serialVersionUID = 1L;
  @JsonProperty(value = "count_mutant_dna")
  private double countMutants;
  @JsonProperty(value = "count_human_dna")
  private double countHumans;
  private BigDecimal ratio;
  private ResponseErrorStats responseErrorStats;
}
