package pandero.app.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import org.slf4j.Logger;
import pandero.app.ui.config.ServersConfigReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * La clase GenerateReportCucumber genera reporte para cucumber
 *
 * @author Denis Camacho Camacho
 * @since 05/16/2022
 */
public class GenerateReportCucumber {
    private static final Logger log = LoggerSingleton.getInstance().getLogger(GenerateReportCucumber.class.getName());

    /**
     * Genera un reporte cucumber de los TCS ejecutados
     *
     * @param pathCucumberFiles directorio donde se encuentran los archivos json
     * @param pathReportOutput  directorio donde se creara el reporte
     */
    public static void generateReportsCucumber(String pathCucumberFiles, String pathReportOutput) {
        log.info("################### GENERANDO REPORTE CUCUMBER #############################");
        File rd = new File(pathCucumberFiles);
        if (rd.exists()) {
            List<String> jsonFiles = new ArrayList<>();
            File outputReport = new File(pathReportOutput);
            for (File file : Objects.requireNonNull(rd.listFiles())) {
                if (!file.isDirectory())
                    jsonFiles.add(pathCucumberFiles + file.getName());
            }
            Configuration configuration = new Configuration(outputReport, "cucumber-report");
            ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
            reportBuilder.generateReports();
            log.info("REPORTE CUCUMBER SE CREO EN LA DIRECCION: \n" + outputReport.getAbsolutePath());
        }
    }
}
