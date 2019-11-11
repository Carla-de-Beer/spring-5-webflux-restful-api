package com.cadebe.webfluxreactive.repository;

import com.cadebe.webfluxreactive.domain.Vendor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
