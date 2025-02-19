package com.sandeep.phonebook.serviceImpl;

import com.sandeep.phonebook.entities.UserEntity;
import com.sandeep.phonebook.helper.ConstantUtils;
import com.sandeep.phonebook.helper.Helper;
import com.sandeep.phonebook.helper.ResourceNotFoundException;
import com.sandeep.phonebook.repositories.IUserRepository;
import com.sandeep.phonebook.services.IEmailService;
import com.sandeep.phonebook.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private IEmailService emailService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private Logger logger = LoggerFactory.getLogger(this.getClass());



	@Override
	public UserEntity saveUser(UserEntity user) {
		// user id : have to generate
		String userId = UUID.randomUUID().toString();
		user.setUserId(userId);

		// password encode
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		// set user role
		 user.setRoleList(List.of(ConstantUtils.ROLE_USER));

		logger.info("sandeep provider details ===>{}",user.getProvider().toString());

		String emailToken = UUID.randomUUID().toString();
		user.setEmailToken(emailToken);
		UserEntity savedUser = userRepository.save(user);
		String emailLink = Helper.getLinkForEmailVerificatiton(emailToken);
		emailService.sendEmail(savedUser.getEmail(), "Verify Account : Smart  Contact Manager", emailLink);
		return savedUser;
	}

	@Override
	public Optional<UserEntity> geUserById(String id) {
		return userRepository.findById(id);
	}

	@Override
	public Optional<UserEntity> updateUser(UserEntity user) {
		//
		// UserEntity user2 = userRepository.findById(user.getUserId()).orElseThrow(()
		// -> new ResourceNotFoundException("User not Found"));
		//
		// user2.setName(user.getName());
		// user2.setEmail(user.getEmail());
		// user2.setPassword(user.getPassword());
		// user2.setAbout(user.getAbout());
		// user2.setPhoneNumber(user.getPhoneNumber());
		// user2.setProfilePicLink(user.getProfilePicLink());
		// //user2.setEnabled(user.isEnabled());
		// user2.setEmailVerified(user.isEmailVerified());
		// user2.setPhoneVerified(user.isPhoneVerified());
		// user2.setProvider(user.getProvider());
		// user2.setProviderUserId(user.getProviderUserId());
		//
		// UserEntity sUser = userRepository.save(user2);

		return Optional.empty();
	}

	@Override
	public void deleteUser(String id) {
		UserEntity user2 = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not Found"));

		userRepository.delete(user2);
	}

	@Override
	public boolean isUserExist(String userId) {
		UserEntity user2 = userRepository.findById(userId).orElse(null);
		return user2 != null ? true : false;
	}

	@Override
	public boolean isUserExistByEmail(String email) {
		UserEntity user = (UserEntity) userRepository.findByEmail(email).orElse(null);
		return user != null ? true : false;
	}

	@Override
	public List<UserEntity> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public UserEntity getUserByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

}
