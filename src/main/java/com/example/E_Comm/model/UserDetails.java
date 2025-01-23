//UserDetails.java
package com.example.E_Comm.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String mobileNumber;

    private String email;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String password;

    private String profileImage;

    private String role;

    @Getter
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Getter
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private Integer failedAttempt;

    private Date lockTime;

    private String resetToken;


}
