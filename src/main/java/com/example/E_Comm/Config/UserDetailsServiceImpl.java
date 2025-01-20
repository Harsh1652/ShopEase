//UserDetailsServiceImpl.java
package com.example.E_Comm.Config;

import com.example.E_Comm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Attempting login for email: " + username);

        com.example.E_Comm.model.UserDetails user = userRepository.findByEmail(username);

        if (user == null) {
            System.out.println("User not found for email: " + username);
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        System.out.println("User found: " + user.getEmail());

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword()) // BCrypt hashed password
                .roles(user.getRole().replace("ROLE_", "")) // Ensure no prefix in DB
                .build();
    }


}
