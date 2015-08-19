
package dkvz.model;

import java.util.*;

/**
 *
 * @author william
 */
public class Item {
    
    private long id = -1;
    private String name = "";
    private long offer = 0;
    private long demand = 0;
    // Actually I should've used the prices in copper, then Double is really not needed.
    // Moreover, the API returns values in copper.
    // Since I want MONEY I'll put all the prices in gold.
    private double highestBuyOrder = 0.0;
    private double lowestSellOrder = 0.0;
    // Those next two values must include taxes.
    private double profitFromSellOrder = 0.0;
    private double profitFromDirectSelling = 0.0;
    private double lowestProfitFromSellOrder = 0.0;
    private double lowestProfitFromDirectSelling = 0.0;
    private boolean component = false;
    private ArrayList<Item> components = null;
    /**
     * Many of my projects used to have a "booléen qui sert à rien", this
     * is the one right here:
     */
    private boolean refreshed = false;
    private int qty = 0;
    private List<FixedPriceItem> fixedPriceItems = null;

    @Override
    public String toString() {
        if (this.id > 0) {
            return Long.toString(this.id).concat(" - ").concat(this.name);
        } else {
            return "";
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof Item))return false;
        Item it = (Item)obj;
        return it.getId() == this.getId();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    // We're going to calculate a lot of values in real time.
    // It would be better optimized to just do it once, and
    // have the "refreshed" flag really work.
    // A computer that can run Guild Wars 2 should be able to compute this though...
    public double getCraftingCostLow() {
        Double cost = 0.0;
        if (this.components != null && !this.components.isEmpty()) {
            for (Item item : this.components) {
                cost += item.getHighestBuyOrder() * item.getQty();
            }
        }
        cost += this.getFixedPriceItemsCost();
        return cost;
    }
    
    public double getFixedPriceItemsCost() {
        Double cost = 0.0;
        if (this.fixedPriceItems != null && !this.fixedPriceItems.isEmpty()) {
            for (FixedPriceItem fItem : this.fixedPriceItems) {
                cost += fItem.getCost() * fItem.getQty();
            }
        }
        return cost;
    }
    
    public double getCraftingCostHigh() {
        Double cost = 0.0;
        if (this.components != null && !this.components.isEmpty()) {
            for (Item item : this.components) {
                cost += item.getLowestSellOrder() * item.getQty();
            }
        }
        cost += this.getFixedPriceItemsCost();
        return cost;
    }
    
    public double getSupplyDemandRatio() {
        if (this.getDemand() > 0) {
            return (double)this.offer / this.demand;
        } else {
            return 0.0;
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
    
    public long getSupply() {
        return offer;
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
    
    public void setSupply(long supply) {
        this.offer = supply;
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
     * I'm calculating this value everytime, which isn't very effective.
     * I've talked about this earlier in this class.
     * Those methods used the lowest prices for the components, there is 
     * another for the higher prices.
     * @return the profitFromSellOrder
     */
    public double getProfitFromSellOrder() {
        this.profitFromSellOrder = (0.85 * this.getLowestSellOrder()) - this.getCraftingCostLow();
        return this.profitFromSellOrder;
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
        this.profitFromDirectSelling = (0.85 * this.getHighestBuyOrder()) - this.getCraftingCostLow();
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

    /**
     * @return the lowestProfitFromSellOrder
     */
    public double getLowestProfitFromSellOrder() {
        this.lowestProfitFromSellOrder = this.getLowestSellOrder() - this.getCraftingCostHigh();
        return lowestProfitFromSellOrder;
    }

    /**
     * @param lowestProfitFromSellOrder the lowestProfitFromSellOrder to set
     */
    public void setLowestProfitFromSellOrder(double lowestProfitFromSellOrder) {
        this.lowestProfitFromSellOrder = lowestProfitFromSellOrder;
    }

    /**
     * @return the lowestProfitFromDirectSelling
     */
    public double getLowestProfitFromDirectSelling() {
        this.lowestProfitFromDirectSelling = this.getHighestBuyOrder() - this.getCraftingCostHigh();
        return lowestProfitFromDirectSelling;
    }

    /**
     * @param lowestProfitFromDirectSelling the lowestProfitFromDirectSelling to set
     */
    public void setLowestProfitFromDirectSelling(double lowestProfitFromDirectSelling) {
        this.lowestProfitFromDirectSelling = lowestProfitFromDirectSelling;
    }

    /**
     * @return the fixedPriceItems
     */
    public List<FixedPriceItem> getFixedPriceItems() {
        return fixedPriceItems;
    }

    /**
     * @param fixedPriceItems the fixedPriceItems to set
     */
    public void setFixedPriceItems(List<FixedPriceItem> fixedPriceItems) {
        this.fixedPriceItems = fixedPriceItems;
    }
    
}
