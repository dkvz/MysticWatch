
package dkvz.UI;

import java.util.*;
import dkvz.model.*;
import java.text.*;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Alain
 */
public class TPLogTableDataModel extends AbstractTableModel {
    
    private final String [] columnNames;
    // We'll use a separate list, we'll need a method to rebuild this list from
    // the original in the transaction log.
    private List<TPEvent> eventList = null;
    private TPTransactionLog transactionLog = null;
    private SimpleDateFormat dateFormat = null;

    public TPLogTableDataModel(TPTransactionLog transactionLog) {
        this.columnNames = new String[]{"Time", "Event", "Prev. order(g)", "Prev. quantity", "New order(g)", "New quantity"};
        this.transactionLog = transactionLog;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        // It's supposed to have loaded the data already.
        this.eventList = new ArrayList<TPEvent>();
        this.generateModelList();
    }
    
    public void generateModelList() {
        this.generateModelList(-1);
    }
    
    public void generateModelList(int eventType) {
        this.eventList.clear();
        if (this.transactionLog.getEventListRead() != null && !this.transactionLog.getEventListRead().isEmpty()) {
            for (TPEvent event : this.transactionLog.getEventListRead()) {
                if (eventType < 0 || event.getEventType() == eventType) {
                    this.eventList.add(event);
                }
            }
        }
    }
    
    public void clear() {
        if (this.eventList != null) {
            this.eventList.clear();
        }
    }

    @Override
    public int getRowCount() {
        return this.eventList.size();
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
    public Class getColumnClass(int c) {
        if (!this.eventList.isEmpty()) {
            return getValueAt(0, c).getClass();
        } else {
            return Object.class;
        }
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object value = null;
        TPEvent event = this.eventList.get(row);
        switch (col) {
            case 0:
                value = dateFormat.format(event.getDate());
                break;
            case 1:
                switch (event.getEventType()) {
                    case TPEvent.EVENT_TYPE_BUY_ORDER_LISTING_QUANTITY_MODIFIED:
                        value = "Quantity modified for Buy Order";
                        break;
                    case TPEvent.EVENT_TYPE_SELL_ORDER_LISTING_QUANTITY_MODIFIED:
                        value = "Quantity modified for Sell Order";
                        break;
                    case TPEvent.EVENT_TYPE_ERROR:
                        value = "Error";
                        break;
                    case TPEvent.EVENT_TYPE_HIGHEST_BUY_ORDER_CHANGED:
                        value = "Highest Buy Order changed";
                        break;
                    case TPEvent.EVENT_TYPE_LOGGING_STARTED:
                        value = "Logging started";
                        break;
                    case TPEvent.EVENT_TYPE_LOGGING_STOPPED:
                        value = "Logging stopped";
                        break;
                    case TPEvent.EVENT_TYPE_LOWEST_SELL_ORDER_CHANGED:
                        value = "Lowest Sell Order changed";
                        break;
                    case TPEvent.EVENT_TYPE_NEW_BUY_LISTING:
                        value = "New Buy Order";
                        break;
                    case TPEvent.EVENT_TYPE_NEW_SELL_LISTING:
                        value = "New Sell Order";
                        break;
                    default:
                        value = "Unknown event";
                }
                break;
            case 2:
                value = event.getPreviousPrice();
                break;
            case 3:
                value = event.getPreviousListingCount();
                break;
            case 4:
                value = event.getNewPrice();
                break;
            case 5:
                value = event.getNewListingCount();
                break;
        }
        return value;
    }
    
}
