package pandero.app.steps;

import pandero.app.ui.LoginPage;
import pandero.app.ui.PageTransporter;
import pandero.app.ui.config.UsersConfigReader;
import pandero.app.ui.entities.User;
import pandero.app.utils.LoggerSingleton;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;

public class LoginSteps {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());
    private LoginPage loginPage;
    private PageTransporter transporter;

    public LoginSteps() {
    }

    @Given("^Voy a abrir la aplicacion de ventas en un dispositivo \"(.*?)\"$")
    public void abreLaAplicacionDeVentas(String device) {
        transporter = new PageTransporter(device);
        loginPage = transporter.navigateToLoginPage();
    }

    @When("^Me logeo con usuario (.*?) y contrasenia (.*?)$")
    public void meLogeoConUsuarioUsuarioYContraseniaContrasenia(String usuario, String password) {
        loginPage.setUserName(usuario);
        loginPage.setPassword(password);
        loginPage.clickLogin();
    }

    @When("^Me logeo con un usuario como \"(.*?)\"$")
    public void meLogeoConUnUsuarioComo(String alias) {
        User usuario = UsersConfigReader.getInstance().getUserByAlias(alias);
        loginPage.setUserName(usuario.getUserName());
        loginPage.setPassword(usuario.getPassword());
        loginPage.clickLogin();
    }

    @Then("^No deberia logearse y debe mostrar el siguiente mensaje \"(.*?)\"$")
    public void noDeberiaLogearseYDebeMostrarElSiguienteMensajeMensaje(String mensaje) {
        String actual = loginPage.getErrorMessage();
        log.info("Mensaje en la pandero.app: {} \n Mensaje esperado: {}", actual, mensaje);
        Assert.assertEquals("Fallo la asercion porque los mensajes no son identicos", mensaje, actual);
    }

    //****************************************************************
    //Hooks para @Login scenarios
    //****************************************************************
    @After(order = 150)
    public void logoutSession(Scenario scenario) {
        log.info("Ejecutando After hook logout");
        if (scenario.isFailed())
            log.error("FALLO el scenario {}", scenario.getName());
        else
            log.info("scenario {} EXITOSO", scenario.getName());

        byte[] screenshot = ((TakesScreenshot) transporter.getDriverManager().getDriver()).
                getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "scenarioup");
        transporter.getDriverManager().quitDriver();
    }
}
