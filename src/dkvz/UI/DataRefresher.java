package dkvz.UI;

import dkvz.model.*;
import java.io.IOException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author william
 */
public class DataRefresher implements Runnable {

    private JFrameMain mainFrame = null;
    private boolean abort = false;
    private final Item singleItem;

    public DataRefresher(JFrameMain mainFrame) {
        this.mainFrame = mainFrame;
        this.singleItem = null;
    }
    
    public DataRefresher(JFrameMain mainFrame, Item item) {
        this.mainFrame = mainFrame;
        this.singleItem = item;
    }

    @Override
    public void run() {
        // I can change the progress bar minimum and maximum.
        if (this.singleItem != null) {
            this.refreshSingleItem(this.singleItem);
        } else {
            this.refreshData();
        }
    }

    public void refreshSingleItem(Item item) {
        int progress = 0;
        this.mainFrame.getjProgressBarStatus().setValue(progress);
        int maximum = 100;
        this.mainFrame.getjProgressBarStatus().setMaximum(maximum);
        try {
            this.mainFrame.getjLabelStatus().setText("Updating values from API...");
            this.mainFrame.logMessage("Starting updating values from API for item " + item.getId() + "...");
            
            // Get the item name:
            try {
                this.refreshName(item);
            } finally {
                progress += 20;
                this.mainFrame.getjProgressBarStatus().setValue(progress);
            }

            // Now get item price info, demand and supply:
            try {
                this.refreshPrices(item);
            } finally {
                progress += 30;
                this.mainFrame.getjProgressBarStatus().setValue(progress);
            }

            // Now get the components infos:
            try {
                this.refreshComponents(item);
            } finally {
                progress += 50;
                this.mainFrame.getjProgressBarStatus().setValue(progress);
            }
            
        } finally {
            this.mainFrame.getjLabelStatus().setText("Values updated");
            this.mainFrame.logMessage("Finished updating values from API.");
            this.mainFrame.getjProgressBarStatus().setValue(this.mainFrame.getjProgressBarStatus().getMaximum());
            ItemTableDataModel model = (ItemTableDataModel) this.mainFrame.getjTableMain().getModel();
            model.fireTableDataChanged();
        }
    }

    private void refreshName(Item item) {
        try {
            if (item.getName() == null || item.getName().isEmpty()) {
                String name = GW2APIHelper.getItemName(item.getId());
                if (name != null && !name.isEmpty()) {
                    item.setName(name);
                }
            }
        } catch (IOException ex) {
            this.mainFrame.logMessage("ERROR getting name for item " + item.getId() + " IOException - Check network connection");
        } catch (ParseException ex) {
            this.mainFrame.logMessage("ERROR getting name for item " + item.getId() + " Error parsing the response from the API");
        } catch (Exception ex) {
            this.mainFrame.logMessage("ERROR getting name for item " + item.getId() + " " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void refreshPrices(Item item) {
        try {
            GW2APIHelper.getSupplyDemandHighBuyLowSellForItem(item);
        } catch (IOException ex) {
            this.mainFrame.logMessage("ERROR getting prices and supply/demand for item " + item.getId() + " IOException - Check network connection");
        } catch (ParseException ex) {
            this.mainFrame.logMessage("ERROR getting prices and supply/demand for item " + item.getId() + " Error parsing the response from the API");
        } catch (Exception ex) {
            this.mainFrame.logMessage("ERROR getting prices and supply/demand for item " + item.getId() + " " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void refreshComponents(Item item) {
        if (item.getComponents() != null && item.getComponents().size() > 0) {
            for (Item compo : item.getComponents()) {
                try {
                    if (compo.getName() == null || compo.getName().isEmpty()) {
                        String compName = GW2APIHelper.getItemName(compo.getId());
                        if (compName != null && !compName.isEmpty()) {
                            compo.setName(compName);
                        }
                    }
                    GW2APIHelper.getSupplyDemandHighBuyLowSellForItem(compo);
                } catch (IOException ex) {
                    this.mainFrame.logMessage("ERROR getting info for component " + compo.getId() + " IOException - Check network connection");
                } catch (ParseException ex) {
                    this.mainFrame.logMessage("ERROR getting info for component " + compo.getId() + " Error parsing the response from the API");
                } catch (Exception ex) {
                    this.mainFrame.logMessage("ERROR getting info for component " + compo.getId() + " " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    public void refreshData() {
        int listCount = this.mainFrame.getDataModel().count();
        if (listCount > 0) {
            int progress = 0;
            this.mainFrame.getjProgressBarStatus().setValue(progress);
            int maximum = listCount * 100;
            this.mainFrame.getjProgressBarStatus().setMaximum(maximum);
            try {
                this.mainFrame.getjLabelStatus().setText("Updating values from API...");
                this.mainFrame.logMessage("Starting updating values from API...");
                // Each list element counts for 100 points of progress:
                // 50 for getting components info
                // 30 for getting price info
                // 20 for getting name
                // We could also compute calculated values here, but I do it in real time for now (not very effective).
                for (Item item : this.mainFrame.getDataModel().getItemList()) {
                    if (this.isAbort()) {
                        this.setAbort(false);
                        break;
                    }
                    // Get the name if needed:
                    try {
                        this.refreshName(item);
                    } finally {
                        progress += 20;
                        this.mainFrame.getjProgressBarStatus().setValue(progress);
                    }

                    // Now get item price info, demand and supply:
                    try {
                        this.refreshPrices(item);
                    } finally {
                        progress += 30;
                        this.mainFrame.getjProgressBarStatus().setValue(progress);
                    }

                    // Now get the components infos:
                    try {
                        this.refreshComponents(item);
                    } finally {
                        progress += 50;
                        this.mainFrame.getjProgressBarStatus().setValue(progress);
                    }

                } // End of the main for loop.
            } finally {
                this.mainFrame.getjLabelStatus().setText("Values updated");
                this.mainFrame.logMessage("Finished updating values from API.");
                this.mainFrame.getjProgressBarStatus().setValue(this.mainFrame.getjProgressBarStatus().getMaximum());
                ItemTableDataModel model = (ItemTableDataModel) this.mainFrame.getjTableMain().getModel();
                model.fireTableDataChanged();
            }
        }
    }

    /**
     * @return the mainFrame
     */
    public JFrameMain getMainFrame() {
        return mainFrame;
    }

    /**
     * @param mainFrame the mainFrame to set
     */
    public void setMainFrame(JFrameMain mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * @return the abort
     */
    public boolean isAbort() {
        return abort;
    }

    /**
     * @param abort the abort to set
     */
    public void setAbort(boolean abort) {
        this.abort = abort;
    }

}
