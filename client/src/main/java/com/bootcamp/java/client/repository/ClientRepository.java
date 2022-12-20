package com.bootcamp.java.client.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.bootcamp.java.client.domain.Client;

import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
	Mono<Client> findTopByIdentityDocumentNumberAndIdentityDocumentType(String identityDocumentNumber, String identityDocumentType);
}
