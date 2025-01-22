//UserDetailsServiceImp
package com.example.E_Comm.Config;

import com.example.E_Comm.Config.CustomUser;
import com.example.E_Comm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.E_Comm.model.UserDetails user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        if (!user.getIsEnabled()) {
            throw new DisabledException("Your account is disabled. Please contact support.");
        }

        if (!user.getAccountNonLocked()) {
            throw new LockedException("Your account is locked. Please wait or contact support.");
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))  // Ensure proper role format
                .build();
    }
}

