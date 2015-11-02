/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkvz.model;

import java.util.*;

/**
 * Class for holding Trading Post listings data
 * @author William
 */
public class TPListings {
    
    // Mapping the price to an array with the listings and the quantity listed
    private Map<Long, Long[]> buys = null;
    private Map<Long, Long[]> sells = null;
    private long itemId = -1;
    private String fullJSONData = "";

    public TPListings() {
        this.buys = new HashMap<Long, Long[]>();
        this.sells = new HashMap<Long, Long[]>();
    }
    
    public TPListings(long itemId) {
        this();
        this.itemId = itemId;
    }
    
    public void clearAll() {
        this.buys.clear();
        this.sells.clear();
    }
    
    public List<TPEvent> getTPEventsUpToListing(TPListings listing) {
        List<TPEvent> ret = new ArrayList<TPEvent>();
        /*
        Let's have a quick list of the event types:
        EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED = 3;
        EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED = 8;
        EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED = 4;
        EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED = 5;
        EVENT_TYPE_NEW_BUY_LISTING = 6;
        EVENT_TYPE_NEW_SELL_LISTING = 7;
        EVENT_TYPE_BUY_ORDER_GONE = 9;
        EVENT_TYPE_SELL_ORDER_GONE = 10;
        */
        ret.addAll(this.getTPEventsFromListings(listing.getBuys(), this.getBuys(), true));
        ret.addAll(this.getTPEventsFromListings(listing.getSells(), this.getSells(), false));
        return ret;
    }
    
    private List<TPEvent> getTPEventsFromListings(Map<Long, Long[]> listing, Map<Long, Long[]> previousListing, boolean buys) {
        // I'm using the boolean to decide if we're checking buys or sells.
        // OK this is a bit convoluted, listing is the new listing, previousListing is the... Well previous listing.
        List<TPEvent> ret = new ArrayList<TPEvent>();
        long count = 0l;
        for (Map.Entry<Long, Long[]> entry : listing.entrySet()) {
            if (count == 0) {
                // We have special events for the first entries.
                // Here I just need to know if it was the first entry before.
                Map.Entry<Long, Long[]> firstEntryBefore = null;
                if (previousListing.entrySet().iterator().hasNext()) {
                    firstEntryBefore = previousListing.entrySet().iterator().next();
                }
                if (firstEntryBefore == null || !firstEntryBefore.getKey().equals(entry.getKey())) {
                    // This wasn't the first entry before, so it's a new top listing.
                    // It may be a new listing too but that will be checked later on in this loop.
                    TPEvent event = null;
                    if (buys) {
                        event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED);
                    } else {
                        event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED);
                    }
                    if (firstEntryBefore != null) {
                        event.setValuesFromListingEntries(firstEntryBefore.getKey(), firstEntryBefore.getValue(), entry.getKey(), entry.getValue());
                    } else {
                        event.setValuesFromListingEntries(0l, TPListings.makeZeroesValueArray(), entry.getKey(), entry.getValue());
                    }
                    ret.add(event);
                }
            }
            Long[] previousArr = previousListing.get(entry.getKey());
            if (previousArr != null) {
                // The listing existed before.
                // Check if the amount of listings changed (we have no event for the quantity and we don't care):
                if (!previousArr[0].equals(entry.getValue()[0])) {
                    // We need to fire the event that means the amount of listings changed:
                    TPEvent event = null;
                    if (buys) {
                        event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED);
                    } else {
                        event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED);
                    }
                    event.setValuesFromListingEntries(entry.getKey(), previousArr, entry.getKey(), entry.getValue());
                    ret.add(event);
                }
            } else {
                // We have a new listing.
                // Let's build an array with zeroes for the previous listings and total quantity:
                Long [] zeroes = TPListings.makeZeroesValueArray();
                TPEvent event = null;
                if (buys) {
                    event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_NEW_BUY_LISTING);
                } else {
                    event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_NEW_SELL_LISTING);
                }
                event.setValuesFromListingEntries(0l, zeroes, entry.getKey(), entry.getValue());
                ret.add(event);
            }
            count++;
        }
        
        // We need to check somewhere if listings are gone (listings that were there before).
        for (Map.Entry<Long, Long[]> entry : previousListing.entrySet()) {
            Long [] existingArr = listing.get(entry.getKey());
            if (existingArr == null) {
                // This entry is no longer there (existingArr is empty btw).
                Long [] zeroes = TPListings.makeZeroesValueArray();
                TPEvent event = null;
                if (buys) {
                    event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_BUY_ORDER_GONE);
                } else {
                    event = new TPEvent(this.getItemId(), TPEvent.EVENT_TYPE_SELL_ORDER_GONE);
                }
                event.setValuesFromListingEntries(entry.getKey(), entry.getValue(), 0l, zeroes);
                ret.add(event);
            }
        }
        
        return ret;
    }
    
    /**
     * I just love that method.
     * @return an Array of Long, dimension 2, with value 0 for both values.
     */
    private static Long [] makeZeroesValueArray() {
        Long [] zeroes = new Long[2];
        zeroes[0] = 0l;
        zeroes[1] = 0l;
        return zeroes;
    }
    
    /**
     * @return the buys
     */
    public Map<Long, Long[]> getBuys() {
        return buys;
    }

    /**
     * @param buys the buys to set
     */
    public void setBuys(Map<Long, Long[]> buys) {
        this.buys = buys;
    }

    /**
     * @return the sells
     */
    public Map<Long, Long[]> getSells() {
        return sells;
    }

    /**
     * @param sells the sells to set
     */
    public void setSells(Map<Long, Long[]> sells) {
        this.sells = sells;
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
     * @return the fullJSONData
     */
    public String getFullJSONData() {
        return fullJSONData;
    }

    /**
     * @param fullJSONData the fullJSONData to set
     */
    public void setFullJSONData(String fullJSONData) {
        this.fullJSONData = fullJSONData;
    }
       
}
