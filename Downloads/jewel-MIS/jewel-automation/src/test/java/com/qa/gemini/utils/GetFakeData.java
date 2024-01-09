package com.qa.gemini.utils;

import com.gemini.generic.reporting.GemTestReporter;
import com.gemini.generic.reporting.STATUS;
import com.gemini.generic.utils.ProjectConfigData;
import com.github.javafaker.Faker;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static com.qa.gemini.utils.CommonMethods.createFile;

public class GetFakeData {

    public static String generateUsername() {
        Faker faker = new Faker();
        return "auto_" + faker.lorem().word().toLowerCase() + "_" + faker.number().digits(3);
    }

    public static String generateEmail(String domain) {
        Faker faker = new Faker();
        return faker.name().firstName().toLowerCase() + faker.number().digits(3) + "@" + domain;
    }

    public static String generatePassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789~`!@#$%^&*()-_=+[{]}\\|;:\'\",<.>/?";
        return RandomStringUtils.random(8, characters);
    }

    public static String generateInvalidToken() {
        Faker faker = new Faker();
        return faker.regexify("[A-Za-z0-9]{203}");
    }

    public static String generateTempSRunId() {
        if (ProjectConfigData.getProperty("environment").equalsIgnoreCase("beta"))
            return "MICRO-TESTING_BETA_710d8eee-a31a-4eb3-b524-59d9aa5e2f78";
        else
            return "MICRO-TESTING_BETA_83d35459-c5ef-4599-bcec-20fa8ca01d6b";
    }

    public static String generateInvalidSRunId() {
        return "JEWEL_MS_API_BETA_c597683b-8hg6-49f8-abe7-56b730a3dc70";
    }

    public static String generateFolderName() {
        Faker faker = new Faker();
        return "folder_" + faker.lorem().word().toLowerCase() + "_" + faker.number().digits(2);
    }

    public static String generateFileName() {
        Faker faker = new Faker();
        return "file_" + faker.lorem().word().toLowerCase() + "_" + faker.number().digits(7);
    }

    public static String getFakeImage(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf("."));
        String imageTxt = fileName.split("_")[1];
        Faker faker = new Faker();
        try {
            String imageUrl = "https://dummyimage.com/180x100/" + faker.color().hex(false) + "/"
                    + faker.color().hex(false) + "." + ext + "&text" + "=" + imageTxt;
            URL url = new URL(imageUrl);
            InputStream in = url.openStream();
            Path des = Paths.get(fileName);
            Files.copy(in, des, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            GemTestReporter.addTestStep("Download Dummy Image", "Fail to download image.", STATUS.ERR);
        }
        return fileName;
    }

    public static String getExpiredPreSignedURL() {
        return "https://geco-cloud-serv.s3.ap-south-1.amazonaws.com/FOLDER_VOLUPTAS_71/file_voluptas_7704216s.png?" +
                "X-Amz-Algorithm=AWS4-HMAC-SHA256&" +
                "X-Amz-Date=20230901T063827Z&" +
                "X-Amz-SignedHeaders=host&" +
                "X-Amz-Expires=1800&" +
                "X-Amz-Credential=AKIA5ZJRWJYI3CURPZBM%2F20230901%2Fap-south-1%2Fs3%2Faws4_request&" +
                "X-Amz-Signature=33ed4f8b7bf3c52cd9a398ff04f95523bc599f83e9234a84a3bc616c5ab2b7cb";
    }

    public static String generateTempTCRunId() {
        if (ProjectConfigData.getProperty("environment").equalsIgnoreCase("beta"))
            return "Pass_and_Fail_scenario1_926fc190_8c3b_45dc_b00e_599b110f6a7d_1702278342000";
        else
            return "JEWEL_MS_API_PROD_5430fc17-1cd0-4792-a834-1639a96a893c";
    }

    public static Faker fakerInstance() {
        return new Faker();
    }

    public static void main(String[] args) {
        System.out.println(getFakeImage(createFile(generateFileName(), "png")));
    }


}


