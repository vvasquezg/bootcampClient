package com.bootcamp.java.client.web;

import com.bootcamp.java.client.service.ClientService;
import com.bootcamp.java.client.service.DocumentTypeService;
import com.bootcamp.java.client.web.mapper.ClientMapper;
import com.bootcamp.java.client.web.mapper.DocumentTypeMapper;
import com.bootcamp.java.client.web.model.ClientModel;
import com.bootcamp.java.client.web.model.DocumentTypeModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/documentType")
public class DocumentTypeController {
    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    String port;

    @Autowired
    private DocumentTypeService documentTypeService;

    @Autowired
    private DocumentTypeMapper documentTypeMapper;

    @GetMapping
    public Mono<ResponseEntity<Flux<DocumentTypeModel>>> getAll(){
        log.info("getAll executed");
        return Mono.just(ResponseEntity.ok()
                .body(documentTypeService.findAll()
                        .map(client -> documentTypeMapper.entityToModel(client))));
    }

    @PostMapping
    public Mono<ResponseEntity<DocumentTypeModel>> create(@Valid @RequestBody DocumentTypeModel request){
        log.info("create executed {}", request);
        return documentTypeService.create(documentTypeMapper.modelToEntity(request))
                .map(documentType -> documentTypeMapper.entityToModel(documentType))
                .flatMap(c ->
                        Mono.just(ResponseEntity.created(URI.create(String.format("http://%s:%s/%s/%s", name,
                                        port, "client", c.getId())))
                                .body(c)))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
