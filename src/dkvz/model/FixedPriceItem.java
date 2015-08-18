
package dkvz.model;

import java.util.Objects;

/**
 * Represents an item that you can't buy on the TP.
 * Recipes are not to be set as this kind of object.
 * Recipe costs are not included at all in this app.
 * I could also have had some kind of interface or mother class for the
 * Item concept.
 * That's big refactoring for low effect though.
 * @author Alain
 */
public class FixedPriceItem {
    
    private String name = "";
    /**
     * Item quantity:
     */
    private int qty = 0;
    /**
     * Cost in Gold. I could've used copper cost and then
     * I would have integer values... Oh well.
     */
    private double cost = 0.0;
    
    public FixedPriceItem() {
        
    }
    
    @Override
    public String toString() {
        return this.getName().concat(" - Fixed price item");
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof FixedPriceItem))return false;
        FixedPriceItem it = (FixedPriceItem)obj;
        if (it.getName() == null) return false;
        return it.getName().equals(this.getName());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.name);
        return hash;
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
     * @return the cost
     */
    public double getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(double cost) {
        this.cost = cost;
    }
    
}
