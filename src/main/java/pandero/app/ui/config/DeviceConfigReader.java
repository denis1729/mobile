package pandero.app.ui.config;

import pandero.app.appium_driver.entities.TypeDevice;
import pandero.app.utils.JsonReader;
import pandero.app.utils.LoggerSingleton;
import pandero.app.utils.PathReader;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase ServerRemoteConfigReader crea clases de tipo ServerRemote a partir de un archivo de configuración json.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class DeviceConfigReader {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private static final String DEVICE_NAME = "deviceName";
    private static final String PLATFORM_NAME = "platformName";
    private static final String APP_PACKAGE = "appPackage";
    private static final String PLATFORM_VERSION = "platformVersion";
    private static final String APP_ACTIVITY = "appActivity";
    private static final String NO_RESET = "noReset";

    private HashMap<String, TypeDevice> devices;

    private static DeviceConfigReader instance;

    private DeviceConfigReader() {
        initialize();
    }

    /**
     * Retorna la instancia de ServerRemoteConfigReader.
     *
     * @return la instancia de ServerRemoteConfigReader.
     */
    public static DeviceConfigReader getInstance() {
        if (instance == null) {
            instance = new DeviceConfigReader();
        }
        return instance;
    }

    /**
     * Lee los valores del archivo de configuracion json.
     */
    private void initialize() {
        String deviceFileName = PathReader.getPathConfig() + "Devices.json";
        log.info("Lee archivo de configuracion de {}", deviceFileName);

        Map<String, Map<String, String>> dataDevices = new JsonReader(deviceFileName, false).getJsonObjectMain();

        devices = new HashMap<>();
        dataDevices.forEach((alias, data) -> {
            TypeDevice typeDevice = new TypeDevice();
            typeDevice.setDeviceName(data.get(DEVICE_NAME));
            typeDevice.setPlatformName(data.get(PLATFORM_NAME));
            typeDevice.setPlatformVersion(data.get(PLATFORM_VERSION));
            typeDevice.setAppPackage(data.get(APP_PACKAGE));
            typeDevice.setAppActivity(data.get(APP_ACTIVITY));
            typeDevice.setNoReset(data.get(NO_RESET));
            devices.put(alias, typeDevice);
        });
    }

    /**
     * Devuelve un servidor remoto por alias.
     *
     * @param deviceAlias - El alias del device.
     * @return un servidor.
     */
    public TypeDevice getDeviceByAlias(String deviceAlias) {
        return devices.get(deviceAlias);
    }
}
