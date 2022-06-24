package com.yubraj.microservice.demo;

import com.yubraj.microservice.models.Credential;
import com.yubraj.microservice.models.EmailCredential;
import com.yubraj.microservice.models.PhoneOTP;

public class OneOfDemo {

    public static void main(String[] args) {

        EmailCredential emailCredential = EmailCredential.newBuilder()
                .setEmail("sample@gmail.com")
                .setPassword("samplepass")
                .build();

        PhoneOTP phoneOTP = PhoneOTP.newBuilder()
                .setPhone(123456789)
                .setCode(1234)
                .build();

        Credential credential = Credential.newBuilder()
                .setEmailMode(emailCredential)
                .setPhoneMode(phoneOTP)
                .build();

        getCredential(credential);
    }

    public static void getCredential(Credential credential){
        switch (credential.getModeCase()) {
            case EMAIL_MODE:
                System.out.println(credential.getEmailMode());
                break;
            case PHONE_MODE:
                System.out.println(credential.getPhoneMode());

        }
    }
}
