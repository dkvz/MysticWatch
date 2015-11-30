
package dkvz.UI;

import dkvz.model.*;
import dkvz.jobs.*;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.swing.*;
import java.io.*;
import javax.swing.table.*;
import org.json.simple.parser.ParseException;

/**
 *
 * @author William
 */
public class JFrameTransactionLogging extends javax.swing.JFrame implements CanLogMessages, Observer {

    private JFrameMain mainFrame = null;
    private List<Item> comboList = null;
    private TPTransactionLog currentlyDisplayed = null;
    // I should stop calling those threads, they have nothing to do with the Thread class.
    private TPTransactionWatcher watchThread = null;
    
    // Some methods in here are expecting watchThread to never be null!
    
    /**
     * Creates new form JFrameTransactionLogging
     */
    public JFrameTransactionLogging(JFrameMain mainFrame) {
        initComponents();
        this.mainFrame = mainFrame;
        this.watchThread = new TPTransactionWatcher();
        mainFrame.setWatcher(this.watchThread);
        this.watchThread.addObserver(this);
        // Build the list of transaction logs:
        this.jButtonStartStopLogging.setEnabled(false);
        this.buildComboFromFiles();
    }
    
    public JFrameTransactionLogging(JFrameMain mainFrame, TPTransactionWatcher watchThread) {
        initComponents();
        this.mainFrame = mainFrame;
        this.watchThread = watchThread;
        this.watchThread.addObserver(this);
        // Build the list of transaction logs:
        this.jButtonStartStopLogging.setEnabled(false);
        this.buildComboFromFiles();
    }
    
    public final void buildComboFromFiles() {
        // Looks for .json item transaction state files in the log directory.
        // This is going to need a whole bunch of try catching.
        this.comboList = new ArrayList<Item>();
        this.jComboBoxTransactionFile.removeAllItems();
        File logDir = new File(TPTransactionLog.PATH_TRANSACTION_LOG);
        if (!logDir.exists()) {
            // Attempt to create the directory.
            logDir.mkdir();
        }
        if (logDir.exists() && logDir.isDirectory()) {
            File [] fList = logDir.listFiles();
            if (fList != null) {
                for (File file : fList) {
                    // The file needs to be a JSON file:
                    if (file.getName().toLowerCase().endsWith(TPTransactionLog.STATE_EXTENSION)) {
                        try {
                            // Read the item name and ID from the file.
                            // If the ID isn't there, ignore that file.
                            Item item = TPTransactionLog.readItemFromStateFile(file);
                            this.comboList.add(item);
                            this.jComboBoxTransactionFile.addItem(item);
                        } catch (IOException ex) {
                            this.logMessage("ERROR - Could not read file " + file.getName() + " - Ignored");
                        } catch (ParseException ex) {
                            this.logMessage("ERROR - Could not parse JSON for file " + file.getName() + " - Ignored");
                        }                        
                    }
                }
            }
        } else {
            // We have a big problem: transaction log dir either doesn't exist or isn't a directory.
            JOptionPane.showMessageDialog(null, "The transaction log directory (" + TPTransactionLog.PATH_TRANSACTION_LOG + 
                    ") is NOT a directory or the directory creation failed.\nYou'll have to manually"
                    + " create the directory.", "Error saving file", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void closeFrame() {
        this.dispose();
        this.mainFrame.setLogFrame(null);
    }
    
    @Override
    public void logMessage(String message) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	String dateToStr = format.format(new Date());
        this.jTextAreaLog.append(dateToStr.concat(" - ").concat(message).concat("\r\n"));
        this.jTextAreaLog.setCaretPosition(this.jTextAreaLog.getText().length());
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
     * this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTop = new javax.swing.JPanel();
        jComboBoxTransactionFile = new javax.swing.JComboBox();
        jButtonStartStopLogging = new javax.swing.JButton();
        jButtonStopAllLogging = new javax.swing.JButton();
        jButtonDeleteLog = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();
        jSplitPaneCenter = new javax.swing.JSplitPane();
        jScrollPaneTable = new javax.swing.JScrollPane();
        jTableTransactionLog = new javax.swing.JTable();
        jPanelBottom = new javax.swing.JPanel();
        jScrollPanelLog = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jProgressBarLog = new javax.swing.JProgressBar();
        jMenuBarTop = new javax.swing.JMenuBar();
        jMenuTools = new javax.swing.JMenu();
        jMenuItemManuallyAddItem = new javax.swing.JMenuItem();
        jMenuItemSort = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Transaction Logging");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanelTop.setLayout(new javax.swing.BoxLayout(jPanelTop, javax.swing.BoxLayout.X_AXIS));

        jComboBoxTransactionFile.setModel(new DefaultComboBoxModel<Item>());
        jComboBoxTransactionFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTransactionFileActionPerformed(evt);
            }
        });
        jPanelTop.add(jComboBoxTransactionFile);

