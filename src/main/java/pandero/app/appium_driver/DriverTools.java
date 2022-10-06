package pandero.app.appium_driver;

import pandero.app.utils.LoggerSingleton;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * La clase WebDriverTools contiene métodos que interactúan con selenium.
 *
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class DriverTools {
    private final AppiumDriver driver;
    private static final By CHK_BOX = By.cssSelector("input[type='checkbox']");
    private final Logger log = LoggerSingleton.getInstance().getLogger(getClass().getName());

    /**
     * Constructor.
     */
    public DriverTools(DriverManager driverManager) {
        this.driver = driverManager.getDriver();
    }

    /**
     * Refresca la pagina actual.
     */
    public void refreshPage() {
        driver.navigate().refresh();
    }

    /**
     * Permite realizar una pausa.
     *
     * @param millSeconds - tiempo de pausa en milliseconds.
     */
    public void sleepMilliSeconds(int millSeconds) {
        try {
            Thread.sleep(millSeconds);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Permite realizar una pausa.
     *
     * @param seconds - tiempo de pausa en segundos.
     */
    public void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Espera hasta que el WebElement sea visible y luego elimina su contenido.
     *
     * @param webElement WebElement a esperar y limpiar
     */
    public void clearTextField(WebElement webElement) {
        webElement.clear();
    }

    /**
     * Espera hasta que el WebElement sea visible y luego elimina su contenido.
     *
     * @param webElement WebElement a esperar y limpiar
     */
    public void clearTextFieldTab(WebElement webElement) {
        int size = getElementValue(webElement).split("").length;
        while (size > 0) {
            webElement.sendKeys(Keys.BACK_SPACE);
            size--;
        }
    }

    /**
     * Envia el contenido de text al WebElement.
     *
     * @param webElement - Tipo de entrada WebElement.
     * @param text       - Contenido para el WebElement.
     */
    public void setInputField(WebElement webElement, String text) {
        clearTextField(webElement);
        webElement.sendKeys(text);
    }

    /**
     * Envia el contenido de text al By selector.
     *
     * @param selector - Tipo de entrada By selector.
     * @param text     - Contenido para el By selector.
     */
    public void setInputField(By selector, String text) {
        WebElement webElement = driver.findElement(selector);
        clearTextField(webElement);
        webElement.sendKeys(text);
    }

    /**
     * Envia el contenido de text al By selector.
     *
     * @param selector - Tipo de entrada By selector.
     * @param text     - Contenido para el By selector.
     */
    public void setInputFieldAndEnter(By selector, String text) {
        WebElement webElement = driver.findElement(selector);
        clearTextField(webElement);
        webElement.sendKeys(text);
        webElement.sendKeys(Keys.ENTER);
    }

    /**
     * Ingrese texto y presiona Enter.
     *
     * @param element - elemento del campo al que se introduce el texto.
     * @param text    - testo a introducir.
     */
    public void setInputFieldAndPressEnter(WebElement element, String text) {
        setInputField(element, text);
        element.sendKeys(Keys.ENTER);
    }

    /**
     * Realiza el click en el webElement
     *
     * @param webElement WebElement al que se hara el click.
     */
    public void clickElement(WebElement webElement) {
        int contador = 0;
        while (contador < 5) {
            try {
                webElement.click();
                contador = 5;
            } catch (ElementClickInterceptedException | StaleElementReferenceException | TimeoutException e) {
                log.error(e.getMessage());
                contador++;
            }
        }
            }

    /**
     * Realiza el click en el webElement
     *
     * @param element WebElement al que se hara el click.
     */
    public void clickElementLast(By element) {
        List<WebElement> elements = driver.findElements(element);
        int size = elements.size();
        WebElement webElement = elements.get(size - 1);
        int contador = 0;
        while (contador < 3) {
            try {
                webElement.click();
                contador = 3;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                log.error(e.getMessage());
                contador++;
            }
        }
    }

    /**
     * Realiza el click en el By element
     *
     * @param by By al que se hara el click.
     */
    public void clickElement(By by) {
        int contador = 0;
        while (contador < 5) {
            try {
                WebElement webElement = driver.findElement(by);
                webElement.click();
                break;
            } catch (ElementClickInterceptedException | StaleElementReferenceException e) {
                log.warn(e.getMessage());
                contador++;
            }
        }
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param webElement - WebElement al que se le pide el contenido.
     * @return Retorna el texto del WebElement.
     */
    public String getElementText(WebElement webElement) {
        try {
            return webElement.getText();
        } catch (StaleElementReferenceException | TimeoutException e) {
            log.warn(e.getMessage());
            return "";
        }
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param by - By al que se le pide el contenido.
     * @return Retorna el texto del WebElement.
     */
    public List<String> getElementTexts(By by) {
        List<WebElement> listTexts = driver.findElements(by);
        List<String> result = new ArrayList<>();
        for (WebElement text : listTexts) {
            result.add(text.getText());
        }
        return result;
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param by - By al que se le pide el contenido.
     * @return Retorna el texto del WebElement.
     */
    public List<String> getElementValueTexts(By by) {
        List<WebElement> listTexts = driver.findElements(by);
        List<String> result = new ArrayList<>();
        for (WebElement text : listTexts) {
            result.add(getElementValue(text));
        }
        return result;
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param by - By al que se le pide el contenido.
     * @return Retorna el texto del WebElement.
     */
    public String getElementTextLast(By by) {
        List<WebElement> listTexts = driver.findElements(by);
        return listTexts.get(listTexts.size() - 1).getText();
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param webElement - WebElement al que se le pide el contenido.
     * @return Retorna el valor del WebElement.
     */
    public String getElementValue(WebElement webElement) {
        return webElement.getAttribute("value");
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param webElement - WebElement al que se le pide el contenido.
     * @return Retorna el valor del WebElement.
     */
    public String getElementAttr(WebElement webElement, String attr) {
        return webElement.getAttribute(attr);
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param by - by al que se le pide el contenido.
     * @return Retorna el valor del WebElement.
     */
    public String getElementAttr(By by, String attr) {
        return getElementAttr(driver.findElement(by), attr);
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param webElement - WebElement al que se le pide el contenido.
     * @return Retorna el valor del WebElement.
     */
    public String getElementValue(WebElement webElement, String attribute) {
        return webElement.getAttribute(attribute);
    }

    /**
     * Devuelve el contenido del WebElement.
     *
     * @param by - by al que se le pide el contenido.
     * @return Retorna el valor del WebElement.
     */
    public String getElementValue(By by) {
        WebElement webElement = driver.findElement(by);
        return getElementValue(webElement);
    }

    /**
     * Devuelve el contenido del By element.
     *
     * @param by - By element al que se le pide el contenido.
     * @return Retorna el texto del By element.
     */
    public String getElementText(By by) {
        try {
            WebElement e = driver.findElement(by);
            return e.getText();
        } catch (TimeoutException | StaleElementReferenceException e) {
            log.warn(e.getMessage());
            return "";
        }
    }

    /**
     * Este metodo selecciona un item de un campo autocomplete
     *
     * @param webElement webelement
     * @param text       texto base
     * @param textSelect opciona seleccionar
     */
    public void selectAutoComplete(WebElement webElement, String text, String textSelect) {
        int counter = 0;
        while (counter < 5) {
            try {
                clearTextField(webElement);
                webElement.sendKeys(text);
                sleepSeconds(2);
                List<WebElement> optionsToSelect = driver.findElements(By.cssSelector(".select-options>.option"));
                for (WebElement option : optionsToSelect) {
                    if (option.getText().equals(textSelect)) {
                        clickElement(option);
                        break;
                    }
                }
                if (getElementValue(webElement).equalsIgnoreCase(textSelect)) {
                    log.info("dato seleccionado {}", getElementValue(webElement));
                    break;
                } else counter++;
            } catch (NoSuchElementException | TimeoutException e) {
                log.warn(e.getMessage());
                counter++;
            }
        }


    }

    /**
     * Limpia los chkbox
     *
     * @param webElement webElement
     */
    public void selectChkBoxAttribute(WebElement webElement) {
        if (!isElementSelectedAttribute(webElement)) {
            try {
                clickElement(webElement);
            } catch (WebDriverException e) {
                moveAndClickElement(webElement);
            }
        }
    }


    /**
     * Limpia los chkbox
     *
     * @param webElement webElement
     */
    public void clearChkBoxAttribute(WebElement webElement) {
        if (isElementSelectedAttribute(webElement)) {
            try {
                clickElement(webElement);
            } catch (WebDriverException e) {
                moveAndClickElement(webElement);
            }
        }
    }

    /**
     * Limpia la seleccion del switch button
     *
     * @param webElement    webElement
     * @param elementStatus webElement
     */
    public void clearSwitchAttribute(WebElement webElement, WebElement elementStatus) {
        if (isElementSelectedAttribute(elementStatus)) {
            try {
                clickElement(webElement);
            } catch (WebDriverException e) {
                moveAndClickElement(webElement);
            }
        }
    }

    /**
     * Se mueve el elemento y se realiza un click.
     *
     * @param by by
     */
    public void moveAndClickElement(By by) {
        WebElement webElement = driver.findElement(by);
        moveAndClickElement(webElement);
    }

    /**
     * Se mueve hacia el elemento seleccionado.
     */
    public void moveToElement(By by) {
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(by));
    }


    /**
     * Se mueve hacia el elemento seleccionado.
     */
    public void moveToElementMouse(WebElement webElement) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(webElement).build().perform();
        } catch (MoveTargetOutOfBoundsException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * Se mueve hacia el elemento seleccionado.
     *
     * @param webElement webElement
     */
    public void moveToElement(WebElement webElement) {
        Actions action = new Actions(driver);
        action.moveToElement(webElement);
    }


    /**
     * Verifica si el elemento checkbox esta seleccionado.
     *
     * @param element WebElement.
     * @return true si esta seleccionado sino falso.
     */
    public boolean isElementSelectedAttribute(WebElement element) {
        String chkboxStatus = element.getAttribute("class");
        return chkboxStatus.equals("active");
    }

    /**
     * Verifica si el elemento checkbox esta seleccionado.
     *
     * @param by by.
     * @return true si esta seleccionado sino falso.
     */
    public boolean isElementSelectedAttribute(By by) {
        WebElement element = driver.findElement(by);
        String chkboxStatus = element.getAttribute("class");
        return chkboxStatus.equals("active");
    }


    /**
     * Espera hasta que elemento esperado se muestre.
     *
     * @param element - elemento a esperar.
     */
    public void waitUntilElementDisplayed(WebElement element) {
        int contador = 0;
        while (contador < 6) {
            try {
                contador = 6;
            } catch (TimeoutException e) {
                log.warn(e.getMessage());
                contador++;
            }
        }
    }

    /**
     * Espera hasta que elemento esperado se muestre.
     *
     * @param element - elemento a esperar.
     * @param tries   - numero de intentos a esperar, un intento equivale a 1 minuto
     */
    public void waitUntilElementDisplayed(WebElement element, int tries) {
        int contador = 0;
        while (contador < tries * 20) {
            try {
                contador = tries * 20;
            } catch (TimeoutException e) {
                log.warn(e.getMessage());
                contador++;
            }
        }
            }

    /**
     * Espera hasta que elemento esperado se muestre.
     *
     * @param by - elemento a esperar.
     */
    public void waitUntilElementDisplayed(By by) {
        int contador = 0;
        while (contador < 6) {
            try {
                contador = 6;
            } catch (TimeoutException e) {
                log.warn(e.getMessage());
                contador++;
            }
        }
            }

    /**
     * Espera hasta que elemento esperado se desaparece.
     *
     * @param element - elemento a esperar.
     */
    public void waitUntilElementDisappear(WebElement element) {
        int contador = 0;
        while (contador < 120) {
            try {
                sleepSeconds(1);
                contador++;
            } catch (TimeoutException | StaleElementReferenceException e) {
                log.warn(e.getMessage());
                break;
            }
        }
            }

    /**
     * Selecciona un item de la lista de box.
     *
     * @param element - ListBox WebElement.
     * @param value   - Valor a seleccionar del ListBox.
     */
    public void selectListBoxByValue(WebElement element, String value) {
        int count = 0;
        while (count < 5) {
            try {
                Select select = new Select(element);
                select.selectByValue(value);
                count = 5;
            } catch (StaleElementReferenceException | NoSuchElementException e) {
                log.warn(e.getMessage());
                clickElement(element);
                count++;
            }
        }
            }


    // #################################
    //    Metodos java script
    // #################################

    /**
     * Envia el contenido de text al WebElement.
     *
     * @param webElement - Tipo de entrada WebElement.
     * @param text       - Contenido para el WebElement.
     */
    public void setInputFieldDocument(WebElement webElement, String text) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].value='';", webElement);
        webElement.sendKeys(text);
    }

    /**
     * Se mueve el elemento y se realiza un click.
     *
     * @param webElement webElement
     */
    public void moveAndClickElement(WebElement webElement) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", webElement);
    }

    /**
     * Limpia el campo de texto e ingresa un valor
     *
     * @param element elemento
     * @param text    informacion a ingresar
     */
    public void setInputFieldJavaScript(WebElement element, String text) {
        WebElement webElement = driver.findElement(By.cssSelector("ginni-vista-externa iframe"));
        driver.switchTo().frame(webElement);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", element);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, text);
    }

    /**
     * Limpia el campo de texto e ingresa un valor y realiza un enter
     *
     * @param element elemento
     * @param text    informacion a ingresar
     */
    public void setInputFieldAndEnterJavaScript(WebElement element, String text) {
        setInputFieldJavaScript(element, text);
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", element, Keys.ENTER);
    }

    /**
     * Desliza la pagina hacia el elemento element
     *
     * @param element locator
     */
    public void scrollDownElement(By element) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].scrollIntoView();", driver.findElement(element));
    }

    /**
     * Desliza la pagina hacia el elemento element
     */
    public void scrollUp() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("scroll(0, 250);");
    }

}
