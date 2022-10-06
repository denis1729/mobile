package pandero.app.ui;

import pandero.app.appium_driver.DriverManager;
import pandero.app.utils.LoggerSingleton;
import org.slf4j.Logger;

public class PageTransporter {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private final DriverManager driverManager;

    /**
     * Constructor.
     */
    public PageTransporter(String driver) {
        log.info("Initialize the page transporter");
        this.driverManager = new DriverManager(driver);
    }

    /**
     * Se direcciona hacia la pagina de Login Page.
     *
     * @return una instancia de Login Page.
     */
    public LoginPage navigateToLoginPage() {
        log.info("Dirigiendonos a la pagina de login");
        return new LoginPage(this.driverManager);
    }

    public DriverManager getDriverManager() {
        return this.driverManager;
    }
}
