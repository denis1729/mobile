package pandero.app.utils;

import org.apache.logging.log4j.util.PropertyFilePropertySource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * La clase LoggerSingleton es un utilitario que nos facilita el registro de eventos en las clases y métodos.
 * @author Denis Camacho Camacho
 * @since 10/20/2021
 */
public class LoggerSingleton {
    private static LoggerSingleton instance;

    private LoggerSingleton() {
        new PropertyFilePropertySource("resources/log4j2.properties");
    }

    public static LoggerSingleton getInstance() {
        if (instance == null)
            instance = new LoggerSingleton();
        return instance;
    }

    public Logger getLogger(String className) {
        return LoggerFactory.getLogger(className);
    }
}
