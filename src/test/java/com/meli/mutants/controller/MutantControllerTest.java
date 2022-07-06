package com.meli.mutants.controller;

import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.repository.MutantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableWebMvc
class MutantControllerTest {

  protected MockMvc mockMvc;

  @MockBean
  private MutantRepository mutantRepository;

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  public void setup() {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void mutantBadRequestTest() throws Exception {
    String request = "";
    mockMvc.perform(
        post("/mutant")
          .contentType(MediaType.APPLICATION_JSON)
          .content(request))
      .andExpect(status().isBadRequest());
  }

  @Test
  public void mutantEmptyListTest() throws Exception {
    String request = "{\n" +
      "    \"dna\": [\n" +
      "    ]\n" +
      "}";
    mockMvc.perform(
        post("/mutant")
          .contentType(MediaType.APPLICATION_JSON)
          .content(request))
      .andExpect(status().isForbidden());
  }

  @Test
  public void validateMutantTrueTest() throws Exception {
    String request = "{\n" +
      "    \"dna\": [\n" +
      "        \"ATGCGA\",\n" +
      "        \"CAGTGC\",\n" +
      "        \"TTATGT\",\n" +
      "        \"AGAAGG\",\n" +
      "        \"CCCCTA\",\n" +
      "        \"TCACTG\"\n" +
      "    ]\n" +
      "}";

    RequestDto requestDto = new RequestDto();
    requestDto.setId("8yf87gy8f7gfyhbvf");
    requestDto.setDna(List.of("ATGCGA", "AAGTGC", "ATATGT", "AGAAGG", "CCCCTA", "TCACTG"));
    requestDto.setMutant(true);
    Mockito.when(mutantRepository.save(any())).thenReturn(requestDto);

    mockMvc.perform(
        post("/mutant")
          .contentType(MediaType.APPLICATION_JSON_VALUE)
          .content(request))
      .andExpect(status().isOk());
  }

  @Test
  public void validateMutantFalseTest() throws Exception {
    String request = "{\n" +
      "    \"dna\": [\n" +
      "        \"ATGCGA\",\n" +
      "        \"CACTGC\",\n" +
      "        \"TTGTCT\",\n" +
      "        \"AGAAGG\",\n" +
      "        \"TCTCTA\",\n" +
      "        \"TCACTG\"\n" +
      "    ]\n" +
      "}";

    RequestDto requestDto = new RequestDto();
    requestDto.setId("8yf87gy8f7gfyhbvf");
    requestDto.setDna(List.of("ATGCGA", "CACTGC", "TTGTCT", "AGAAGG", "TCTCTA", "TCACTG"));
    requestDto.setMutant(false);
    Mockito.when(mutantRepository.save(any())).thenReturn(requestDto);

    mockMvc.perform(
        post("/mutant")
          .contentType("application/json")
          .content(request))
      .andExpect(status().isForbidden());
  }

}