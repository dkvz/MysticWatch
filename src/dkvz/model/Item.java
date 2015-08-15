
package dkvz.model;

import java.util.*;

/**
 *
 * @author william
 */
public class Item {
    
    private long id = -1;
    private String name = "";
    private long offer;
    private long demand;
    private double highestBuyOrder;
    private double lowestSellOrder;
    // Those next two values must include taxes.
    private double profitFromSellOrder = 0;
    private double profitFromDirectSelling = 0;
    private boolean component = false;
    private ArrayList<Item> components = null;
    private boolean refreshed = false;
    private int qty = 0;

    @Override
    public String toString() {
        if (this.id > 0) {
            return Long.toString(this.id).concat(" - ").concat(this.name);
        } else {
            return "";
        }
    }
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
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

    /**
     * @return the offer
     */
    public long getOffer() {
        return offer;
    }

    /**
     * @param offer the offer to set
     */
    public void setOffer(long offer) {
        this.offer = offer;
    }

    /**
     * @return the demand
     */
    public long getDemand() {
        return demand;
    }

    /**
     * @param demand the demand to set
     */
    public void setDemand(long demand) {
        this.demand = demand;
    }

    /**
     * @return the highestBuyOrder
     */
    public double getHighestBuyOrder() {
        return highestBuyOrder;
    }

    /**
     * @param highestBuyOrder the highestBuyOrder to set
     */
    public void setHighestBuyOrder(double highestBuyOrder) {
        this.highestBuyOrder = highestBuyOrder;
    }

    /**
     * @return the lowestSellOrder
     */
    public double getLowestSellOrder() {
        return lowestSellOrder;
    }

    /**
     * @param lowestSellOrder the lowestSellOrder to set
     */
    public void setLowestSellOrder(double lowestSellOrder) {
        this.lowestSellOrder = lowestSellOrder;
    }

    /**
     * @return the profitFromSellOrder
     */
    public double getProfitFromSellOrder() {
        return profitFromSellOrder;
    }

    /**
     * @param profitFromSellOrder the profitFromSellOrder to set
     */
    public void setProfitFromSellOrder(double profitFromSellOrder) {
        this.profitFromSellOrder = profitFromSellOrder;
    }

    /**
     * @return the profitFromDirectSelling
     */
    public double getProfitFromDirectSelling() {
        return profitFromDirectSelling;
    }

    /**
     * @param profitFromDirectSelling the profitFromDirectSelling to set
     */
    public void setProfitFromDirectSelling(double profitFromDirectSelling) {
        this.profitFromDirectSelling = profitFromDirectSelling;
    }

    /**
     * @return the component
     */
    public boolean isComponent() {
        return component;
    }

    /**
     * @param component the component to set
     */
    public void setComponent(boolean component) {
        this.component = component;
    }

    /**
     * @return the components
     */
    public ArrayList<Item> getComponents() {
        return components;
    }

    /**
     * @param components the components to set
     */
    public void setComponents(ArrayList<Item> components) {
        this.components = components;
    }

    /**
     * @return the refreshed
     */
    public boolean isRefreshed() {
        return refreshed;
    }

    /**
     * @param refreshed the refreshed to set
     */
    public void setRefreshed(boolean refreshed) {
        this.refreshed = refreshed;
    }

    /**
     * @return the qty
     */
    public int getQty() {
        return qty;
    }

    /**
     * @param qty the qty to set
     */
    public void setQty(int qty) {
        this.qty = qty;
    }
    
}
