package com.sandeep.phonebook.repositories;

import java.util.*;

import com.sandeep.phonebook.entities.ContactEntity;
import com.sandeep.phonebook.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface ContactRepo extends JpaRepository<ContactEntity, String> {
    // find the contact by user
    // custom finder method
    Page<ContactEntity> findByUser(UserEntity user, Pageable pageable);

    // custom query method
    @Query("SELECT c FROM ContactEntity c WHERE c.user.id = :userId")
    List<ContactEntity> findByUserId(@Param("userId") String userId);

    Page<ContactEntity> findByUserAndNameContaining(UserEntity user, String namekeyword, Pageable pageable);

    Page<ContactEntity> findByUserAndEmailContaining(UserEntity user, String emailkeyword, Pageable pageable);

    Page<ContactEntity> findByUserAndPhoneNumberContaining(UserEntity user, String phonekeyword, Pageable pageable);

}
