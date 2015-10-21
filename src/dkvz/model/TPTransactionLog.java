
package dkvz.model;

import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 *
 * @author Alain
 */
public class TPTransactionLog {
    
    // It's important that this path doesn't have path separators anywhere:
    public static final String PATH_TRANSACTION_LOG = "tp_logs";
    // Extensions should have an excplicit leading ".":
    public static final String LOG_EXTENSION = ".txt";
    // Extensions should have an excplicit leading ".":
    public static final String STATE_EXTENSION = ".json";
    // This is the CSV separator. I don't even know why I'm saving in CSV but whatever.
    public static final String SEPARATOR = ";;";
    // There is a sub CSV in the CSV (of course) and this is its separator:
    public static final String VALUES_SEPARATOR = "|";
    
    private int progress;
    private long itemId;
    private String name;
    private boolean loaded = false;
    private List<TPEvent> eventListRead = null;
    private TPListings tpListings = null;
    
    public TPTransactionLog(long itemId) {
        this.itemId = itemId;
        this.progress = 0;
        this.name = "";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof TPTransactionLog))return false;
        TPTransactionLog it = (TPTransactionLog)obj;
        return it.getItemId() == this.getItemId();
    }

    /**
     * I don't even know what this is for
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.itemId ^ (this.itemId >>> 32));
        return hash;
    }
    
    public void readTransactionLog() throws IOException, org.json.simple.parser.ParseException {
        this.eventListRead = new ArrayList<TPEvent>();
        this.progress = 0;
        if (this.itemId > 0) {
            File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(File.pathSeparator).concat(Long.toString(this.getItemId()).concat(TPTransactionLog.LOG_EXTENSION)));
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
        this.loadItemState();
        this.loaded = true;
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
                                // Order: prev highest buy order ; prev listings ; new highest buy order ; new listings
                                // Also added quantity later on because the data is available, not sure if I'm going
                                // to display it though.
                                event.setPreviousPrice(Double.parseDouble(values[0]));
                                event.setPreviousListingCount(Long.parseLong(values[1]));
                                event.setNewPrice(Double.parseDouble(values[2]));
                                event.setNewListingCount(Long.parseLong(values[3]));
                                event.setPreviousQuantity(Long.parseLong(values[4]));
                                event.setNewQuantity(Long.parseLong(values[5]));
                                break;
                            case TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED:
                            case TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED:
                                // Item price ; Prev lising count ; New listing count ; Prev quantity ; New quantity
                                event.setPreviousPrice(Double.parseDouble(values[0]));
                                event.setPreviousListingCount(Long.parseLong(values[1]));
                                event.setNewListingCount(Long.parseLong(values[2]));
                                event.setPreviousQuantity(Long.parseLong(values[3]));
                                event.setNewQuantity(Long.parseLong(values[4]));
                                break;
                            case TPEvent.EVENT_TYPE_NEW_BUY_LISTING:
                            case TPEvent.EVENT_TYPE_NEW_SELL_LISTING:
                                // Just the new price ; new listing count ; new quantity
                                event.setNewPrice(Double.parseDouble(values[0]));
                                event.setNewListingCount(Long.parseLong(values[1]));
                                event.setNewQuantity(Long.parseLong(values[2]));
                                break;
                            case TPEvent.EVENT_TYPE_BUY_ORDER_GONE:
                            case TPEvent.EVENT_TYPE_SELL_ORDER_GONE:
                                // Just the prev price ; prev listing count ; prev quantity
                                event.setPreviousPrice(Double.parseDouble(values[0]));
                                event.setPreviousListingCount(Long.parseLong(values[1]));
                                event.setPreviousQuantity(Long.parseLong(values[2]));
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
     * @param file the JSON state File to process
     * @return an Item instance with the name and ID set
     * @throws java.io.IOException If reading the file causes issues
     * @throws org.json.simple.parser.ParseException is the JSON cannot be parsed accordingly
     */
    public static Item readItemFromStateFile(File file) throws IOException, org.json.simple.parser.ParseException {
        Item res = null;
        // First check if the file exists.
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(file));
        try {
            JSONObject jsonObject = (JSONObject) obj;
            Long id = (Long)jsonObject.get("id");
            String name = (String)jsonObject.get("name");
            if (id == null) {
                throw new org.json.simple.parser.ParseException(20);
            } else {
                if (name == null) name = "";
                res.setId(id);
                res.setName(name);
            }
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw new org.json.simple.parser.ParseException(10);
        }
        return res;
    }
    
    public void saveItemState() throws IOException, org.json.simple.parser.ParseException {
        // This got written after the loadItemState() mathod.
        // I'm trying to take advantage of the full JSON text that should be in the TPListings object.
        // We cannot save if the item ID is bogus or if there is no TPListings object.
        if (this.itemId > 0 && this.tpListings != null) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(this.tpListings.getFullJSONData());
            try {
                JSONObject jsonObject = (JSONObject)obj;
                if (this.name != null && !this.name.isEmpty()) {
                    jsonObject.put("name", this.name);
                }
                // Check if directory exists:
                TPTransactionLog.checkAndCreateTransactionLogDir();
                // This is a "try with resources" thing, I don't know if it works.
                try (FileWriter file = new FileWriter(TPTransactionLog.PATH_TRANSACTION_LOG.concat(File.pathSeparator).concat(Long.toString(this.getItemId())).concat(".json"))) {
                    file.write(jsonObject.toJSONString());
                    file.flush();
                }
            } catch (ClassCastException ex) {
                // Don't ask me why I put 12 in that constructor.
                throw new org.json.simple.parser.ParseException(12);
            }
        }
    }
    
    /**
     * Try to load the state for this transaction logging instance from the state file
     * Also loads the name of the item if it's in there and has not been set on the current
     * TPTransactionLog instance.
     * @throws java.io.IOException in case of file input errors
     * @throws org.json.simple.parser.ParseException in case of class casting error or general JSON parsing errors
     */
    public void loadItemState() throws IOException, org.json.simple.parser.ParseException {
        this.tpListings = new TPListings(this.itemId);
        // Check if the file exists:
        if (this.itemId > 0) {
            String filPath = TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(itemId)).concat(".json");
            File file = new File(filPath);
            if (file.exists()) {
                // It does exist.
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(file));
                try {
                    JSONObject jsonObject = (JSONObject) obj;
                    Object name = jsonObject.get("name");
                    if (name != null) {
                        this.name = (String)name;
                    }
                    // We're looking for two lists: buys and sells.
                    // Both lists of maps.
                    JSONArray rdBuys = (JSONArray) jsonObject.get("buys");
                    JSONArray rdSells = (JSONArray) jsonObject.get("sells");
                    // The following redundant code could be refactored but I don't have the motivation.
                    for (Object b : rdBuys) {
                        Map<String, Long> bb = (Map<String, Long>) b;
                        Long listings = bb.get("listings");
                        Long unitPrice = bb.get("unit_price");
                        Long quantity = bb.get("quantity");
                        Long [] arr = new Long[2];
                        arr[0] = listings;
                        arr[1] = quantity;
                        this.tpListings.getBuys().put(unitPrice, arr);
                    }
                    for (Object b : rdSells) {
                        Map<String, Long> bb = (Map<String, Long>) b;
                        Long listings = bb.get("listings");
                        Long unitPrice = bb.get("unit_price");
                        Long quantity = bb.get("quantity");
                        Long [] arr = new Long[2];
                        arr[0] = listings;
                        arr[1] = quantity;
                        this.tpListings.getSells().put(unitPrice, arr);
                    }
                } catch (ClassCastException ex) {
                    ex.printStackTrace();
                    throw new org.json.simple.parser.ParseException(10);
                }
            }
        }
    }
    
    public static void checkAndCreateTransactionLogDir() throws IOException {
        File folder = new File(TPTransactionLog.PATH_TRANSACTION_LOG);
        if (!folder.exists()) {
            // Create the folder:
            folder.mkdir();
        } else if (!folder.isDirectory()) {
            // That's a big problem, not deleting that file that's not a directory.
            throw new IOException("The log path " + TPTransactionLog.PATH_TRANSACTION_LOG + " exists but is not a directory. Cannot log data.");
        }
    }
    
    public static void appendEventListToLog(List<TPEvent> events, long itemId) throws IOException, SecurityException {
        // If log file does not exist, try to create it.
        // We'll have to throw a whole bunch of exceptions.
        // Check for folder existence and create the directory if it doesn't exist:
        TPTransactionLog.checkAndCreateTransactionLogDir();
        File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(itemId).concat(TPTransactionLog.LOG_EXTENSION)));
        StandardOpenOption opt = null;
        if (file.exists()) {
            opt = StandardOpenOption.APPEND;
        } else {
            opt = StandardOpenOption.CREATE_NEW;
        }
        for (TPEvent event : events) {
            String line = TPTransactionLog.generateLogLine(event);
            Files.write(Paths.get(file.getPath()), line.getBytes(), opt);
        }
    }
    
    private static String generateLogLine(TPEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss");
	String dateToStr = format.format(new Date());
        String line = dateToStr.concat(TPTransactionLog.SEPARATOR).concat(Integer.toString(event.getEventType()));
        // Now it depends on the type of event:
        switch (event.getEventType()) {
            case TPEvent.EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED:
            case TPEvent.EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED:
                // Log new highest buy order, previous highest buy order + quantity for reach.
                // Order: prev highest buy order ; prev listings ; new highest buy order ; new listings ; prev quantity ; new quantity
                line = line.concat(Double.toString(event.getNewPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Double.toString(event.getPreviousPrice())).concat(TPTransactionLog.VALUES_SEPARATOR)
                        .concat(Long.toString(event.getPreviousListingCount())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getPreviousQuantity()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getQuantity()));
                break;
            case TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED:
            case TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED:
                // Item price ; Prev lising count ; New listing count ; Prev quantity ; New quantity
                line = line.concat(Double.toString(event.getPreviousPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getPreviousListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount())).concat(TPTransactionLog.VALUES_SEPARATOR)
                        .concat(Long.toString(event.getPreviousQuantity())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getQuantity()));
                break;
            case TPEvent.EVENT_TYPE_NEW_BUY_LISTING:
            case TPEvent.EVENT_TYPE_NEW_SELL_LISTING:
                // Just the new price ; new listing count ; new quantity
                line = line.concat(Double.toString(event.getNewPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getNewListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getQuantity()));
                break;
            case TPEvent.EVENT_TYPE_BUY_ORDER_GONE:
            case TPEvent.EVENT_TYPE_SELL_ORDER_GONE:
                // Just the prev price ; prev listing count ; prev quantity
                line = line.concat(Double.toString(event.getPreviousPrice())).concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getPreviousListingCount()))
                        .concat(TPTransactionLog.VALUES_SEPARATOR).concat(Long.toString(event.getPreviousQuantity()));
                break;
            default:
                line = line.concat("0");
        }
        return line.concat("\n");
    }
   
    public static void appendToLog(TPEvent event) throws IOException, SecurityException {
        // If log file does not exist, try to create it.
        // We'll have to throw a whole bunch of exceptions.
        // Check for folder existence and create the directory if it doesn't exist:
        TPTransactionLog.checkAndCreateTransactionLogDir();
        File file = new File(TPTransactionLog.PATH_TRANSACTION_LOG.concat(Long.toString(event.getId()).concat(TPTransactionLog.LOG_EXTENSION)));
        StandardOpenOption opt = null;
        if (file.exists()) {
            opt = StandardOpenOption.APPEND;
        } else {
            opt = StandardOpenOption.CREATE_NEW;
        }
        String line = TPTransactionLog.generateLogLine(event);
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

    /**
     * @return the loaded
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * @param loaded the loaded to set
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    /**
     * @return the tpListings
     */
    public TPListings getTpListings() {
        return tpListings;
    }

    /**
     * @param tpListings the tpListings to set
     */
    public void setTpListings(TPListings tpListings) {
        this.tpListings = tpListings;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
}
