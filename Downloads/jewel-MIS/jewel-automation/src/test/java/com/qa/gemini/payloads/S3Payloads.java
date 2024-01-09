package com.qa.gemini.payloads;

import com.qa.gemini.pojo.S3;

import java.util.List;

public class S3Payloads {

    public static S3 deletePayload(String fileName, String folderName) {
        return S3.builder().fileName(fileName)
                .folderName(folderName).build();
    }

    public static S3 folderAccess(String folderName, List<String> usernames) {
        return S3.builder().folderName(folderName)
                .usernames(usernames).build();
    }

    public static S3 fileAccess(String fileName, String folderName, List<String> usernames) {
        return S3.builder().fileName(fileName)
                .folderName(folderName)
                .usernames(usernames).build();
    }

    public static S3 recycleBinDelete(String fileName, String folderName) {
        return S3.builder().fileName(fileName)
                .folderName(folderName).build();
    }
}
