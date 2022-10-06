package pandero.app.ui;

import pandero.app.appium_driver.DriverManager;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private By userNameTxt = By.xpath("//android.view.View[2]//*/android.view.View[2]//*/android.view.View[1]/android.widget.EditText");

    private By passwordTxt = By.xpath("//android.view.View[2]//*/android.view.View[2]//*/android.view.View[2]/android.widget.EditText");

    private By loginBtn =By.className("android.widget.Button");

    private By errorMessage=By.xpath("//android.view.View[3]//*/android.view.View[2]");

    /**
     * Inicializa el web driver, webDriverTools, webDriverManager
     *
     * @param driverManager web DriverManager
     */
    public LoginPage(DriverManager driverManager) {
        super(driverManager);
    }

    /**
     * Espera hasta que un elemento esperado aparesca
     */
    @Override
    public void waitUntilPageObjectIsLoaded() {
        log.info("Se ingreso a la pagina de : {}", getClass().getName());
    }

    public void setUserName(String userName) {
        driverTools.setInputField(userNameTxt, userName);
    }

    public void setPassword(String password) {
        driverTools.setInputField(passwordTxt, password);
    }

    public void clickLogin() {
        driverTools.clickElement(loginBtn);
    }

    public String getErrorMessage() {
        return driverTools.getElementText(errorMessage).trim();
    }
}
