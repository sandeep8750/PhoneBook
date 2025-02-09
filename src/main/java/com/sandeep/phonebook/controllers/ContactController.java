package com.sandeep.phonebook.controllers;

import java.util.*;

import com.sandeep.phonebook.helper.ConstantUtils;
import com.sandeep.phonebook.requestDto.ContactSearchForm;
import com.sandeep.phonebook.services.ImageService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sandeep.phonebook.entities.ContactEntity;
import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.Color;
import com.sandeep.phonebook.helper.Helper;
import com.sandeep.phonebook.helper.Message;
import com.sandeep.phonebook.requestDto.ContactFormReqDTO;
import com.sandeep.phonebook.services.IContactService;
import com.sandeep.phonebook.services.IUserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private IContactService contactService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ImageService imageService;
    @RequestMapping("/add")
    // add contact page: handler
    public String addContactView(Model model) {
        ContactFormReqDTO contactForm = new ContactFormReqDTO();

        contactForm.setFavorite(true);
        model.addAttribute("ContactReqDto", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute("ContactReqDto") ContactFormReqDTO ContactReqDto, BindingResult result,
            Authentication authentication, HttpSession session) {

        // process the form data

        // 1 validate form

        if (result.hasErrors()) {

            result.getAllErrors().forEach(error -> logger.info(error.toString()));

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .color(Color.red)
                    .build());
            return "user/add_contact";
        }

        String username = Helper.getUserName(authentication);
        // form ---> contact

        UserEntity user = userService.getUserByEmail(username);

        ContactEntity contact = new ContactEntity();
        contact.setName(ContactReqDto.getName());
        contact.setFavorite(ContactReqDto.isFavorite());
        contact.setEmail(ContactReqDto.getEmail());
        contact.setPhoneNumber(ContactReqDto.getPhoneNumber());
        contact.setAddress(ContactReqDto.getAddress());
        contact.setDescription(ContactReqDto.getDescription());
        contact.setUser(user);
        contact.setLinkedInLink(ContactReqDto.getLinkedInLink());
        contact.setWebsiteLink(ContactReqDto.getWebsiteLink());

        // 2 process the contact picture
        // image process
        // uplod karne ka code

        if (ContactReqDto.getContactImage() != null && !ContactReqDto.getContactImage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(ContactReqDto.getContactImage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);

        }
        contactService.save(contact);
        System.out.println(ContactReqDto);

        // 3 set the contact picture url

        // 4 `set message to be displayed on the view

        session.setAttribute("message",
                Message.builder()
                        .content("You have successfully added a new contact")
                        .color(Color.green)
                        .build());

        return "redirect:/user/contacts/add";

    }

    // view contacts

    @RequestMapping
    public String viewContacts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = ConstantUtils.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction, Model model,
            Authentication authentication) {

        // load all the user contacts
        String username = Helper.getUserName(authentication);

        UserEntity user = userService.getUserByEmail(username);

        Page<ContactEntity> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", ConstantUtils.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    // search handler

    @RequestMapping("/search")
    public String searchHandler(

            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = ConstantUtils.PAGE_SIZE + "") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());

        var user = userService.getUserByEmail(Helper.getUserName(authentication));

        Page<ContactEntity> pageContact = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContact = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContact = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContact = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }

        logger.info("pageContact {}", pageContact);

        model.addAttribute("contactSearchForm", contactSearchForm);

        model.addAttribute("pageContact", pageContact);

        model.addAttribute("pageSize", ConstantUtils.PAGE_SIZE);

        return "user/search";
    }

    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .color(Color.green)
                        .build()

        );

        return "redirect:/user/contacts";
    }

    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {

        var contact = contactService.getById(contactId);
        ContactFormReqDTO contactForm = new ContactFormReqDTO();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedInLink(contact.getLinkedInLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactFormReqDTO contactForm,
            BindingResult bindingResult,
            Model model) {

        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }

        var con = contactService.getById(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedInLink(contactForm.getLinkedInLink());

        // process image:

//        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
//            logger.info("file is not empty");
//            String fileName = UUID.randomUUID().toString();
//            String imageUrl = imageService.uploadImage(contactForm.getContactImage(), fileName);
//            con.setCloudinaryImagePublicId(fileName);
//            con.setPicture(imageUrl);
//            contactForm.setPicture(imageUrl);
//
//        } else {
//            logger.info("file is empty");
//        }

        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);

        model.addAttribute("message", Message.builder().content("Contact Updated !!").color(Color.green).build());

        return "redirect:/user/contacts/view/" + contactId;
    }

}
