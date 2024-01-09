package com.qa.gemini.payloads;

import com.qa.gemini.pojo.Authentication;
import com.qa.gemini.pojo.Users;

import java.util.List;

public class UserControllerPayloads {

    public static Authentication createUserPayload(String username, String fName, String lName,
                                                   String email, String password, String company) {
        return Authentication.builder().username(username)
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .company(company)
                .password(password)
                .build();
    }

    public static Authentication createUserPayloadWithoutValues() {
        return Authentication.builder().build();
    }

    public static Authentication createUserPayloadWithoutUsername(String fName, String lName,
                                                                  String email, String password, String company) {
        return Authentication.builder().firstName(fName)
                .lastName(lName)
                .email(email)
                .company(company)
                .password(password)
                .build();
    }

    public static Authentication createUserPayloadWithoutFName(String username, String lName,
                                                               String email, String password, String company) {
        return Authentication.builder().username(username)
                .lastName(lName)
                .email(email)
                .company(company)
                .password(password)
                .build();
    }

    public static Authentication createUserPayloadWithoutLName(String username, String fName,
                                                               String email, String password, String company) {
        return Authentication.builder().username(username)
                .firstName(fName)
                .email(email)
                .company(company)
                .password(password)
                .build();
    }

    public static Authentication createUserPayloadWithoutEmail(String username, String fName,
                                                               String lName, String password,
                                                               String company) {
        return Authentication.builder().username(username)
                .firstName(fName)
                .lastName(lName)
                .company(company)
                .password(password)
                .build();
    }

    public static Authentication createUserPayloadWithoutPassword(String username, String fName,
                                                                  String lName, String email, String company) {
        return Authentication.builder().username(username)
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .company(company)
                .build();
    }

    public static Authentication createUserPayloadWithoutCompany(String username, String fName, String lName,
                                                                 String email, String password) {
        return Authentication.builder().username(username)
                .firstName(fName)
                .lastName(lName)
                .email(email)
                .password(password)
                .build();
    }

    public static Users deleteUserPayload(List<String> usernames) {
        return Users.builder().username(usernames).build();
    }

    public static Users deleteUserPayloadWithoutValue() {
        return Users.builder().build();
    }

    public static Users addAdminPayload(String companyName, List<String> usernames) {
        return Users.builder().realCompanyName(companyName)
                .username(usernames).build();
    }

    public static Users removeAdminPayload(String companyName, List<String> usernames) {
        return Users.builder().realCompanyName(companyName)
                .username(usernames).build();
    }

    public static Users addAdminPayloadWithoutCompanyName(List<String> usernames) {
        return Users.builder().username(usernames).build();
    }

    public static Users addAdminPayloadWithoutUsername(String companyName) {
        return Users.builder().realCompanyName(companyName).build();
    }

    public static Users removeAdminPayloadWithoutCompanyName(List<String> usernames) {
        return Users.builder().username(usernames).build();
    }

    public static Users removeAdminPayloadWithoutUsername(String companyName) {
        return Users.builder().realCompanyName(companyName).build();
    }

    public static Users addAdminPayloadWithoutAll() {
        return Users.builder().build();
    }

    public static Users removeAdminPayloadWithoutAll() {
        return Users.builder().build();
    }
}
