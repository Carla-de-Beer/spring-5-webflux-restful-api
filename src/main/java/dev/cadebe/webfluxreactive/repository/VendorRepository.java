package dev.cadebe.webfluxreactive.repository;

import dev.cadebe.webfluxreactive.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
