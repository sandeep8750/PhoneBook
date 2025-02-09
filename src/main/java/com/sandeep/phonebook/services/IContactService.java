package com.sandeep.phonebook.services;

import java.util.List;

import com.sandeep.phonebook.entities.ContactEntity;
import com.sandeep.phonebook.entities.UserEntity;
import org.springframework.data.domain.Page;



public interface IContactService {
    // save contacts
    ContactEntity save(ContactEntity contact);

    // update contact
    ContactEntity update(ContactEntity contact);

    // get contacts
    List<ContactEntity> getAll();

    // get contact by id

    ContactEntity getById(String id);

    // delete contact

    void delete(String id);

    // search contact
    Page<ContactEntity> searchByName(String nameKeyword, int size, int page, String sortBy, String order, UserEntity user);

    Page<ContactEntity> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, UserEntity user);

    Page<ContactEntity> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
                                      UserEntity user);

    // get contacts by userId
    List<ContactEntity> getByUserId(String userId);

    Page<ContactEntity> getByUser(UserEntity user, int page, int size, String sortField, String sortDirection);

}
