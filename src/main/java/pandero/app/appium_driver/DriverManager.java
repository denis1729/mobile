package pandero.app.appium_driver;

import pandero.app.appium_driver.drivers.DriverFactory;
import pandero.app.utils.LoggerSingleton;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.slf4j.Logger;

import java.time.Duration;

/**
 * La clase WebDriverManager inicializa el web driver.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class DriverManager {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private AppiumDriver driver;
    private final Wait<AppiumDriver> driverWait;

    /**
     * Inicializa la configuracion para el driver.
     */
    public DriverManager(String device) {
        log.info("DriverManager : Iniciando el driver {}", device);
        this.driver = DriverFactory.getDriver(device);
        DriverConfigReader driverConfigReader = DriverConfigReader.getInstance();
        this.driver.manage().timeouts().
                implicitlyWait(Duration.ofSeconds(driverConfigReader.getImplicitWaitTime()));
        driverWait = new FluentWait<>(driver).
                withTimeout(Duration.ofSeconds(driverConfigReader.getExplicitWaitTime())).
                pollingEvery(Duration.ofMillis(driverConfigReader.getWaitSleepTime())).
                ignoring(NoSuchElementException.class);
    }

    /**
     * Retorna el WebDriver.
     *
     * @return WebDriver.
     */
    public AppiumDriver getDriver() {
        return driver;
    }

    /**
     * Retorna el WebDriver Wait.
     *
     * @return WebDriverWait.
     */
    public Wait<AppiumDriver> getWait() {
        return driverWait;
    }


    /**
     * Cierra la instancia del web driver.
     */
    public void quitDriver() {
        try {
            log.info("Cerrando el driver");
            driver.quit();
        } catch (WebDriverException e) {
            log.error("No se ha podido cerrar el WebDriver", e);
        }
        driver = null;
    }
}
