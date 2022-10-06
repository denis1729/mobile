package pandero.app.appium_driver.drivers;

import io.appium.java_client.AppiumDriver;

/**
 * La clase DriverFactory tiene la capacidad de crear un tipo de navegador según el usuario lo requiera.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class DriverFactory {

    private static final String ANDROID = "android";
    private static final String IOS = "ios";
    private static final String WINDOWS = "windows";

    private DriverFactory() {
    }

    /**
     * Retorna una instancia de un navegador.
     *
     * @return instancia de web driver .
     */
    public static AppiumDriver getDriver(String device) {
        IDriver driver;
        switch (device) {
            case ANDROID:
                driver = new Android();
                break;
            case IOS:
                driver = new IOS();
                break;
            case WINDOWS:
                driver = new Windows();
                break;
            default:
                driver = new Android();
                break;
        }
        return driver.initDriver();
    }
}
