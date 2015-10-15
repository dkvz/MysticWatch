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
