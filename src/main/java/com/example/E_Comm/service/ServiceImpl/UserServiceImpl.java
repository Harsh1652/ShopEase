//UserServiceImpl.java
package com.example.E_Comm.service.ServiceImpl;

import com.example.E_Comm.Util.AppConstant;
import com.example.E_Comm.model.UserDetails;
import com.example.E_Comm.repository.UserRepository;
import com.example.E_Comm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

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
        findByEmail.setResetToken(resetToken);
        userRepository.save(findByEmail);
    }


    @Override
    public UserDetails getUserByToken(String token) {

        return userRepository.findByResetToken(token);
    }


    @Override
    public UserDetails updateUser(UserDetails user) {
        return userRepository.save(user);
    }

    @Override
    public UserDetails updateUserProfile(UserDetails user, MultipartFile image) throws IOException {

        UserDetails dbUser = userRepository.findById(user.getId()).orElse(null);

        if (dbUser != null) {
            dbUser.setName(user.getName());
            dbUser.setMobileNumber(user.getMobileNumber());
            dbUser.setAddress(user.getAddress());
            dbUser.setCity(user.getCity());
            dbUser.setState(user.getState());
            dbUser.setPincode(user.getPincode());

            // Check if the user uploaded a new image
            if (image != null && !image.isEmpty()) {
                String imageName = image.getOriginalFilename();
                dbUser.setProfileImage(imageName);

                // Ensure directory exists
                File saveDir = new ClassPathResource("static/img/Profile").getFile();
                if (!saveDir.exists()) {
                    saveDir.mkdirs();
                }

                // Save the image file
                Path path = Paths.get(saveDir.getAbsolutePath(), imageName);
                Files.copy(image.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

                System.out.println("Image uploaded: " + image.getOriginalFilename());
                System.out.println("Saved at: " + path.toString());
            }

            dbUser = userRepository.save(dbUser);
        }

        return dbUser;
    }

}