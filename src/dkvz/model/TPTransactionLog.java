
package dkvz.model;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 *
 * @author Alain
 */
public class TPTransactionLog {
    
    public static final String PATH_TRANSACTION_LOG = "tp_logs";
    public static final String LOG_EXTENSION = ".txt";
    public static final String SEPARATOR = ";;";
    public static final String VALUES_SEPARATOR = "|";
    
    private int progress;
    private long itemId;
    private List<TPEvent> eventListRead = null;
    
    public TPTransactionLog(long itemId) {
        this.itemId = itemId;
        this.progress = 0;
    }
    
    public void readTransactionLog() throws IOException {
        this.eventListRead = new ArrayList<TPEvent>();
        this.progress = 0;
        if (this.itemId > 0) {
            File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(this.getItemId()).concat(TPTransactionLog.LOG_EXTENSION)));
            if (file.exists()) {
                try {
                    FileInputStream fstream = new FileInputStream(file);
                    BufferedReader br = null;
                    try {
                        br = new BufferedReader(new InputStreamReader(fstream));
                        String strLine;
                        long readByte = 0;
                        long max = file.length();
                        if (max == 0) max = 1; // Uh...

                        //Read File Line By Line
                        while ((strLine = br.readLine()) != null)   {
                            // Interpret the line:
                            TPEvent event = TPTransactionLog.logLineToEvent(strLine, itemId);
                            if (event != null) {
                                this.eventListRead.add(event);
                            }
                            readByte += strLine.getBytes().length;
                            this.progress = (int)(((double)readByte / (double)max) * 100.0);
                        }
                    } finally {
                        if (br != null) {
                            br.close();
                        }
                    }
                } catch (FileNotFoundException ex) {
                    // Not suposed to happen. We check for file existence first.
                    ex.printStackTrace();
                }
            }
        }
        this.progress = 100;
    }
    
    private static TPEvent logLineToEvent(String line, long itemId) {
        TPEvent event = null;
        if (line != null && !line.isEmpty()) {
            String [] l = line.split(TPTransactionLog.SEPARATOR);
            if (l.length == 3) {
                // Date ; Event Type ; Values
                try {
                    SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
                    Date date = format.parse(l[0]);
                    int eventType = Integer.parseInt(l[1]);
                    // Now parse the values, depends on the eventType:
                    String [] values = l[2].split(TPTransactionLog.VALUES_SEPARATOR);
                    if (values != null) {
                        event = new TPEvent(itemId, eventType);
                        event.setDate(date);
                        switch (eventType) {
                            case TPEvent.EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED:
                            case TPEvent.EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED:
                                // Log new highest buy order, previous highest buy order + quantity for reach.
                                // Order: prev highest buy order ; prev quantity ; new highest buy order ; new quantity
                                event.setPreviousPrice(Double.parseDouble(values[0]));
                                event.setPreviousListingCount(Long.parseLong(values[1]));
                                event.setNewPrice(Double.parseDouble(values[2]));
                                event.setNewListingCount(Long.parseLong(values[3]));
                                break;
                            case TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED:
                            case TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED:
                                // Item price ; Prev lising count ; New listing count
                                event.setPreviousPrice(Double.parseDouble(values[0]));
                                event.setPreviousListingCount(Long.parseLong(values[1]));
                                event.setNewListingCount(Long.parseLong(values[2]));
                                break;
                            case TPEvent.EVENT_TYPE_NEW_BUY_LISTING:
                            case TPEvent.EVENT_TYPE_NEW_SELL_LISTING:
                                // Just the new price ; new listing count
                                event.setNewPrice(Double.parseDouble(values[0]));
                                event.setNewListingCount(Long.parseLong(values[1]));
                                break;
                            default:
                                return null;
                        }
                    }
                } catch (ParseException | NumberFormatException | ArrayIndexOutOfBoundsException ex) {
                    // Returning null.
                    return null;
                }
            }
        }
        return event;
    }
    
    /**
     * Static method to read an item name and id from a state file (in JSON)
     * @param file the JSON state file to process
     * @return an Item instance with the name and ID set
     */
    public static Item readItemFromStateFile(File file) {
        Item res = null;
        
        return res;
    }
   
    public static void appendToLog(TPEvent event) throws IOException, SecurityException {
        // If log file does not exist, try to create it.
        // We'll have to throw a whole bunch of exceptions.
        // Check for folder existence:
        File folder = new File(TPTransactionLog.PATH_TRANSACTION_LOG);
        if (!folder.exists()) {
            // Create the folder:
            folder.mkdir();
        } else if (!folder.isDirectory()) {
            // That's a big problem, not deleting that file that's not a directory.
            throw new IOException("The log path " + TPTransactionLog.PATH_TRANSACTION_LOG + " exists but is not a directory. Cannot log data.");
        }
        File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(event.getId()).concat(TPTransactionLog.LOG_EXTENSION)));
        StandardOpenOption opt = null;
        if (file.exists()) {
            opt = StandardOpenOption.APPEND;
        } else {
            opt = StandardOpenOption.CREATE_NEW;
        }
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
	String dateToStr = format.format(new Date());
        String line = dateToStr.concat(TPTransactionLog.SEPARATOR).concat(Integer.toString(event.getEventType()));
        // Now it depends on the type of event:
        switch (event.getEventType()) {
            case TPEvent.EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED:
            case TPEvent.EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED:
                // Log new highest buy order, previous highest buy order + quantity for reach.
                // Order: prev highest buy order ; prev quantity ; new highest buy order ; new quantity
                line = line.concat(Double.toString(event.getNewPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Double.toString(event.getPreviousPrice())).concat(TPTransactionLog.VALUES_SEPARATOR)
                        .concat(Long.toString(event.getPreviousListingCount()));
                break;
            case TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED:
            case TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED:
                // Item price ; Prev lising count ; New listing count
                line = line.concat(Double.toString(event.getPreviousPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getPreviousListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount()));
                break;
            case TPEvent.EVENT_TYPE_NEW_BUY_LISTING:
            case TPEvent.EVENT_TYPE_NEW_SELL_LISTING:
                // Just the new price ; new listing count
                line = line.concat(Double.toString(event.getNewPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount()));
                break;
            default:
                line = line.concat("0");
        }
        line = line.concat("\n");
        Files.write(Paths.get(file.getPath()), line.getBytes(), opt);
    }

    /**
     * @return the progress
     */
    public int getProgress() {
        return progress;
    }

    /**
     * @param progress the progress to set
     */
    public void setProgress(int progress) {
        this.progress = progress;
    }

    /**
     * @return the itemId
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * @return the eventListRead
     */
    public List<TPEvent> getEventListRead() {
        return eventListRead;
    }

    /**
     * @param eventListRead the eventListRead to set
     */
    public void setEventListRead(List<TPEvent> eventListRead) {
        this.eventListRead = eventListRead;
    }
    
}
