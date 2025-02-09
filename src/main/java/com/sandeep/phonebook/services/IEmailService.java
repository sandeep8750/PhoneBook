package com.sandeep.phonebook.services;

public interface IEmailService {

    //
    void sendEmail(String to, String subject, String body);

    //
    void sendEmailWithHtml();

    //
    void sendEmailWithAttachment();

}
