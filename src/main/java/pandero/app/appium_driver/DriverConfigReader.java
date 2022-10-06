package pandero.app.appium_driver;

import pandero.app.utils.JsonReader;
import pandero.app.utils.LoggerSingleton;
import pandero.app.utils.PathReader;
import org.slf4j.Logger;

/**
 * La clase DriverConfigReader configura el web driver a partir de un documento json.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class DriverConfigReader {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());

    private static final String DRIVER = "typeDevice";
    private static final String DRIVER_WAIT = "driver wait";
    private static final String IMPLICIT_WAIT_TIME = "implicit wait time";
    private static final String EXPLICIT_WAIT_TIME = "explicit wait time";
    private static final String WAIT_SLEEP_TIME = "wait sleep time";
    private String driverName;
    private int implicitWaitTime;
    private int explicitWaitTime;
    private int waitSleepTime;

    private static DriverConfigReader instance;

    private DriverConfigReader() {
        initialize();
    }

    /**
     * Constructor de WebDriverConfigReader.
     * Retorna WebDriverConfigReader como Singleton.
     *
     * @return una instancia.
     */
    public static DriverConfigReader getInstance() {
        if (instance == null) {
            instance = new DriverConfigReader();
        }
        return instance;
    }

    /**
     * Inicializa la configuracion de acuerdo al archivo de configuracion.
     */
    private void initialize() {
        String driverConfigFilename = PathReader.getPathConfig() + "DriverConfig.json";
        driverName = System.getProperty(DRIVER);
        log.info("Se selecciono el driver : {}", driverName);
        JsonReader configReader = new JsonReader(driverConfigFilename, false);
        implicitWaitTime = Integer.parseInt(configReader.getKeyValue(DRIVER_WAIT, IMPLICIT_WAIT_TIME));
        explicitWaitTime = Integer.parseInt(configReader.getKeyValue(DRIVER_WAIT, EXPLICIT_WAIT_TIME));
        waitSleepTime = Integer.parseInt(configReader.getKeyValue(DRIVER_WAIT, WAIT_SLEEP_TIME));
    }

    /**
     * Devuelve el nombre del browser.
     *
     * @return Browser.
     */
    public String getDriverName() {
        return driverName;
    }

    /**
     * Devuelve el implicit wait time seteado para el WebDriver.
     *
     * @return El implicit time.
     */
    public int getImplicitWaitTime() {
        return implicitWaitTime;
    }

    /**
     * Gets theDevuelve el explicit wait time seteado para el WebDriver.
     *
     * @return El explicit time.
     */
    public int getExplicitWaitTime() {
        return explicitWaitTime;
    }

    /**
     * Devuelve el sleep time wait seteado para el WebDriver.
     *
     * @return Sleep time wait.
     */
    public int getWaitSleepTime() {
        return waitSleepTime;
    }

}
