package pandero.app.appium_driver.drivers;

import io.appium.java_client.AppiumDriver;

/**
 * La clase DriverAbstract implementa los métodos de la interfaz IDriver.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public abstract class DriverAbstract implements IDriver {
    protected static final String DEVICE_NAME = "deviceName";
    protected static final String PLATFORM_NAME = "platformName";
    protected static final String APP_PACKAGE = "appPackage";
    protected static final String PLATFORM_VERSION = "platformVersion";
    protected static final String APP_ACTIVITY = "appActivity";
    protected static final String NO_RESET = "noReset";
    protected AppiumDriver driver;
}
