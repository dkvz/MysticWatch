
package dkvz.jobs;

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
    private long requestInterval = 1000;
    private List<TPTransactionLog> transactionLogs = null;
    
    public TPTransactionWatcher() {
        this.transactionLogs = new ArrayList<TPTransactionLog>();
    }
    
    @Override
    public void run() {
        // I'm goind this Observable experiment since what I wanted to do is basically
        // the same.
        // I think I'm going to need to use setChanged() and stuff like that.
        
        // I'll need a synchronized method to do the actual updating of values.
        
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

    
}
