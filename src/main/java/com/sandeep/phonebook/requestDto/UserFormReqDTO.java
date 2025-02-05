package com.sandeep.phonebook.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserFormReqDTO {

	@NotBlank(message = "Username required min 3 Charactor")
	private String name ;
	@NotBlank(message = "Email address required")
	@Email(message = "Invalid email address")
	private String email;
	@Size(min = 6, message = "Min 6 Character requried")
	private String password;
	@NotBlank(message = "About is requried")
	private String about ;
	@Size(min = 8,max = 12, message = "Invalid Phone Number")
	private String phoneNumber;

}
