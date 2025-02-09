package com.sandeep.phonebook.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "social_link_tbl")
public class SocialLinks {
	
	@Id
	private Long id;
	private String socialLInks;
	private String title;
	
	@ManyToOne
	private ContactEntity contact;
	
}
