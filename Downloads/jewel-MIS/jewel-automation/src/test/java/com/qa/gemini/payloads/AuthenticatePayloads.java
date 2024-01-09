package com.qa.gemini.payloads;

import com.qa.gemini.pojo.Authentication;
import com.qa.gemini.utils.DataFileReader;

import static com.qa.gemini.utils.CommonMethods.decodeMD5;
import static com.qa.gemini.utils.CommonMethods.getJewelMapValue;


public class AuthenticatePayloads {

    public static Authentication authenticationPayload(String user) {
        if (user.equalsIgnoreCase("new_user")) {
            return Authentication.builder()
                    .username((String) getJewelMapValue("username"))
                    .password(decodeMD5((String) getJewelMapValue("password")))
                    .build();
        }
        return Authentication.builder()
                .username(DataFileReader.getInstance().getUsername(user))
                .password(DataFileReader.getInstance().getPassword(user))
                .build();
    }


    public static Authentication authenticationPayloadAdmin(String username, String password) {
        return Authentication.builder()
                .username(username)
                .password(password)
                .build();
    }
}
