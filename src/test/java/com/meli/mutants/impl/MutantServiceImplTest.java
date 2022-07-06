package com.meli.mutants.impl;

import com.meli.mutants.domain.exception.BusinessException;
import com.meli.mutants.domain.exception.ConstantsException;
import com.meli.mutants.domain.exception.DnaSequenceEmptyOrNullException;
import com.meli.mutants.domain.exception.InvalidDnaSequenceException;
import com.meli.mutants.domain.exception.IsNotMutantException;
import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.ResponseDto;
import com.meli.mutants.domain.service.IMutantService;
import com.meli.mutants.repository.MutantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class MutantServiceImplTest {

  @Autowired
  private IMutantService mutantService;

  @MockBean
  private MutantRepository repository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void isMutantEmptySequenceExceptionTest() {
    DnaSequenceEmptyOrNullException sequenceEmptyOrNullException = Assertions.assertThrows(DnaSequenceEmptyOrNullException.class,
      () -> {
        RequestDto requestDto = new RequestDto();
        mutantService.isMutant(requestDto);
      });
    Assertions.assertEquals(ConstantsException.DNA_SEQUENCE_EMPTY, sequenceEmptyOrNullException.getMessage());
  }

  @Test
  public void isMutantInvalidSequenceExceptionTest() {
    RequestDto requestDto = new RequestDto();
    InvalidDnaSequenceException invalidDnaSequenceException = Assertions.assertThrows(InvalidDnaSequenceException.class,
      () -> {
        List<String> dnaSequence = List.of("ATGCGB", "ZAGTGC", "UTATGT", "AGAAGG", "CCCCTA", "TCACTG");
        requestDto.setDna(dnaSequence);
        mutantService.isMutant(requestDto);
      });
    Assertions.assertEquals(ConstantsException.DNA_SEQUENCE_INVALID, invalidDnaSequenceException.getMessage());
  }

  @Test
  public void isNotMutantTest() {
    RequestDto requestDto = new RequestDto();
    IsNotMutantException isNotMutantException = Assertions.assertThrows(IsNotMutantException.class,
      () -> {
        List<String> dnaSequence = List.of("ATGCGA", "CAGTGC", "TTATGT", "AGATTG", "CGCCAA", "TCCCTG");
        requestDto.setDna(dnaSequence);
        requestDto.setMutant(false);
        Mockito.when(repository.save(any())).thenReturn(requestDto);
        mutantService.isMutant(requestDto);
      });
    Assertions.assertEquals(ConstantsException.DNA_IS_NOT_A_MUTANT, isNotMutantException.getMessage());
  }

  @Test
  public void isMutantSuccessRowTest()
    throws IsNotMutantException, BusinessException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException {
    RequestDto requestDto = new RequestDto();
    List<String> dnaSequence = List.of("AAAAGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG");
    requestDto.setDna(dnaSequence);

    RequestDto requestDtoResponse = new RequestDto();
    requestDtoResponse.setId("7y87tv876tcx876t6cxtv");
    requestDtoResponse.setDna(dnaSequence);
    requestDtoResponse.setMutant(true);

    Mockito.when(repository.save(any())).thenReturn(requestDtoResponse);

    ResponseDto mutant = mutantService.isMutant(requestDto);

    Assertions.assertEquals("7y87tv876tcx876t6cxtv", mutant.getId());
    Assertions.assertEquals(dnaSequence, mutant.getDna().getDna());
    Assertions.assertTrue(mutant.isMutant());
  }

  @Test
  public void isMutantSuccessColumnTest()
    throws IsNotMutantException, BusinessException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException {
    RequestDto requestDto = new RequestDto();
    List<String> dnaSequence = List.of("TTGCGA", "TGGTGC", "TCATGT", "TGAAGG", "TCCTAG", "TCACTG");
    requestDto.setDna(dnaSequence);

    RequestDto requestDtoResponse = new RequestDto();
    requestDtoResponse.setId("7y87tv876tcx876t6cxtv");
    requestDtoResponse.setDna(dnaSequence);
    requestDtoResponse.setMutant(true);

    Mockito.when(repository.save(any())).thenReturn(requestDtoResponse);

    ResponseDto mutant = mutantService.isMutant(requestDto);

    Assertions.assertEquals("7y87tv876tcx876t6cxtv", mutant.getId());
    Assertions.assertEquals(dnaSequence, mutant.getDna().getDna());
    Assertions.assertTrue(mutant.isMutant());
  }

  @Test
  public void isMutantSuccessDiagonalTest()
    throws IsNotMutantException, BusinessException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException {
    RequestDto requestDto = new RequestDto();
    List<String> dnaSequence = List.of("ATGCGA", "CAGTGC", "TAAACT", "AGAAGG", "CGCCAA", "CGCCAA");
    requestDto.setDna(dnaSequence);

    RequestDto requestDtoResponse = new RequestDto();
    requestDtoResponse.setId("7y87tv876tcx876t6cxtv");
    requestDtoResponse.setDna(dnaSequence);
    requestDtoResponse.setMutant(true);

    Mockito.when(repository.save(any())).thenReturn(requestDtoResponse);

    ResponseDto mutant = mutantService.isMutant(requestDto);

    Assertions.assertEquals("7y87tv876tcx876t6cxtv", mutant.getId());
    Assertions.assertEquals(dnaSequence, mutant.getDna().getDna());
    Assertions.assertTrue(mutant.isMutant());
  }

}