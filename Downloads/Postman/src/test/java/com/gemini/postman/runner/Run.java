package com.gemini.postman.runner;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gemini.postman.utils.CommonFunctions;
import com.gemini.postman.utils.JsonParserForEnv;
import com.gemini.postman.utils.JsonParserForPostman;
import com.gemini.postman.utils.ReplaceValues;

import java.io.File;
import java.io.IOException;

public class Run {
    public static void main(String[] args)
    {
     //   ReplaceValues.replace();
        JsonParserForPostman.readJson();
        JsonParserForEnv.readJson();
    }
}