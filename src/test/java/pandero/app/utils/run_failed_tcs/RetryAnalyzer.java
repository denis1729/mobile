package pandero.app.utils.run_failed_tcs;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * La clase RetryAnalyzer vuelve a ejecutar los test que fallaron.
 *
 * @author Denis Camacho Camacho
 * @since 05/02/2022
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int counter = 0;

    /**
     * Retorna true si el test tiene que ser ejecutado nuevamente sino false
     *
     * @param result el resultado del test que fue ejecutado
     * @return true si el test tiene que ser ejecutado nuevamente sino false
     */
    @Override
    public boolean retry(ITestResult result) {
        int retryLimit = 1;
        if (counter < retryLimit) {
            counter++;
            return true;
        }
        return false;
    }
}
