package com.qa.gemini.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;


@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class S3 {
    @JsonProperty("fileName")
    public String fileName;
    @JsonProperty("folderName")
    public String folderName;
    @JsonProperty("usernames")
    public List<String> usernames;
}
