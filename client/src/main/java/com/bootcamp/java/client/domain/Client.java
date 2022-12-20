
package com.bootcamp.java.client.domain;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@ToString
@EqualsAndHashCode(of = { "identityDocumentNumber" })
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "client")
public class Client {
	@Id
	private String id;
	@NotNull
	private String identityDocumentType;
	@NotNull
	private String identityDocumentNumber;
	private String name;
	private String lastName;
	private String businessName;
	@NotNull
	@Indexed(unique = true)
	private String email;
	@NotNull
	private String phoneNumber;
	@NotNull
	private LocalDate birthday;
	private String clientType;
	private String clientProfile;
}
