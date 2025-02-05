package com.sandeep.phonebook.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.sandeep.phonebook.entities.UserEntity;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
	

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
