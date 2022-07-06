package com.meli.mutants.repository;

import com.meli.mutants.domain.models.RequestDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MutantRepository extends MongoRepository<RequestDto, String> {

}
