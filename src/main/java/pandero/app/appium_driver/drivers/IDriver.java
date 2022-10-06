package pandero.app.appium_driver.drivers;

import io.appium.java_client.AppiumDriver;

/**
 * La interfaz IDriver inicia el web driver de forma local y remota.
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
interface IDriver {

    /**
     * Inicializa el Web Driver.
     *
     * @return WebDriver.
     */
    AppiumDriver initDriver();

}
