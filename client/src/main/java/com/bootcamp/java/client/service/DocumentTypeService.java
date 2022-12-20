package com.bootcamp.java.client.service;

import com.bootcamp.java.client.domain.DocumentType;
import com.bootcamp.java.client.repository.DocumentTypeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DocumentTypeService {
    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    public Flux<DocumentType> findAll(){
        log.debug("findAll executed");
        return documentTypeRepository.findAll();
    }

    public Mono<DocumentType> findTopByCodeAndClientTypeAndClientProfile(String code, String clientType, String clientProfile){
        log.debug("findById executed {} - {} - {}", code, clientType, clientProfile);
        return documentTypeRepository.findTopByCodeAndClientTypeAndClientProfileAndActive(code, clientType, clientProfile, true);
    }

    public Mono<DocumentType> findTopByCodeAndClientProfile(String code, String clientProfile){
        log.debug("findById executed {} - {}", code, clientProfile);
        return documentTypeRepository.findTopByCodeAndClientProfileAndActive(code, clientProfile, true);
    }

    public Mono<DocumentType> create(DocumentType documentType){
        log.debug("create executed {}", documentType);
        return documentTypeRepository.save(documentType);
    }
}
