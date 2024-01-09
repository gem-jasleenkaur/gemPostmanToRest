package com.qa.gemini.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Introduction {

    @JsonProperty("gemName")
    public String gemName;

    @JsonProperty("data")
    @JsonDeserialize(as = Map.class)
    private Map<String, Object> data;
}
