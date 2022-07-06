package com.meli.mutants.domain.service.impl;

import com.meli.mutants.domain.models.RequestDto;
import com.meli.mutants.domain.models.Statistics;
import com.meli.mutants.domain.service.IStatisticsService;
import com.meli.mutants.repository.StatisticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Class to calculate mutants and humans percent
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {

  @Autowired
  private StatisticsRepository repository;

  /**
   * Calculate humans and mutants percent
   * from all saved registers of mongodb
   *
   * @return Statistics
   */
  @Override
  public Statistics stats() {
    Statistics statistics = new Statistics();
    List<RequestDto> allItems = repository.findAll();
    if (allItems.size() > 0) {
      Map<Boolean, Long> groupMutants = allItems.stream()
        .collect(Collectors.groupingBy(RequestDto::isMutant, Collectors.counting()));

      if (groupMutants.size() > 0) {
        groupMutants.forEach((key, val) -> {
          if (key) {
            statistics.setCountMutants(val);
          } else {
            statistics.setCountHumans(val);
          }
        });
        double ratio = statistics.getCountMutants() / statistics.getCountHumans();
        statistics.setRatio(roundNumberRatio(ratio));
      }
      return statistics;
    }
    return new Statistics(0, 0, new BigDecimal("0"), null);
  }

  public BigDecimal roundNumberRatio(double value) {
    BigDecimal ratio = new BigDecimal(value);
    ratio = ratio.setScale(2, RoundingMode.HALF_UP);
    return ratio;
  }
}
