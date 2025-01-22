//UserServiceImpl.java
package com.example.E_Comm.service;

import com.example.E_Comm.Util.AppConstant;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails saveUser(UserDetails user) {
        user.setRole("USER");
        user.setIsEnabled(true);
        user.setAccountNonLocked(true);
        user.setFailedAttempt(0);

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        UserDetails saveUser = userRepository.save(user);
        return saveUser;
    }

    @Override
    public UserDetails getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDetails> getUsers(String role) {
        return userRepository.findByRole(role);
    }

    @Override
    public Boolean updateAccountStatus(Integer id, boolean status) {
        Optional<UserDetails> findByuser = userRepository.findById(id);

        if (findByuser.isPresent()) {
            UserDetails userDtls = findByuser.get();
            userDtls.setIsEnabled(status);
            userRepository.save(userDtls);
            return true;
        }

        return false;
    }

    @Override
    public Boolean updateAccountStatus(Integer id, Boolean status) {
        return null;
    }


    @Override
    public void increaseFailedAttempt(UserDetails user) {
        int attempt = user.getFailedAttempt() + 1;
        user.setFailedAttempt(attempt);
        userRepository.save(user); // Save changes to the database
        System.out.println("Failed attempts incremented: " + attempt);
    }

    @Override
    public void userAccountLock(UserDetails user) {
        user.setAccountNonLocked(false);
        user.setLockTime(new Date());
        userRepository.save(user); // Save changes to the database
        System.out.println("User account locked: " + user.getEmail());
    }

    @Override
    public boolean unlockAccountTimeExpired(UserDetails user) {

        long lockTime = user.getLockTime().getTime();
        long unLockTime = lockTime + AppConstant.UNLOCK_DURATION_TIME;

        long currentTime = System.currentTimeMillis();

        if (unLockTime < currentTime) {
            user.setAccountNonLocked(true);
            user.setFailedAttempt(0);
            user.setLockTime(null);
            userRepository.save(user);
            return true;
        }

        return false;
    }

    @Override
    public void resetAttempt(int userId) {
        //TODO
    }


    @Override
    public void updateUserResetToken(String email, String resetToken) {
        UserDetails findByEmail = userRepository.findByEmail(email);
        findByEmail.setReset_token(resetToken);
        userRepository.save(findByEmail);
    }
}
