package pandero.app.ui;

import pandero.app.appium_driver.DriverManager;
import pandero.app.appium_driver.DriverTools;
import pandero.app.utils.LoggerSingleton;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;

public abstract class BasePage {

    protected final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    protected AppiumDriver driver;
    protected DriverTools driverTools;
    protected DriverManager driverManager;
    protected String xpathLocator = "//*[contains(text(), '%s')]";
    protected String xpathBtn = "//button[contains(text(), '%s')]";
    protected static final int TIME_WAIT = 5;
    protected static final int TIME_MEDIUM = 10;
    protected static final int TIME_SEMI_LONG = 20;
    protected static final int TIME_LONG = 30;

    protected static final String REG = "[A-Za-z,$\\s]";

    /**
     * Inicializa el web driver, webDriverTools, webDriverManager
     *
     * @param driverManager web DriverManager
     */
    protected BasePage(DriverManager driverManager) {
        this.driverManager = driverManager;
        this.driver = driverManager.getDriver();
        this.driverTools = new DriverTools(driverManager);
        PageFactory.initElements(driver, this);
        waitUntilPageObjectIsLoaded();
    }

    /**
     * Espera hasta que un elemento esperado aparesca
     */
    public abstract void waitUntilPageObjectIsLoaded();
}
