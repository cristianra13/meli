package com.meli.mutants.impl;

import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.Statistics;
import com.meli.mutants.domain.service.IStatisticsService;
import com.meli.mutants.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class StatisticsServiceImplTest {

  @Autowired
  private IStatisticsService service;

  @Autowired
  @MockBean
  private StatisticsRepository repository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void statsEmptyList() {
    when(repository.findAll()).thenReturn(Collections.emptyList());

    Statistics stats = service.stats();
    assertEquals(0, stats.getCountMutants());
    assertEquals(0, stats.getCountHumans());
  }

  @Test
  public void statsFindALltest() {
    List<RequestDto> requestDtoList = new ArrayList<>();
    requestDtoList.add(new RequestDto("4214bnj45dsf", List.of("AAAAGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"), true));
    requestDtoList.add(new RequestDto("y9yf87gf8dsg", List.of("ATGCGA", "CAGTGC", "TTATGT", "AGATTG", "CGCCAA", "TCCCTG"), false));
    requestDtoList.add(new RequestDto("9u9sfhiuihdi", List.of("ATGCGB", "ZAGTGC", "UTATGT", "AGAAGG", "CCCCTA", "TCACTG"), true));

    when(repository.findAll()).thenReturn(requestDtoList);

    Statistics stats = service.stats();

    assertEquals(2, stats.getCountMutants());
    assertEquals(1, stats.getCountHumans());

  }

}