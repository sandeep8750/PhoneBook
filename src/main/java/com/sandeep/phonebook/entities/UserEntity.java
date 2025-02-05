package com.sandeep.phonebook.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "user_tbl")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity  {

    @Id
    private String userId;
    @Column(name = "user_name", nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    @Column(length = 1000)
    private String about;
    @Column(length = 1000)
    private String profilePicLink;
    private String phoneNumber;

    //information
    @Getter(AccessLevel.NONE)
    private boolean enabled = true;
    private boolean emailVerified = false;
    private boolean phoneVerified = false;

    // SELF , GOOGLE , FACEBOOK , TWITTER , LINKDIN , GITHUB
    @Enumerated(value = EnumType.STRING)
    private Providers provider = Providers.SELF;
    private String providerUserId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Contact> contacts = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList = new ArrayList<>();

}
