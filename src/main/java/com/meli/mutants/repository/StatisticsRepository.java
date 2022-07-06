package com.meli.mutants.repository;

import com.meli.mutants.domain.models.RequestDto;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatisticsRepository extends MongoRepository<RequestDto, String> {

}
