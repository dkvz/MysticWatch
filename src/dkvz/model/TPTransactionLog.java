
package dkvz.model;

import java.io.*;
import java.nio.file.*;
import java.util.*;

/**
 *
 * @author Alain
 */
public class TPTransactionLog {
    
    public static final String PATH_TRANSACTION_LOG = "tp_logs";
    public static final String LOG_EXTENSION = ".txt";
    public static final String SEPARATOR = ";;";
   
    public static void appendToLog(TPEvent event) throws IOException {
        // If log file does not exist, try to create it.
        // We'll have to throw a whole bunch of exceptions.
        File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(event.getId()).concat(TPTransactionLog.LOG_EXTENSION)));
        StandardOpenOption opt = null;
        if (file.exists()) {
            opt = StandardOpenOption.APPEND;
        } else {
            opt = StandardOpenOption.CREATE_NEW;
        }
        
    }
    
}
