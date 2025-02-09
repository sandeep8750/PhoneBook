package com.sandeep.phonebook.serviceImpl;

import java.util.*;

import com.sandeep.phonebook.entities.ContactEntity;
import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.ResourceNotFoundException;
import com.sandeep.phonebook.repositories.ContactRepo;
import com.sandeep.phonebook.services.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl implements IContactService

{

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public ContactEntity save(ContactEntity contact) {

        String contactId = UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);

    }

    @Override
    public ContactEntity update(ContactEntity contact) {

        var contactOld = contactRepo.findById(contact.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found"));
        contactOld.setName(contact.getName());
        contactOld.setEmail(contact.getEmail());
        contactOld.setPhoneNumber(contact.getPhoneNumber());
        contactOld.setAddress(contact.getAddress());
        contactOld.setDescription(contact.getDescription());
        contactOld.setPicture(contact.getPicture());
        contactOld.setFavorite(contact.isFavorite());
        contactOld.setWebsiteLink(contact.getWebsiteLink());
        contactOld.setLinkedInLink(contact.getLinkedInLink());
        contactOld.setCloudinaryImagePublicId(contact.getCloudinaryImagePublicId());

        return contactRepo.save(contactOld);
    }

    @Override
    public List<ContactEntity> getAll() {
        return contactRepo.findAll();
    }

    @Override
    public ContactEntity getById(String id) {
        return contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
    }

    @Override
    public void delete(String id) {
        var contact = contactRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact not found with given id " + id));
        contactRepo.delete(contact);

    }

    @Override
    public List<ContactEntity> getByUserId(String userId) {
        return contactRepo.findByUserId(userId);

    }

    @Override
    public Page<ContactEntity> getByUser(UserEntity user, int page, int size, String sortBy, String direction) {

        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();

        var pageable = PageRequest.of(page, size, sort);

        return contactRepo.findByUser(user, pageable);

    }

    @Override
    public Page<ContactEntity> searchByName(String nameKeyword, int size, int page, String sortBy, String order, UserEntity user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndNameContaining(user, nameKeyword, pageable);
    }

    @Override
    public Page<ContactEntity> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order,
            UserEntity user) {
        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user, emailKeyword, pageable);
    }

    @Override
    public Page<ContactEntity> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy,
            String order, UserEntity user) {

        Sort sort = order.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        var pageable = PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user, phoneNumberKeyword, pageable);
    }

}
