package com.meli.mutants.domain.service;

import com.meli.mutants.domain.exception.BusinessException;
import com.meli.mutants.domain.exception.DnaSequenceEmptyOrNullException;
import com.meli.mutants.domain.exception.InvalidDnaSequenceException;
import com.meli.mutants.domain.exception.IsNotMutantException;
import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.ResponseDto;

public interface IMutantService {

  ResponseDto isMutant(RequestDto dnaSecuence) throws BusinessException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException, IsNotMutantException;

}
