package pandero.app.ui.config;

import pandero.app.appium_driver.DriverConfigReader;

/**
 * La clase SettingAutomation inicializa todas las clases de configuración.
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class SettingAutomation {

    private static SettingAutomation instance;

    /**
     * Constructor inicializa la configuracion necesaria.
     */
    private SettingAutomation() {
        DriverConfigReader.getInstance();
        DeviceConfigReader.getInstance();
    }

    /**
     * Retorna la instancia del AutomationGlobalEnvironment.
     *
     * @return La instancia del AutomationGlobalEnvironment.
     */
    public static SettingAutomation getInstance() {
        if (instance == null) {
            instance = new SettingAutomation();
        }
        return instance;
    }
}
