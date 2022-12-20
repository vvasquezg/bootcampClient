package com.bootcamp.java.client.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentTypeModel {
    @JsonIgnore
    private String id;
    @NotBlank(message = "Name cannot be null or empty")
    private String name;
    @NotBlank(message = "Code cannot be null or empty")
    private String code;
    @NotBlank(message = "Client Type cannot be null or empty")
    private String clientType;
    @NotBlank(message = "Client Profile cannot be null or empty")
    private String clientProfile;
    private Boolean validateBusinessName;
    private Boolean active;
}
