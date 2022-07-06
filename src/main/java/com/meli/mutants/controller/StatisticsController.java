package com.meli.mutants.controller;

import com.meli.mutants.domain.exception.ExceptionHandling;
import com.meli.mutants.domain.models.Statistics;
import com.meli.mutants.domain.service.IStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/stats")
public class StatisticsController extends ExceptionHandling {

  @Autowired
  private IStatisticsService service;

  @GetMapping()
  public ResponseEntity<?> getStats() {
    Statistics statistics = service.stats();
    return ResponseEntity.ok(statistics);
  }
}
