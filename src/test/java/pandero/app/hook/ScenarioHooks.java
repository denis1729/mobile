package pandero.app.hook;

import io.cucumber.java.Before;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import pandero.app.appium_driver.DriverManager;
import pandero.app.ui.config.SettingAutomation;
import pandero.app.utils.LoggerSingleton;
import org.slf4j.Logger;

/**
 * La clase ScenarioHooks contiene métodos para la pre y post configuración de los features.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class ScenarioHooks {
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());

    public ScenarioHooks() {
    }

    // Este hooks se ejecutara en todos los escenarios.
    @Before(order = 101)
    public void initializeConfiguration() {
        log.info("Inicia la configuracion antes que todos los hooks...");
        SettingAutomation.getInstance();

    }

    //****************************************************************
    //Hooks para @Login scenarios
    //****************************************************************

}