package com.bootcamp.java.client.web.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bootcamp.java.client.domain.Client;
import com.bootcamp.java.client.web.model.ClientModel;

@Mapper(componentModel = "spring")
public interface ClientMapper {
	Client modelToEntity(ClientModel model);
	ClientModel entityToModel(Client event);
	@Mapping(target = "id", ignore = true)
	void update(@MappingTarget Client entity, Client updateEntity);
}
