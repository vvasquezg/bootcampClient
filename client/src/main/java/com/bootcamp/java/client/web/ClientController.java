package com.bootcamp.java.client.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.java.client.domain.Client;
import com.bootcamp.java.client.service.ClientService;
import com.bootcamp.java.client.web.mapper.ClientMapper;
import com.bootcamp.java.client.web.model.ClientModel;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/client")
public class ClientController {
	@Value("${spring.application.name}")
	String name;
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private ClientMapper clientMapper;
	
	@GetMapping
	public Mono<ResponseEntity<Flux<ClientModel>>> getAll(){
		log.info("getAll executed");
		return Mono.just(ResponseEntity.ok()
			.body(clientService.findAll()
					.map(client -> clientMapper.entityToModel(client))));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<ClientModel>> getById(@PathVariable String id){
		log.info("getById executed {}", id);
		Mono<Client> response = clientService.findById(id);
		return response
				.map(client -> clientMapper.entityToModel(client))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public Mono<ResponseEntity<ClientModel>> create(@Valid @RequestBody ClientModel request){
		log.info("create executed {}", request);
		return clientService.create(clientMapper.modelToEntity(request))
				.map(client -> clientMapper.entityToModel(client))
				.flatMap(c ->
					Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
							port, "client", c.getId())))
							.body(c)))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<ClientModel>> updateById(@PathVariable String id, @Valid @RequestBody ClientModel request){
		log.info("updateById executed {}:{}", id, request);
		return clientService.update(id, clientMapper.modelToEntity(request))
				.map(client -> clientMapper.entityToModel(client))
				.flatMap(c ->
				Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
						port, "client", c.getId())))
						.body(c)))
				.defaultIfEmpty(ResponseEntity.badRequest().build());
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteById(@PathVariable String id){
		log.info("deleteById executed {}", id);
		return clientService.delete(id)
				.map( r -> ResponseEntity.ok().<Void>build())
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping("/{identityDocumentNumber}/{identityDocumentType}")
	public Mono<ResponseEntity<ClientModel>> getByIdentityDocumentNumberAndIdentityDocumentType(@PathVariable String identityDocumentNumber, @PathVariable String identityDocumentType){
		log.info("getById executed {} {}", identityDocumentNumber, identityDocumentType);
		Mono<Client> response = clientService.findTopByIdentityDocumentNumberAndIdentityDocumentType(identityDocumentNumber, identityDocumentType);
		return response
				.map(client -> clientMapper.entityToModel(client))
				.map(ResponseEntity::ok)
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
