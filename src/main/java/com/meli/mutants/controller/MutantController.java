package com.meli.mutants.controller;

import com.meli.mutants.domain.exception.BusinessException;
import com.meli.mutants.domain.exception.DnaSequenceEmptyOrNullException;
import com.meli.mutants.domain.exception.ExceptionHandling;
import com.meli.mutants.domain.exception.InvalidDnaSequenceException;
import com.meli.mutants.domain.exception.IsNotMutantException;
import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.ResponseDto;
import com.meli.mutants.domain.service.IMutantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(path = "/mutant")
public class MutantController extends ExceptionHandling {
  @Autowired
  private IMutantService mutantService;

  @RequestMapping(
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> isMutant(@RequestBody RequestDto dna)
    throws BusinessException, IsNotMutantException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException {
    ResponseDto mutant = mutantService.isMutant(dna);
    return ResponseEntity.ok(mutant);
  }
}
