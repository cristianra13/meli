package com.meli.mutants.domain.service.impl;

import com.meli.mutants.domain.exception.BusinessException;
import com.meli.mutants.domain.exception.ConstantsException;
import com.meli.mutants.domain.exception.DnaSequenceEmptyOrNullException;
import com.meli.mutants.domain.exception.InvalidDnaSequenceException;
import com.meli.mutants.domain.exception.IsNotMutantException;
import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.ResponseDto;
import com.meli.mutants.domain.service.IMutantService;
import com.meli.mutants.repository.MutantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class to validate DNA
 */
@Service
@Slf4j
public class MutantServiceImpl implements IMutantService {

  @Autowired
  private MutantRepository repository;

  @Override
  public ResponseDto isMutant(RequestDto dnaSequence)
    throws BusinessException, DnaSequenceEmptyOrNullException, InvalidDnaSequenceException, IsNotMutantException {
    log.info("Start validation dna sequence");
    if (dnaSequence == null || dnaSequence.getDna() == null || dnaSequence.getDna().size() == 0) {
      throw new DnaSequenceEmptyOrNullException(ConstantsException.DNA_SEQUENCE_EMPTY);
    }
    char[][] sequenceMatrix = setSequenceToArr(dnaSequence.getDna());

    for (char[] sequence : sequenceMatrix) {
      if (!validateSequence(sequence)) {
        log.error("Sequence is not valid " + Arrays.toString(sequence));
        throw new InvalidDnaSequenceException(ConstantsException.DNA_SEQUENCE_INVALID);
      }
    }

    if (validateRow(sequenceMatrix) || validateColumn(sequenceMatrix)
      || validateDiagonal(sequenceMatrix) || validateDiagonalInverse(sequenceMatrix)) {
      log.info("Sequence valid " + Arrays.deepToString(sequenceMatrix));
      dnaSequence.setMutant(true);
    }

    RequestDto dnaSaved = repository.save(dnaSequence);
    log.info("Sequence saved successfully");
    if (dnaSaved.isMutant()) {
      return isMutantResponse(dnaSaved);
    } else {
      throw new IsNotMutantException(ConstantsException.DNA_IS_NOT_A_MUTANT);
    }

  }

  public char[][] setSequenceToArr(List<String> seq) throws BusinessException {
    log.info("Convert sequence to char[][] " + seq);
    int lengthSeq = seq.size();
    char[][] sequence = new char[lengthSeq][lengthSeq];

    for (int i = 0; i < lengthSeq; i++) {
      char[] chars = seq.get(i).toCharArray();
      for (int j = 0; j < chars.length; j++) {
        sequence[i][j] = chars[j];
      }
    }
    return sequence;
  }

  private boolean validateRow(char[][] sequenceMatrix) throws BusinessException {
    log.info("Start validation to sequence by row");
    int contDna = 0;
    for (int i = 0; i < sequenceMatrix.length; i++) {
      for (int j = 0; j < sequenceMatrix[i].length - 1; j++) {
        char letter = sequenceMatrix[i][j];
        if (letter == sequenceMatrix[i][j + 1]) {
          contDna++;
          if (contDna >= 4) {
            return true;
          }
        } else {
          contDna = 0;
        }
      }
    }
    return false;
  }

  private boolean validateColumn(char[][] sequenceMatrix) throws BusinessException {
    log.info("Start validation to sequence by column");
    int contDna = 0;
    for (int i = 0; i < sequenceMatrix.length; i++) {
      for (int j = 0; j < sequenceMatrix.length - 1; j++) {
        char letter = sequenceMatrix[j][i];
        if (letter == sequenceMatrix[j + 1][i]) {
          contDna++;
          if (contDna >= 4) {
            return true;
          }
        } else {
          contDna = 0;
        }
      }
    }
    return false;
  }

  private boolean validateDiagonal(char[][] sequenceMatrix) throws BusinessException {
    log.info("Start validation to sequence by main diagonal");
    int contDna = 0;
    for (int i = 0; i < sequenceMatrix.length - 1; i++) {
      char charSeq = sequenceMatrix[i][i];
      if (charSeq == sequenceMatrix[i + 1][i + 1]) {
        contDna++;
        if (contDna >= 4) {
          return true;
        }
      }
    }
    return false;
  }

  private boolean validateDiagonalInverse(char[][] sequenceMatrix) throws BusinessException {
    log.info("Start validation to sequence by secondary diagonal");
    List<Character> letters = new ArrayList<>();
    for (int i = 0, j = sequenceMatrix.length - 1; i < sequenceMatrix.length && j >= 0; i++, j--) {
      letters.add(sequenceMatrix[j][i]);
    }

    Map<Character, Long> collect = letters.stream()
      .collect(Collectors.groupingBy(letter -> letter, Collectors.mapping(letter -> letter >= 4, Collectors.counting())));

    Long max = collect.values().stream().max(Comparator.naturalOrder()).orElse(0L);

    if (max >= 4) {
      return true;
    }
    return false;
  }

  private boolean validateSequence(char[] sequenceMatrix) {
    return String.valueOf(sequenceMatrix).matches("[ATGCatgc]*");
  }

  private ResponseDto isMutantResponse(RequestDto requestDto) {
    return new ResponseDto(requestDto.getId(), requestDto.isMutant(), requestDto, HttpStatus.OK.value(), false, "Success", null);
  }

}
