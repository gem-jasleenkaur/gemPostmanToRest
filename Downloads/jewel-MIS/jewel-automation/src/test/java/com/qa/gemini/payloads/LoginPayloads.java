package com.qa.gemini.payloads;

import com.qa.gemini.pojo.Authentication;

public class LoginPayloads {

    public static Authentication loginPayload(String username, String password) {
        return Authentication.builder().username(username)
                .password(password).build();
    }

    public static Authentication loginPayloadWithoutUsername(String password) {
        return Authentication.builder().password(password).build();
    }

    public static Authentication loginPayloadWithoutPassword(String username) {
        return Authentication.builder().username(username).build();
    }

    public static Authentication loginPayloadWithoutAll() {
        return Authentication.builder().build();
    }
}
