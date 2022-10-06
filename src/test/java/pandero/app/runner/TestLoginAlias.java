package pandero.app.runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;
import pandero.app.utils.MergeReports;
import pandero.app.utils.PathReader;

@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"pandero.app"},
        plugin = {"json:reports/loginAlias.json", "pretty"},//snippets = CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true,
        tags = "@loginAlias")
public class TestLoginAlias extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider()
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @AfterTest
    public void generateReport() {
        String pathCucumberFiles = PathReader.getPathLocal("reports");
        String pathCucumberFilesReport = PathReader.getPathLocal("reports_1");
        String pathOutput = PathReader.getPathLocal("cucumber-report");
        MergeReports.generateMergeReport(pathCucumberFiles, pathCucumberFilesReport, pathOutput);
    }
}