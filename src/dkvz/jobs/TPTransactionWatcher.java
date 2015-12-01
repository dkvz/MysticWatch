package dkvz.jobs;

import dkvz.UI.*;
import java.util.*;
import dkvz.model.*;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Alain
 */
public class TPTransactionWatcher extends Observable implements Runnable, CanLogMessages {

    /**
     * Time in milliseconds between API requests.
     */
    private long requestInterval = 5000;
    private List<TPTransactionLog> transactionLogs = null;
    private boolean abort;
    private CanLogMessages logger = null;
    private Thread watcherThread = null;

    public TPTransactionWatcher() {
        this.transactionLogs = new ArrayList<TPTransactionLog>();
        this.setAbort(true);
    }

    @Override
    public void run() {
        // I'm goind this Observable experiment since what I wanted to do is basically
        // the same.
        // I think I'm going to need to use setChanged() and stuff like that.

        // I'll need a synchronized method to do the actual updating of values.
        try {
            while (!this.abort) {
                //for (TPTransactionLog tpLog : this.transactionLogs) {
                for (int i = 0; i < this.transactionLogs.size(); i++) {
                    if (this.abort || this.isEmpty()) {
                        break;
                    }
                    TPTransactionLog tpLog = this.transactionLogs.get(i);
                    if (tpLog == null) continue;
                    // Check if those instances have loaded their data (should be loaded before so we
                    // can easily display the progress on progress bars).
                    if (!tpLog.isLoadedStateOnly()) {
                        try {
                            this.logMessage("TP listings for " + tpLog.getItemId() + " have to be loaded...");
                            tpLog.loadItemState();
                            this.logMessage("TP listings loaded.");
                        } catch (Exception ex) {
                            this.logMessage("ERROR - TP listings for item " + tpLog.getItemId() + " could not be loaded.");
                            this.logMessage("Removing the item from the watch list.");
                            this.removeItemToWatch(tpLog.getItemId());
                        }
                    }
                    try {
                        // Load the updated state from the API:
                        this.logMessage("Getting updated listings for item " + tpLog.getItemId() + "...");
                        TPListings newState = GW2APIHelper.getTPListings(tpLog.getItemId());
                        if (this.abort) {
                            break;
                        }
                        // We're supposed to compare with the older state to create events... Right?
                        // There should be a comparison method in TPListings.
                        List<TPEvent> tpEvents = tpLog.getTpListings().getTPEventsUpToListing(newState);
                        // Save the new listing as the current listing.
                        tpLog.setTpListings(newState);
                        if (!tpEvents.isEmpty()) {
                        // We got some stuff that happened.
                            // TODO We should write to the actual file, and also notify the observers of events that may be interresting for
                            // those.
                            try {
                                // Writing to the log:
                                TPTransactionLog.appendEventListToLog(tpEvents, tpLog.getItemId());
                            } catch (IOException ex) {
                                this.logMessage("ERROR - Could not write transaction log file for " + tpLog.getItemId() + " - Input Output Exception");
                            } catch (SecurityException ex) {
                                this.logMessage("ERROR - Could not write transaction log file for " + tpLog.getItemId() + " - Security Exception - Check permissions");
                            }
                        // Notify observers. We need to pass on a list of events, it's going to be set to the tpLog object,
                            // so it won't have all the events, just the latest detected.
                            tpLog.setEventListRead(new ArrayList<TPEvent>());
                            tpLog.getEventListRead().addAll(tpEvents);
                            this.setChanged();
                            this.notifyObservers(tpLog);
                        }

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
                        this.logMessage("ERROR parsing the JSON in the response from the API for item " + tpLog.getItemId());
                    }
                    // Sleep for the time set in property.
                    if (this.requestInterval > 0) {
                        Thread.sleep(this.requestInterval);
                    }
                }
                Thread.yield();
            }
        } catch (InterruptedException ex) {
            // We're just stopping the thread here.
            this.logMessage("Logging thread has been interrupted.");
        }
        this.setAbort(true);
        this.setChanged();
        // Notify observers that the thread ended.
        this.notifyObservers();
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

    public void startWatcherThread() {
        if (this.watcherThread == null || !this.watcherThread.isAlive()) {
            this.watcherThread = new Thread(this);
            this.setAbort(false);
            this.watcherThread.start();
        }
    }

    public void interruptWatcherThread() {
        if (this.watcherThread != null) {
            this.watcherThread.interrupt();
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
        if (item.getName() != null) {
            log.setName(item.getName());
        }
        this.transactionLogs.add(log);
    }

    public synchronized void addItemToWatch(long itemId) {
        Item item = new Item();
        item.setId(itemId);
        this.addItemToWatch(item);
    }
    
    public synchronized void addItemToWatch(long itemId, String name) {
        Item item = new Item();
        item.setId(itemId);
        item.setName(name);
        this.addItemToWatch(item);
    }

    public synchronized void removeItemToWatch(Item item) {
        this.removeItemToWatch(item.getId());
    }

    public synchronized void removeItemToWatch(long itemId) {
        // Let's do it this way so I haven't written equals for nothing:
        TPTransactionLog lookForMe = new TPTransactionLog(itemId);
        this.transactionLogs.remove(lookForMe);
        if (this.isEmpty()) {
            // We can stop logging.
            this.setAbort(true);
        }
    }

    public synchronized void removeAllItemsToWatch() {
        this.transactionLogs.clear();
        this.setAbort(true);
    }

    public synchronized boolean isEmpty() {
        return this.transactionLogs.isEmpty();
    }

    public synchronized boolean contains(Item item) {
        return this.contains(item.getId());
    }

    public synchronized boolean contains(long itemId) {
        // Again using awkward equals antics:
        TPTransactionLog lookForMe = new TPTransactionLog(itemId);
        return this.transactionLogs.contains(lookForMe);
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
    public final boolean isAbort() {
        return abort;
    }

    /**
     * @param abort the abort to set
     */
    public final void setAbort(boolean abort) {
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
