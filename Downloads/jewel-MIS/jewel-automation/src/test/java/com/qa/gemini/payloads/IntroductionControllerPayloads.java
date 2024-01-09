package com.qa.gemini.payloads;

import com.qa.gemini.pojo.Introduction;

import java.util.Map;

public class IntroductionControllerPayloads {

    public static Introduction createIntroductionPayload(String gemName, Map<String, Object> data) {
        return Introduction.builder().gemName(gemName)
                .data(data)
                .build();
    }

    public static Introduction createIntroductionPayloadWithoutGemName(String gemName, Map<String, Object> data) {
        if(gemName.equals("gemName"))
        {
            return Introduction.builder()
                    .data(data)
                    .build();

        }
        else{
            return Introduction.builder().gemName(gemName)
                    .build();
        }
    }




}
