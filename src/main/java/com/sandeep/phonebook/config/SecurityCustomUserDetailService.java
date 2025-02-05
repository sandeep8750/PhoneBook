package com.sandeep.ContactManger.config;

import com.sandeep.ContactManger.Entities.UserEntity;
import com.sandeep.ContactManger.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityCustomUserDetailService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // apne user ko load karana hai
        UserEntity user = userRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + username));

        System.out.println(user.getName());
        System.out.println("====================");
        System.out.println(user.getEmail());
        System.out.println("====================");
        System.out.println(user.getPassword());
        return  user;
    }
}
