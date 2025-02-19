package com.sandeep.phonebook;

import com.sandeep.phonebook.services.IEmailService;
import com.sandeep.phonebook.services.ImageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PhoneBookApplicationTests {

	@Autowired
	private IEmailService emailService;

	@Test
	void contextLoads() {

	}


	@Test
	void  testEmail(){
		emailService.sendEmail("monu875036@gmail.com","test mail","test mail body content");
	}

}