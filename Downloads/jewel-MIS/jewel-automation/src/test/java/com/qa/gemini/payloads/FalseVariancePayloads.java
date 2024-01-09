package com.qa.gemini.payloads;

import com.qa.gemini.pojo.StatusClassification.FalseVariance;
import com.qa.gemini.pojo.StatusClassification.VarianceFields;

public class FalseVariancePayloads {

    public static FalseVariance createVariancePayload(String reason, Long startTime, String name, String match,
                                                      String tc_run_id, Long endDate, String category) {
        return FalseVariance.builder().reason(reason)
                .startTime(startTime)
                .name(name)
                .match(match)
                .tc_run_id(tc_run_id)
                .endDate(endDate)
                .category(category).build();
    }

    public static VarianceFields createVarianceFieldsPayload(String reason, Long startTime, String name, String match,
                                                             String tc_run_id, Long endDate, String category) {
         return VarianceFields.builder().reason(reason)
                .startTime(startTime)
                .name(name)
                .match(match)
                .tc_run_id(tc_run_id)
                .endDate(endDate)
                .category(category).build();
    }
}
