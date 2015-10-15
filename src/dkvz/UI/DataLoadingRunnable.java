
package dkvz.UI;

import dkvz.model.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 * This is a stupid class just to show a progress bar when I load transaction logs.
 * I could use an abstract version of this and some interface of abstract class for
 * "loadable" things but that'll be left for later.
 * @author Alain
 */
public class DataLoadingRunnable implements Runnable, CanLogMessages {

    private final TPTransactionLog transactionLog;
    private CanLogMessages logger = null;
    
    public DataLoadingRunnable(TPTransactionLog transactionLog) {
        this.transactionLog = transactionLog;
    }
    
    public DataLoadingRunnable(TPTransactionLog transactionLog, CanLogMessages logger) {
        this(transactionLog);
        this.logger = logger;
    }
    
    @Override
    public void run() {
        try {
            transactionLog.readTransactionLog();
        } catch (IOException ex) {
            logMessage("ERROR - Error while reading file for transaction log, item ID " + transactionLog.getItemId());
        } catch (ParseException ex) {
            logMessage("ERROR - Parsing exception while reading state file for item ID " + transactionLog.getItemId());
        }
    }

    /**
     * @return the transactionLog
     */
    public TPTransactionLog getTransactionLog() {
        return transactionLog;
    }

    /**
     * @return the logger
     */
    public CanLogMessages getLogger() {
        return logger;
    }

    /**
     * @param logger the logger to set
     */
    public void setLogger(CanLogMessages logger) {
        this.logger = logger;
    }

    @Override
    public void logMessage(String message) {
        if (this.logger != null) {
            logger.logMessage(message);
        } else {
            System.out.println(message);
        }
    }
    
}
