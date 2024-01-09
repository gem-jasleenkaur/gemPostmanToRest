package com.qa.gemini.payloads;

import com.qa.gemini.pojo.PreSigned;

import java.util.List;

public class PreSignedPayloads {

    public static PreSigned preSignedPayload(List<String> files, String folder, String s_run_id, String tag) {
        return PreSigned.builder().files(files)
                .folder(folder)
                .s_run_id(s_run_id)
                .tag(tag).build();
    }
}
