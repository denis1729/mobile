package pandero.app.utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;

import java.io.File;
import java.util.Objects;

/**
 * La clase MergeReports crea nuevos archivos json las cuales no contienen duplicados
 *
 * @author Denis Camacho Camacho
 * @since 05/16/2022
 */
public class MergeReports {
    private static final Logger log = LoggerSingleton.getInstance().getLogger(MergeReports.class.getName());

    /**
     * Reemplaza los TCs que se volvieron a ejecutar por si pasaron en otro intento
     *
     * @param pathCucumberFiles       directorio de los archivos json resultado de la ejecucion de los TCs
     * @param pathCucumberFilesReport directorio donde se creara los nuevos json filtrados
     * @param pathOutputReport        directorio donde se generara el reporte
     */
    public static void generateMergeReport(String pathCucumberFiles,
                                           String pathCucumberFilesReport,
                                           String pathOutputReport) {

        File outputCucumberFiles = new File(pathCucumberFiles);
        File outputCucumberFilesFiles = new File(pathCucumberFilesReport);
        if (outputCucumberFilesFiles.mkdir())
            log.info("Se creo el directorio: {}", outputCucumberFilesFiles.getAbsolutePath());
        if (outputCucumberFiles.exists()) {
            for (File file : Objects.requireNonNull(outputCucumberFiles.listFiles())) {
                if (!file.isDirectory()) {
                    createJsonFileReport(pathCucumberFiles + file.getName(),
                            pathCucumberFilesReport + file.getName()
                    );
                }
            }

        }
        GenerateReportCucumber.generateReportsCucumber(pathCucumberFilesReport, pathOutputReport);
    }

    /**
     * Crea archivos json para generar un reporte cucumber
     *
     * @param pathJsonFile     directorio donde se encuentra el json a analizar
     * @param pathJsonFileSave directorio donde se guardara el nuevo json
     */
    public static void createJsonFileReport(String pathJsonFile, String pathJsonFileSave) {
        JsonReader jsonReader = new JsonReader(pathJsonFile, true);
        JsonReader newJsonFile = new JsonReader();
        newJsonFile.setJsonObjectMainArray(new JSONArray());
        //Iteracion features
        for (int i = 0; i < jsonReader.getJsonObjectMainArray().size(); i++) {
            JsonReader feature = new JsonReader();
            feature.setJsonObjectMain((JSONObject) jsonReader.getJsonObjectMainArray().get(i));
            newJsonFile.addJsonArray(i, feature.getJsonObjectMain());
            int contScenarios = 0;
            //iteracion scenarios
            JSONArray scenarios = feature.getJSONArray("elements");
            JSONArray newScenarios = new JSONArray();
            boolean background = false;
            for (int k = 0; k < scenarios.size(); k++) {
                boolean isFoundScenario = false;
                JSONObject scenario = (JSONObject) scenarios.get(k);
                for (int j = k + 1; j < scenarios.size(); j++) {
                    JSONObject scenario1 = (JSONObject) scenarios.get(j);
                    if (scenario.get("name").toString().equals(scenario1.get("name").toString())) {
                        if (scenario.get("keyword").toString().equals("Background")) {
                            background = true;
                            isFoundScenario = true;
                            break;
                        } else {
                            if (compareSteps((JSONArray) scenario.get("steps"), (JSONArray) scenario1.get("steps"))) {
                                boolean steps = verifySteps((JSONArray) scenario.get("steps"));
                                boolean steps1 = verifySteps((JSONArray) scenario1.get("steps"));

                                if (steps && steps1) {
                                    if (background) {
                                        newScenarios.add(contScenarios, scenarios.get(k - 1));
                                        contScenarios++;
                                        newScenarios.add(contScenarios, scenarios.get(k));
                                        contScenarios++;
                                        newScenarios.add(contScenarios, scenarios.get(j - 1));
                                        contScenarios++;
                                        newScenarios.add(contScenarios, scenarios.get(j));
                                        contScenarios++;
                                        k = k + 4;

                                    } else {
                                        newScenarios.add(contScenarios, scenarios.get(k));
                                        contScenarios++;
                                        newScenarios.add(contScenarios, scenarios.get(j));
                                        contScenarios++;
                                        k = k + 2;
                                    }
                                    isFoundScenario = true;
                                    background = false;
                                    break;
                                }

                                if (steps) {
                                    if (background) {
                                        newScenarios.add(contScenarios, scenarios.get(k - 1));
                                        contScenarios++;
                                        newScenarios.add(contScenarios, scenarios.get(k));
                                        contScenarios++;
                                        k = k + 2;

                                    } else {
                                        newScenarios.add(contScenarios, scenarios.get(k));
                                        contScenarios++;
                                    }
                                    isFoundScenario = true;
                                    background = false;
                                    break;
                                }

                                if (background) {
                                    newScenarios.add(contScenarios, scenarios.get(j - 1));
                                    contScenarios++;
                                    newScenarios.add(contScenarios, scenarios.get(j));
                                    contScenarios++;
                                    k = k + 2;

                                } else {
                                    newScenarios.add(contScenarios, scenarios.get(j));
                                    contScenarios++;
                                }
                                isFoundScenario = true;
                                background = false;
                                break;
                            }
                        }
                    }
                }
                if (!isFoundScenario) {
                    if (background) {
                        newScenarios.add(contScenarios, scenarios.get(k - 1));
                        contScenarios++;
                        background = false;
                    }
                    newScenarios.add(contScenarios, scenarios.get(k));
                    contScenarios++;
                }
            }
            newJsonFile.setObjectIntoJsonArray(i, "elements", newScenarios);
        }
        jsonReader.setJsonObjectMainArray(newJsonFile.getJsonObjectMainArray());
        jsonReader.createJsonFile(pathJsonFileSave, true);
    }

    /**
     * Verifica el status de los steps
     *
     * @param steps steps
     * @return false si fallo sino true
     */
    private static boolean verifySteps(JSONArray steps) {
        for (Object object : steps) {
            JSONObject step = (JSONObject) object;
            if (!((JSONObject) step.get("result")).get("status").toString().equalsIgnoreCase("passed"))
                return false;
        }
        return true;
    }

    /**
     * Compara los steps
     *
     * @param steps1 steps del primer scenario
     * @param steps2 steps del segundo scenario
     * @return true si son iguales sino false
     */
    private static boolean compareSteps(JSONArray steps1, JSONArray steps2) {
        for (int i = 0; i < steps1.size(); i++) {
            JSONObject step1 = (JSONObject) steps1.get(i);
            JSONObject step2 = (JSONObject) steps2.get(i);
            if (!step1.get("name").toString().equals(step2.get("name").toString()))
                return false;
        }
        return true;
    }
}
