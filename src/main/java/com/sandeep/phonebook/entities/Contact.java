package com.sandeep.phonebook.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Contact {

    @Id
    private String id; // Changed from String to Long for MySQL compatibility

    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String picture;
    @Column(length = 1000)
    private String description;

    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;

    private String cloudinaryImagePublicId;

    @ManyToOne
    private UserEntity user;

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SocialLinks> socialLinks = new ArrayList<>();


}
