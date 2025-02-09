package com.sandeep.phonebook.services;

import com.sandeep.phonebook.entities.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserService {

	UserEntity saveUser(UserEntity user);

	Optional<UserEntity> geUserById(String id);

	Optional<UserEntity> updateUser(UserEntity user);

	void deleteUser(String id);

	boolean isUserExist(String userId);

	boolean isUserExistByEmail(String email);

	List<UserEntity> getAllUser();

	UserEntity getUserByEmail(String email);
}
