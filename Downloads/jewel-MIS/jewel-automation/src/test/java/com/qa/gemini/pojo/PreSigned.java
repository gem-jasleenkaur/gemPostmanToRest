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
public class PreSigned {
    @JsonProperty("files")
    public List<String> files;
    @JsonProperty("folder")
    public String folder;
    @JsonProperty("s_run_id")
    public String s_run_id;
    @JsonProperty("tag")
    public String tag;
}
// "tag": "Protected, Public, Private"
