package com.qa.gemini.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FalseVariance {
    @JsonProperty("reason")
    public String reason;
    @JsonProperty("startTime")
    public long startTime;
    @JsonProperty("name")
    public String name;
    @JsonProperty("match")
    public String match;
    @JsonProperty("tc_run_id")
    public String tc_run_id;
    @JsonProperty("endDate")
    public long endDate;
    @JsonProperty("category")
    public String category;
}
