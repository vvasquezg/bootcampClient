package com.bootcamp.java.client.service;

import com.bootcamp.java.client.service.exception.InvalidNameClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bootcamp.java.client.domain.Client;
import com.bootcamp.java.client.repository.ClientRepository;
import com.bootcamp.java.client.service.exception.InvalidClientDocumentTypeException;
import com.bootcamp.java.client.web.mapper.ClientMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private ClientMapper clientMapper;

	@Autowired
	private DocumentTypeService documentTypeService;

	public Flux<Client> findAll(){
		log.debug("findAll executed");
		return clientRepository.findAll();
	}

	public Mono<Client> findById(String customerId){
		log.debug("findById executed {}", customerId);
		return clientRepository.findById(customerId);
	}

	public Mono<Client> create(Client client) {
		log.debug("create executed {}", client);
		return documentTypeService.findTopByCodeAndClientProfile(client.getIdentityDocumentType(), client.getClientProfile())
				.switchIfEmpty(Mono.error(new InvalidClientDocumentTypeException()))
				.flatMap(documentType -> {
					client.setClientType(documentType.getClientType());
					return validateName(client, documentType.getValidateBusinessName())
							.flatMap(dbClient -> clientRepository.save(dbClient));
				});
	}

	public Mono<Client> update(String clientId, Client client){
		log.debug("update executed {}:{}", clientId, client);
		return clientRepository.findById(clientId)
		.flatMap(dbClient -> {
			clientMapper.update(dbClient, client);
			return documentTypeService.findTopByCodeAndClientProfile(dbClient.getIdentityDocumentType(), dbClient.getClientProfile())
					.switchIfEmpty(Mono.error(new InvalidClientDocumentTypeException()))
					.flatMap(documentType -> {
						dbClient.setClientType(documentType.getClientType());
						return validateName(dbClient, documentType.getValidateBusinessName())
								.flatMap(clientValidate -> clientRepository.save(clientValidate));
					});
		});
	}

	private Mono<Client> validateName(Client client, boolean validateBusinessName) {
		boolean nameIsValid = validateBusinessName
				? client.getBusinessName() != null && !client.getBusinessName().isEmpty()
				: client.getName() != null && !client.getName().isEmpty() && client.getLastName() != null && !client.getLastName().isEmpty();
		return nameIsValid ? Mono.just(client) : Mono.error(new InvalidNameClientException());
	}

	public Mono<Client> delete(String clientId){
		log.debug("delete executed {}", clientId);
		return clientRepository.findById(clientId)
		.flatMap(existingCustomer -> clientRepository.delete(existingCustomer)
		.then(Mono.just(existingCustomer)));
	}

	public Mono<Client> findTopByIdentityDocumentNumberAndIdentityDocumentType(String identityDocumentNumber, String identityDocumentType){
		log.debug("findById executed {} {}", identityDocumentNumber, identityDocumentType);
		return clientRepository.findTopByIdentityDocumentNumberAndIdentityDocumentType(identityDocumentNumber, identityDocumentType);
	}
} 