        jButtonStartStopLogging.setText("Start Logging");
        jButtonStartStopLogging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartStopLoggingActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonStartStopLogging);

        jButtonStopAllLogging.setText("Stop All Logging");
        jButtonStopAllLogging.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStopAllLoggingActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonStopAllLogging);

        jButtonDeleteLog.setText("Delete Log");
        jButtonDeleteLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonDeleteLogActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonDeleteLog);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonClose);

        getContentPane().add(jPanelTop, java.awt.BorderLayout.NORTH);

        jSplitPaneCenter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPaneCenter.setResizeWeight(1.0);

        jTableTransactionLog.setAutoCreateRowSorter(true);
        jTableTransactionLog.setModel(new DefaultTableModel());
        jScrollPaneTable.setViewportView(jTableTransactionLog);

        jSplitPaneCenter.setTopComponent(jScrollPaneTable);

        jPanelBottom.setLayout(new java.awt.BorderLayout());

        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPanelLog.setViewportView(jTextAreaLog);

        jPanelBottom.add(jScrollPanelLog, java.awt.BorderLayout.CENTER);
        jPanelBottom.add(jProgressBarLog, java.awt.BorderLayout.SOUTH);

        jSplitPaneCenter.setBottomComponent(jPanelBottom);

        getContentPane().add(jSplitPaneCenter, java.awt.BorderLayout.CENTER);

        jMenuTools.setMnemonic('T');
        jMenuTools.setText("Tools");

        jMenuItemManuallyAddItem.setMnemonic('M');
        jMenuItemManuallyAddItem.setText("Manually add item...");
        jMenuItemManuallyAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemManuallyAddItemActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemManuallyAddItem);

        jMenuItemSort.setText("Sort the log list");
        jMenuItemSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSortActionPerformed(evt);
            }
        });
        jMenuTools.add(jMenuItemSort);

        jMenuBarTop.add(jMenuTools);

        setJMenuBar(jMenuBarTop);

        setSize(new java.awt.Dimension(775, 622));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.closeFrame();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.closeFrame();
    }//GEN-LAST:event_formWindowClosed

    private void jComboBoxTransactionFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTransactionFileActionPerformed
        // We need to load up the model for the table.
        if (this.jComboBoxTransactionFile.getSelectedItem() != null) {
            this.logMessage("Loading TP transaction log...");
            Cursor initCursor = this.getCursor();
            try {
                this.jButtonStartStopLogging.setEnabled(false);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Item item = (Item)this.jComboBoxTransactionFile.getSelectedItem();
                // Load the events table:
                TPTransactionLog tLog = new TPTransactionLog(item.getId());
                if (item.getName() != null && !item.getName().isEmpty()) {
                    tLog.setName(item.getName());
                }
                DataLoadingRunnable tpLogLoader = new DataLoadingRunnable(tLog, this);
                Thread t = new Thread(tpLogLoader);
                this.jProgressBarLog.setMaximum(110);
                this.jProgressBarLog.setValue(0);
                t.start();
                while (!tLog.isLoaded()) {
                    // This loop creates a lockdown unless we yield:
                    Thread.yield();
                    this.jProgressBarLog.setValue(tLog.getProgress());
                }
                // The file is loaded.
                // Let's create the data model for the table:
                TPLogTableDataModel dataModel = new TPLogTableDataModel(tLog);
                this.currentlyDisplayed = tLog;
                this.jTableTransactionLog.setModel(dataModel);
                this.jProgressBarLog.setValue(110);
                this.jButtonStartStopLogging.setEnabled(true);
                if (this.isLoggingStartedForItem(item.getId())) {
                    // Start logging becomes stop logging here.
                    this.toggleStopLogging();
                } else {
                    this.toggleStartLogging();
                }
            } finally {
                if (initCursor != null) {
                    this.setCursor(initCursor);
                } else {
                    this.setCursor(Cursor.getDefaultCursor());
                }
            }
        }
    }//GEN-LAST:event_jComboBoxTransactionFileActionPerformed

    private void toggleStopLogging() {
        // Set text to stop logging.
        this.jButtonStartStopLogging.setText("Stop Logging");
        this.jButtonStartStopLogging.setEnabled(true);
    }
    
    private void toggleStartLogging() {
        // Set text to stop logging.
        this.jButtonStartStopLogging.setText("Start Logging");
        this.jButtonStartStopLogging.setEnabled(true);
    }
    
    private boolean isLoggingStartedForItem(long itemId) {
        if (!this.watchThread.isEmpty()) {
            if (this.getWatchThread().contains(itemId)) {
                return true;
            }
        }
        return false;
    }
    
    private void jMenuItemManuallyAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemManuallyAddItemActionPerformed
        // I'm going to do this with a dialog box:
        String strItemID = JOptionPane.showInputDialog(this, "Enter the item ID:", "Enter item ID", JOptionPane.INFORMATION_MESSAGE);
        if (strItemID != null && !strItemID.isEmpty()) {
            try {
                long itemId = Long.parseLong(strItemID);
                Item item = new Item();
                item.setId(itemId);
                // Let's get the name.
                Cursor initCursor = this.getCursor();
                try {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    String name = GW2APIHelper.getItemName(itemId);
                    item.setName(name);
                } catch (Exception ex) {
                    this.logMessage("Could not get the name of item ".concat(Long.toString(itemId)));
                } finally {
                    if (initCursor != null) {
                        this.setCursor(initCursor);
                    } else {
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                }
                this.comboList.add(item);
                this.jComboBoxTransactionFile.addItem(item);
                // We should set this item as the active log:
                this.jComboBoxTransactionFile.setSelectedItem(item);
                // I need to check if this fires ActionPerformed.
                // Yes it does.
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Could not add item, invalid item ID.", "Error adding item", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_jMenuItemManuallyAddItemActionPerformed
    
    private void jButtonStartStopLoggingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartStopLoggingActionPerformed
        // This frame is actually responsible for adding the events logging started and logging stopped.
        // I mean mainFrame can be responsible of that too but this button press may have to log those events.
        if (this.currentlyDisplayed != null) {
            if (this.isLoggingStartedForItem(this.currentlyDisplayed.getItemId())) {
                this.jButtonStartStopLogging.setEnabled(false);
                // We need to stop the logging for this item.
                this.watchThread.removeItemToWatch(this.currentlyDisplayed.getItemId());
                // Add the logging stopped event:
                TPEvent event = new TPEvent(this.currentlyDisplayed.getItemId(), TPEvent.EVENT_TYPE_LOGGING_STOPPED);
                this.currentlyDisplayed.getEventListRead().add(event);
                // Refresh the datatable:
                this.refreshDataTable();
                try {
                    TPTransactionLog.appendToLog(event);
                } catch (IOException ex) {
                    this.logMessage("ERROR writing the logging stopped event for " + this.currentlyDisplayed.getItemId() + " - IO Exception");
                } catch (SecurityException ex) {
                    this.logMessage("ERROR writing the logging stopped event for " + this.currentlyDisplayed.getItemId() + " - Security exception, check file permissions");
                }
                this.toggleStartLogging();
            } else {
                this.jButtonStartStopLogging.setEnabled(false);
                // Start the logging for this item.
                this.watchThread.addItemToWatch(this.currentlyDisplayed.getItemId(), this.currentlyDisplayed.getName());
                // This may have to actually start the thread itself.
                if (this.watchThread.isAbort()) {
                    // Start the thread:
                    this.watchThread.startWatcherThread();
                }
                // Add the logging started event:
                TPEvent event = new TPEvent(this.currentlyDisplayed.getItemId(), TPEvent.EVENT_TYPE_LOGGING_STARTED);
                this.currentlyDisplayed.getEventListRead().add(event);
                // Refresh the datatable:
                this.refreshDataTable();
                try {
                    TPTransactionLog.appendToLog(event);
                } catch (IOException ex) {
                    this.logMessage("ERROR writing the logging started event for " + this.currentlyDisplayed.getItemId() + " - IO Exception");
                } catch (SecurityException ex) {
                    this.logMessage("ERROR writing the logging started event for " + this.currentlyDisplayed.getItemId() + " - Security exception, check file permissions");
                }
                this.toggleStopLogging();
            }
        }
    }//GEN-LAST:event_jButtonStartStopLoggingActionPerformed

    private void jButtonStopAllLoggingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStopAllLoggingActionPerformed
        this.watchThread.removeAllItemsToWatch();
        // This will also set the abort boolean to true and effectively end the thread eventually.
        this.toggleStartLogging();
    }//GEN-LAST:event_jButtonStopAllLoggingActionPerformed

    private void jButtonDeleteLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonDeleteLogActionPerformed
        if (this.currentlyDisplayed != null) {
            // We could probably do with a "Are you sure?" thing:
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure? This will delete the related files.", "Delete TP transactions log", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                Cursor initCursor = this.getCursor();
                try {
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    // We need to remove it from the thread first. We should maybe wait the request interval duration to make sure
                    // we're not writing more to that log in the meantime but whatever.
                    this.watchThread.removeItemToWatch(this.currentlyDisplayed.getItemId());
                    // Remove the files:
                    String base = TPTransactionLog.PATH_TRANSACTION_LOG.concat(File.separator).concat(Long.toString(this.currentlyDisplayed.getItemId()));
                    File state = new File(base.concat(TPTransactionLog.STATE_EXTENSION));
                    File log = new File(base.concat(TPTransactionLog.LOG_EXTENSION));                
                    if (state.exists()) {
                        boolean success = state.delete();
                        if (success) {
                            this.logMessage("Deleted state file for item " + this.currentlyDisplayed.getItemId());
                        } else {
                            this.logMessage("ERROR - Could not delete state file for item " + this.currentlyDisplayed.getItemId());
                        }
                    }
                    if (log.exists()) {
                        boolean success = log.delete();
                        if (success) {
                            this.logMessage("Deleted transactions log file for item " + this.currentlyDisplayed.getItemId());
                        } else {
                            this.logMessage("ERROR - Could not delete transactions log file for item " + this.currentlyDisplayed.getItemId());
                        }
                    }
                    // Update the combo and the list:
                    this.buildComboFromFiles();
                    this.currentlyDisplayed = null;
                    // TODO: what happens if this was the selected event? I may have to clean the datatable.
                    if (this.jComboBoxTransactionFile.getItemCount() == 0) {
                        // Get the dataModel from the table, if it's a TPLogTableDataModel instance, empty the list:
                        TableModel tModel = this.jTableTransactionLog.getModel();
                        if (tModel instanceof TPLogTableDataModel) {
                            TPLogTableDataModel model = (TPLogTableDataModel) tModel;
                            model.clear();
                            this.refreshDataTable();
                        }
                    }
                } finally {
                    this.setCursor(initCursor);
                }
            }
        }
    }//GEN-LAST:event_jButtonDeleteLogActionPerformed

    private void jMenuItemSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSortActionPerformed
        // TODO I think I may have to implement comparable or something like that for this.
        
    }//GEN-LAST:event_jMenuItemSortActionPerformed

    @Override
    public void update(Observable o, Object tranLog) {
        // The thread that's updating listings can send update events to this frame. It's 
        // supposed to provide a TPTransactionLog object with a limited list of events
        // that are the new events only. If that item is the one currently displayed on the
        // form, we can update the data model and show the changes.
        if (tranLog instanceof TPTransactionLog) {
            if (this.currentlyDisplayed != null) {
                TPTransactionLog tpLog = (TPTransactionLog)tranLog;
                if (this.currentlyDisplayed.equals(tpLog)) {
                    this.currentlyDisplayed.getEventListRead().addAll(tpLog.getEventListRead());
                    // Fire a model update event for the datatable:
                    this.refreshDataTable();
                }
            }
        }
    }
    
    public void refreshDataTable() {
        TPLogTableDataModel model = (TPLogTableDataModel) this.jTableTransactionLog.getModel();
        model.fireTableDataChanged();
        // We should also scroll to the bottom, I don't know how to do that.
        // Let's try this:
        JScrollBar vertical = this.jScrollPaneTable.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
        // I don't know if I need to repaint the frame. Trying without.
    }

    /**
     * @return the currentlyDisplayed
     */
    public TPTransactionLog getCurrentlyDisplayed() {
        return currentlyDisplayed;
    }
    
    /**
     * @return the watchThread
     */
    public TPTransactionWatcher getWatchThread() {
        return watchThread;
    }
    
    /**
     * @param watchThread the watchThread to set
     */
    public void setWatchThread(TPTransactionWatcher watchThread) {
        this.watchThread = watchThread;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonDeleteLog;
    private javax.swing.JButton jButtonStartStopLogging;
    private javax.swing.JButton jButtonStopAllLogging;
    private javax.swing.JComboBox jComboBoxTransactionFile;
    private javax.swing.JMenuBar jMenuBarTop;
    private javax.swing.JMenuItem jMenuItemManuallyAddItem;
    private javax.swing.JMenuItem jMenuItemSort;
    private javax.swing.JMenu jMenuTools;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JProgressBar jProgressBarLog;
    private javax.swing.JScrollPane jScrollPaneTable;
    private javax.swing.JScrollPane jScrollPanelLog;
    private javax.swing.JSplitPane jSplitPaneCenter;
    private javax.swing.JTable jTableTransactionLog;
    private javax.swing.JTextArea jTextAreaLog;
    // End of variables declaration//GEN-END:variables


}
