
package dkvz.model;

import java.util.*;

/**
 *
 * @author Alain
 */
public class TPEvent {
    
    public static final int EVENT_TYPE_ERROR = 0;
    public static final int EVENT_TYPE_LOGGING_STARTED = 1;
    public static final int EVENT_TYPE_LOGGING_STOPPED = 2;
    public static final int EVENT_TYPE_LISTING_QUANTITY_MODIFIED = 3;
    public static final int EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED = 4;
    public static final int EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED = 5;
    public static final int EVENT_TYPE_NEW_BUY_LISTING = 6;
    public static final int EVENT_TYPE_NEW_SELL_LISTING = 7;
    
    private final long id;
    private final int eventType;
    private double previousPrice;
    private double newPrice;
    private long listingCount;
    private long previousListingCount;
    private Date date;

    public TPEvent(long id, int eventType) {
        this.id = id;
        this.eventType = eventType;
        this.date = new Date();
    }
    
    public TPEvent(Item item, int eventType) {
        this(item.getId(), eventType);
        this.date = new Date();
    }
    
    public TPEvent(long id, int eventType, Date date) {
        this(id, eventType);
        this.date = date;
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the eventType
     */
    public int getEventType() {
        return eventType;
    }

    /**
     * @return the previousPrice
     */
    public double getPreviousPrice() {
        return previousPrice;
    }

    /**
     * @param previousPrice the previousPrice to set
     */
    public void setPreviousPrice(double previousPrice) {
        this.previousPrice = previousPrice;
    }

    /**
     * @return the newPrice
     */
    public double getNewPrice() {
        return newPrice;
    }

    /**
     * @param newPrice the newPrice to set
     */
    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    /**
     * @return the listingCount
     */
    public long getListingCount() {
        return listingCount;
    }

    /**
     * @param listingCount the listingCount to set
     */
    public void setListingCount(long listingCount) {
        this.listingCount = listingCount;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the previousListingCount
     */
    public long getPreviousListingCount() {
        return previousListingCount;
    }
    
    public Long getNewListingCount() {
        return this.listingCount;
    }
    
    public void setNewListingCount(long newListingCount) {
        this.listingCount = newListingCount;
    }

    /**
     * @param previousListingCount the previousListingCount to set
     */
    public void setPreviousListingCount(long previousListingCount) {
        this.previousListingCount = previousListingCount;
    }
    
}
