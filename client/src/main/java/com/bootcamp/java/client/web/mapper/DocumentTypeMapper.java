package com.bootcamp.java.client.web.mapper;

import com.bootcamp.java.client.domain.DocumentType;
import com.bootcamp.java.client.web.model.DocumentTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {
    DocumentType modelToEntity(DocumentTypeModel model);
    DocumentTypeModel entityToModel(DocumentType event);
    @Mapping(target = "id", ignore = true)
    void update(@MappingTarget DocumentType entity, DocumentType updateEntity);
}
