//OrderRequest
package com.example.E_Comm.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderRequest {

    private String firstName;

    private String lastName;

    private String Email;

    private String mobileNo;

    private String address;

    private String city;

    private String state;

    private String pincode;

    private String paymentType;

}
