package pandero.app.ui.config;

import pandero.app.utils.JsonReader;
import pandero.app.utils.LoggerSingleton;
import pandero.app.utils.PathReader;
import org.slf4j.Logger;

/**
 * La clase ServersConfigReader obtiene la url y nombre del navegador a partir de un archivo de configuración json.
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class ServersConfigReader {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());

    //web
    private static final String SERVER = "server";
    private static final String URL = "url";

    private String serverAlias;
    private String url;

    private static ServersConfigReader instance;

    private ServersConfigReader() {
        initialize();
    }

    /**
     * Retorna la instancia de ServersConfigReader.
     *
     * @return la instancia de ServersConfigReader.
     */
    public static ServersConfigReader getInstance() {
        if (instance == null) {
            instance = new ServersConfigReader();
        }
        return instance;
    }

    /**
     * Lee los valores del archivo de configuracion json.
     */
    private void initialize() {
        String serversConfigFileName = PathReader.getPathConfig() + "ServersConfig.json";
        log.info("Lee archivo de configuracion de {}", serversConfigFileName);

        JsonReader jsonReader = new JsonReader(serversConfigFileName,false);

        //dato optenido del system property
        serverAlias = System.getProperty(SERVER);
        log.info("Server Alias --> " + serverAlias);


        setUrl(jsonReader.getKeyValue(serverAlias, URL));
        log.info("Base URL --> " + url);

    }

    /**
     * Devuelve el server alias.
     *
     * @return Server Alias.
     */
    public String getServerAlias() {
        return serverAlias;
    }


    /**
     * Devuelve la URL base.
     *
     * @return URL.
     */
    public String getURL() {
        return this.url;
    }

    private void setUrl(String url1) {
        this.url = url1;
    }


}
