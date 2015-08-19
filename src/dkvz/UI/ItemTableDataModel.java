
package dkvz.UI;

import javax.swing.table.*;
import dkvz.model.*;

/**
 *
 * @author william
 */
public class ItemTableDataModel extends AbstractTableModel {

    private JSONDataModel dataModel = null;
    private final String [] columnNames = {
      "Item ID", "Name", "Supply", "Demand", "Ratio", "Highest buy order", "Lowest sell order", 
        "Crafting cost low", "Crafting cost high", "Profit sell order", "Profit direct selling"
    };
    
    public ItemTableDataModel(JSONDataModel dataModel) {
        this.dataModel = dataModel;
    }
    
    @Override
    public int getRowCount() {
        return dataModel.getItemList().size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object value = null;
        Item item = this.dataModel.getItemList().get(row);
        switch (col) {
            case 0:
                value = item.getId();
                break;
            case 1:
                value = item.getName();
                break;
            case 2:
                value = item.getOffer();
                break;
            case 3:
                value = item.getDemand();
                break;
            case 4:
                value = item.getSupplyDemandRatio();
                break;
            case 5:
                value = item.getHighestBuyOrder();
                break;
            case 6:
                value = item.getLowestSellOrder();
                break;
            case 7:
                value = item.getCraftingCostLow();
                break;
            case 8:
                value = item.getCraftingCostHigh();
                break;
            case 9:
                value = item.getProfitFromSellOrder();
                break;
            case 10:
                value = item.getProfitFromDirectSelling();
                break;
        }
        return value;
    }
    
    @Override
    public Class getColumnClass(int c) {
        if (this.dataModel.getItemList().size() > 0) {
            return getValueAt(0, c).getClass();
        } else {
            return Object.class;
        }
    }
    
}
