package pandero.app.utils;

import java.io.File;

/**
 * La clase PathReader es un utilitario que nos facilita la lectura PATHS.
 *
 * @author Denis Camacho Camacho
 * @since 12/27/2021
 */
public class PathReader {

    private PathReader() {
    }

    private static final String PATH_LOCAL = System.getProperty("user.dir");

    private static final String PATH_DOCS = PATH_LOCAL + File.separator + "src" +
            File.separator + "main" + File.separator + "java" + File.separator + "pandero" + File.separator +
            "web" + File.separator + "utils" + File.separator + "documentos" + File.separator;

    private static final String PATH_DOCS_GLV = PATH_LOCAL + File.separator + "src" +
            File.separator + "main" + File.separator + "java" + File.separator + "pandero" + File.separator +
            "web" + File.separator + "utils" + File.separator + "glv_docs" + File.separator;

    private static final String PATH_DOCS_API = PATH_LOCAL + File.separator + "src" +
            File.separator + "main" + File.separator + "java" + File.separator + "pandero" + File.separator +
            "web" + File.separator + "api" + File.separator + "archivos" + File.separator;

    private static final String PATH_CONFIG = PATH_LOCAL + File.separator + "config" +
            File.separator;

    private static final String PATH_CONFIG_ENV = PATH_LOCAL + File.separator +
            "config" + File.separator + "execution_variables" + File.separator;

    public static String getPathConfig() {
        return PATH_CONFIG;
    }

    public static String getPathDocs() {
        return PATH_DOCS;
    }

    public static String getPathDocsApi() {
        return PATH_DOCS_API;
    }

    public static String getPathConfigEnv() {
        return PATH_CONFIG_ENV;
    }

    public static String getPathDocsGlv() {
        return PATH_DOCS_GLV;
    }

    public static String getPathLocal(String dir) {
        return PATH_LOCAL + File.separator + dir + File.separator;
    }

}
