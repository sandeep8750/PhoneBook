package com.sandeep.phonebook.repositories;


import com.sandeep.phonebook.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, String> {
	

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
