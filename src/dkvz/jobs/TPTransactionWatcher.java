
package dkvz.jobs;

import dkvz.UI.*;
import java.util.*;
import dkvz.model.*;

/**
 *
 * @author Alain
 */
public class TPTransactionWatcher extends Observable implements Runnable {

    /**
     * Time in milliseconds between API requests.
     */
    private long requestInterval = 2000;
    private List<TPTransactionLog> transactionLogs = null;
    private boolean abort = false;
    private CanLogMessages logger = null;
    
    public TPTransactionWatcher() {
        this.transactionLogs = new ArrayList<TPTransactionLog>();
    }
    
    @Override
    public void run() {
        // I'm goind this Observable experiment since what I wanted to do is basically
        // the same.
        // I think I'm going to need to use setChanged() and stuff like that.
        
        // I'll need a synchronized method to do the actual updating of values.
        
        for (TPTransactionLog tpLog : this.transactionLogs) {
            if (this.abort) {
                break;
            }
            
        }
        this.logMessage("TP Transaction Watching thread closing...");
    }
    
    private void logMessage(String msg) {
        if (this.logger != null) {
            this.logger.logMessage(msg);
        }
    }
    
    public synchronized void addItemToWatch(Item item) {
        // The item needs not already be in the watch list:
        for (TPTransactionLog log : this.transactionLogs) {
            if (log.getItemId() == item.getId()) {
                return;
            }
        }
        // Not yet in the list:
        TPTransactionLog log = new TPTransactionLog(item.getId());
        this.transactionLogs.add(log);
    }
    
    public synchronized void removeItemToWatch(Item item) {
        this.removeItemToWatch(item.getId());
    }
    
    public synchronized void removeItemToWatch(long itemId) {
        // Let's do it this way so I haven't written equals for nothing:
        TPTransactionLog lookForMe = new TPTransactionLog(itemId);
        this.transactionLogs.remove(lookForMe);
    }

    /**
     * @return the requestInterval
     */
    public long getRequestInterval() {
        return requestInterval;
    }

    /**
     * @param requestInterval the requestInterval to set
     */
    public void setRequestInterval(long requestInterval) {
        this.requestInterval = requestInterval;
    }

    /**
     * @return the transactionLogs
     */
    public List<TPTransactionLog> getTransactionLogs() {
        return transactionLogs;
    }

    /**
     * @return the abort
     */
    public boolean isAbort() {
        return abort;
    }

    /**
     * @param abort the abort to set
     */
    public void setAbort(boolean abort) {
        this.abort = abort;
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

    
}
