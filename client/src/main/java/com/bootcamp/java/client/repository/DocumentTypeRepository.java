package com.bootcamp.java.client.repository;

import com.bootcamp.java.client.domain.DocumentType;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DocumentTypeRepository extends ReactiveMongoRepository<DocumentType, String> {
    Mono<DocumentType> findTopByCodeAndClientTypeAndClientProfileAndActive(String code, String clientType, String clientProfile, Boolean active);

    Mono<DocumentType> findTopByCodeAndClientProfileAndActive(String code, String clientProfile, Boolean active);
}
