package com.meli.mutants.controller;

import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.Statistics;
import com.meli.mutants.repository.StatisticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class StatisticsControllerTest {

  private final String PATH = "/stats";

  @MockBean
  private StatisticsRepository statisticsRepository;

  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void statsEmptyListTest() throws Exception {

    Statistics statistics = new Statistics(0, 0, new BigDecimal("0"), null);
    when(statisticsRepository.findAll()).thenReturn(Collections.emptyList());

    mockMvc.perform(get(PATH)
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.count_mutant_dna").value(0))
      .andExpect(jsonPath("$.count_human_dna").value(0));
  }

  @Test
  public void statsListTest() throws Exception {
    List<RequestDto> requestDtoList = new ArrayList<>();
    requestDtoList.add(new RequestDto("4214bnj45dsf", List.of("AAAAGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"), true));
    requestDtoList.add(new RequestDto("y9yf87gf8dsg", List.of("ATGCGA", "CAGTGC", "TTATGT", "AGATTG", "CGCCAA", "TCCCTG"), false));
    requestDtoList.add(new RequestDto("9u9sfhiuihdi", List.of("ATGCGB", "ZAGTGC", "UTATGT", "AGAAGG", "CCCCTA", "TCACTG"), true));

    when(statisticsRepository.findAll()).thenReturn(requestDtoList);

    mockMvc.perform(get(PATH)
      .contentType(MediaType.APPLICATION_JSON_VALUE))
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.count_mutant_dna").value(2))
      .andExpect(jsonPath("$.count_human_dna").value(1));

  }

}