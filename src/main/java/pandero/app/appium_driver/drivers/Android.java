package pandero.app.appium_driver.drivers;

import pandero.app.appium_driver.entities.TypeDevice;
import pandero.app.ui.config.DeviceConfigReader;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * La clase Chrome es un navegador que extiende de la clase DriverAbstract.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
class Android extends DriverAbstract {
    /**
     * Inicializa el Web Driver.
     *
     * @return Un AppiumDriver.
     */
    @Override
    public AppiumDriver initDriver() {
        TypeDevice device = DeviceConfigReader.getInstance().getDeviceByAlias("android");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(DEVICE_NAME, device.getDeviceName());
        capabilities.setCapability(PLATFORM_NAME, device.getPlatformName());
        capabilities.setCapability(PLATFORM_VERSION, device.getPlatformVersion());
        capabilities.setCapability(APP_PACKAGE, device.getAppPackage());
        capabilities.setCapability(APP_ACTIVITY, device.getAppActivity());
        capabilities.setCapability(NO_RESET, device.getNoReset());


        try {
            driver = new AppiumDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
        } catch (MalformedURLException e) {
            return null;
        }

        return driver;
    }
}
