package com.cadebe.webfluxreactive.repository;

import com.cadebe.webfluxreactive.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository <Category, String>{
}
