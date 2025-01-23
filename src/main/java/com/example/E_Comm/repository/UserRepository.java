//UserRepository.java
package com.example.E_Comm.repository;

import com.example.E_Comm.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserDetails, Integer> {

    public UserDetails findByEmail(String email);

    public List<UserDetails> findByRole(String role);

    public  UserDetails findByResetToken(String token);





}
