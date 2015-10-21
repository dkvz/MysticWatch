
package dkvz.jobs;

import dkvz.UI.*;
import java.util.*;
import dkvz.model.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alain
 */
public class TPTransactionWatcher extends Observable implements Runnable, CanLogMessages {

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
            // Check if those instances have loaded their data (should be loaded before so we
            // can easily display the progress on progress bars).
            if (!tpLog.isLoaded()) {
                try {
                    this.logMessage("Transaction log " + tpLog.getItemId() + " has to be loaded...");
                    tpLog.readTransactionLog();
                    this.logMessage("Transaction log loaded.");
                } catch (Exception ex) {
                    this.logMessage("ERROR - Transaction log for item " + tpLog.getItemId() + " could not be loaded.");
                    this.logMessage("Removing the item from the watch list.");
                    this.removeItemToWatch(tpLog.getItemId());
                }
            }
            try {
                // Load the updated state from the API:
                TPListings newState = GW2APIHelper.getTPListings(tpLog.getItemId());
                // We're supposed to compare with the older state to create events... Right?
                // There should be a comparison method in TPListings.
                List<TPEvent> tpEvents = tpLog.getTpListings().getTPEventsUpToListing(newState);
                if (!tpEvents.isEmpty()) {
                    // We got some stuff that happened.
                    // TODO We should write to the actual file, and also notify the observers of events that may be interresting for
                    // those.
                    
                }
                
                // Save the new listing as the current listing.
                tpLog.setTpListings(newState);
                // Save the state.
                try {
                    tpLog.saveItemState();
                } catch (IOException ex) {
                    this.logMessage("ERROR - Could not save the item state for Item " + tpLog.getItemId() + " - Input Output Exception");
                } catch (org.json.simple.parser.ParseException ex) {
                    this.logMessage("ERROR - Could not save the item state for Item " + tpLog.getItemId() + " - The saved data for this item is corrupted");
                }
                
            } catch (IOException ex) {
                this.logMessage("ERROR - IO Exception while looking for listings for item " + tpLog.getItemId());
            } catch (ParseException ex) {
                Logger.getLogger("ERROR parsing the JSON in the response from the API for item " + tpLog.getItemId());
            }
        }
        this.logMessage("TP Transaction Watching thread closing...");
    }
    
    @Override
    public void logMessage(String msg) {
        if (this.logger != null) {
            this.logger.logMessage(msg);
        } else {
            System.out.println(msg);
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
