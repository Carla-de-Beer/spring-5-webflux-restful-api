package dev.cadebe.webfluxreactive.repository;

import dev.cadebe.webfluxreactive.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository <Category, String>{
}
