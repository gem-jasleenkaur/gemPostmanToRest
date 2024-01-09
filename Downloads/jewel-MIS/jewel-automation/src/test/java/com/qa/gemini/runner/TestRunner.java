package com.qa.gemini.runner;

import io.cucumber.junit.CucumberSerenityRunner;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(CucumberSerenityRunner.class)
@CucumberOptions(
        features = "src/test/resources/features"
        ,glue = "com.qa.gemini.stepdefinitions"
)
public class TestRunner {
}
