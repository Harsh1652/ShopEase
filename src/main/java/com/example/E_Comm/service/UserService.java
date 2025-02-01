//UserService.java
package com.example.E_Comm.service;

import com.example.E_Comm.model.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    public UserDetails saveUser(UserDetails user);

    public UserDetails saveAdmin(UserDetails user);


    public UserDetails getUserByEmail(String email);

    public List<UserDetails> getUsers(String role);

    Boolean updateAccountStatus(Integer id, boolean status);

    public Boolean updateAccountStatus(Integer id, Boolean status);

    public void increaseFailedAttempt(UserDetails user);

    public void userAccountLock(UserDetails user);

    public boolean unlockAccountTimeExpired(UserDetails user);

    public void resetAttempt(int userId);

    public void updateUserResetToken(String email, String resetToken);

    public UserDetails getUserByToken(String token);

    public UserDetails updateUser(UserDetails user);

    public UserDetails updateUserProfile(UserDetails user, MultipartFile img) throws IOException;

}
