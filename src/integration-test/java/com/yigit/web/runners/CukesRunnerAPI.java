package com.yigit.web.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;



@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"json:build/apiTests.json", "pretty", "html:build/cucumber-reports"},
        features = "src/integration-test/resources/features",
        glue = "com/yigit/web/stepDef",
        tags = "@smoke"
)
/**
 * This class runs and connect all feature and step definition methods for API
 */
public class CukesRunnerAPI {

}
