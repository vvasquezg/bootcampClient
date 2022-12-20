package com.bootcamp.java.client.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "code" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "documentType")
public class DocumentType {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String code;
    @NotNull
    private String clientType;
    @NotNull
    private String clientProfile;
    @NotNull
    private Boolean validateBusinessName;
    @NotNull
    private Boolean active;
}
