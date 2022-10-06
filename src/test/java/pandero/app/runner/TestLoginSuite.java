package pandero.app.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.*;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"pandero.app"},
        plugin = {"json:reports/login.json", "pretty"},//snippets = CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true,
        tags = "@login")
public class TestLoginSuite extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }
}