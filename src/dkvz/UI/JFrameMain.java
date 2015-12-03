
package dkvz.UI;

import dkvz.mysticWatch.*;
import dkvz.model.*;
import dkvz.jobs.*;
import java.awt.Cursor;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;

/**
 *
 * @author william
 */
public class JFrameMain extends javax.swing.JFrame implements CanLogMessages {
    
    private JFrameItem itemFrame = null;
    private JSONDataModel dataModel = null;
    private DataRefresher refresher = null;
    private Thread refreshThread = null;
    private JFrameTransactionLogging logFrame = null;
    private TPTransactionWatcher watcher = null;
    
    /**
     * Creates new form JFrameMain
     */
    public JFrameMain() {
        // Must initialize a data model.
        this.dataModel = new JSONDataModel();
        initComponents();
        this.loadData();
    }
    
    public JFrameMain(JSONDataModel dataModel) {
        this.dataModel = dataModel;
        initComponents();
        this.loadData();
    }
    
    /**
     * 
     * @param message 
     */
    @Override
    public void logMessage(String message) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	String dateToStr = format.format(new Date());
        this.jTextAreaLog.append(dateToStr.concat(" - ").concat(message).concat("\r\n"));
        this.jTextAreaLog.setCaretPosition(this.jTextAreaLog.getText().length());
    }
    
    private void loadData() {
        this.jButtonSecret.setVisible(false);
        try {
            this.logMessage("Loading data...");
            // Try to load the data model from disk.
            this.dataModel.loadData();
            this.logMessage("Loaded data from disk");
        } catch (FileNotFoundException ex) {
            this.logMessage(this.dataModel.getFilename() + " not found. Please add some items to the list.");
        } catch (IOException ex) {
            this.logMessage(this.dataModel.getFilename() + " - Unexpected IO exception, please check permissions on the data file.");
        } catch (ParseException ex) {
            this.logMessage(this.dataModel.getFilename() + " - Could not parse JSON in data file, the file will get overwritten if you add new items.");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelBottom = new javax.swing.JPanel();
        jLabelStatus = new javax.swing.JLabel();
        jProgressBarStatus = new javax.swing.JProgressBar();
        jPanelCenter = new javax.swing.JPanel();
        jSplitPaneCenter = new javax.swing.JSplitPane();
        jScrollPaneCenter = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jScrollPaneLog = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jPanelTop = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jButtonRefreshSelected = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonSecret = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelTrnLogging = new javax.swing.JLabel();
        jButtonTrnLoggingStartSelected = new javax.swing.JButton();
        jButtonTrnLoggingStartAll = new javax.swing.JButton();
        jButtonShowTrnLoggingFrame = new javax.swing.JButton();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mystic Watch");
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanelBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.X_AXIS));

        jLabelStatus.setText("Ready");
        jPanelBottom.add(jLabelStatus);

        jProgressBarStatus.setMaximum(1000);
        jPanelBottom.add(jProgressBarStatus);

        getContentPane().add(jPanelBottom, java.awt.BorderLayout.SOUTH);

        jPanelCenter.setLayout(new java.awt.BorderLayout());

        jSplitPaneCenter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPaneCenter.setResizeWeight(1.0);

        jTableMain.setAutoCreateRowSorter(true);
        jTableMain.setModel(new ItemTableDataModel(this.dataModel));
        jTableMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMainMouseClicked(evt);
            }
        });
        jScrollPaneCenter.setViewportView(jTableMain);

        jSplitPaneCenter.setTopComponent(jScrollPaneCenter);

        jTextAreaLog.setEditable(false);
        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPaneLog.setViewportView(jTextAreaLog);

        jSplitPaneCenter.setBottomComponent(jScrollPaneLog);

        jPanelCenter.add(jSplitPaneCenter, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelTop.setLayout(new javax.swing.BoxLayout(jPanelTop, javax.swing.BoxLayout.X_AXIS));

        jButtonAdd.setText("Add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonAdd);

        jButtonRemove.setText("Remove");
        jButtonRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRemoveActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonRemove);

        jButtonModify.setText("Modify");
        jButtonModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonModify);

        jButtonRefresh.setText("Refresh All");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonRefresh);

        jButtonRefreshSelected.setText("Refresh Selected");
        jButtonRefreshSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshSelectedActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonRefreshSelected);

        jButtonSave.setText("Save Data");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonSave);

        jButtonSecret.setText("Secret Button");
        jButtonSecret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSecretActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonSecret);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanelTop.add(jSeparator1);

        jLabelTrnLogging.setText("Transaction logging:");
        jPanelTop.add(jLabelTrnLogging);

        jButtonTrnLoggingStartSelected.setText("Start for Selected");
        jButtonTrnLoggingStartSelected.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTrnLoggingStartSelectedActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonTrnLoggingStartSelected);

        jButtonTrnLoggingStartAll.setText("Start for All");
        jButtonTrnLoggingStartAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonTrnLoggingStartAllActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonTrnLoggingStartAll);

        jButtonShowTrnLoggingFrame.setText("Show Log Window");
        jButtonShowTrnLoggingFrame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonShowTrnLoggingFrameActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonShowTrnLoggingFrame);

        getContentPane().add(jPanelTop, java.awt.BorderLayout.NORTH);

        jMenuFile.setMnemonic('F');
        jMenuFile.setText("File");

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setMnemonic('E');
        jMenuItemExit.setText("Exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemExit);

        jMenuBarMain.add(jMenuFile);

        setJMenuBar(jMenuBarMain);

        setBounds(0, 0, 1210, 700);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        // Quit application, make checks before exiting.
        this.confirmSaveDataBeforeClosing();
        MysticWatch.exitApplication();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        MysticWatch.exitApplication();
    }//GEN-LAST:event_formWindowClosed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        if (this.itemFrame == null) {
            this.itemFrame = new JFrameItem(this);
            this.itemFrame.setLocationRelativeTo(null);
            this.itemFrame.setVisible(true);
            this.itemFrame.toFront();
        } else {
            this.itemFrame.toFront();
            this.itemFrame.repaint();
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        // Find selected item:
        if (this.jTableMain.getSelectedRow() >= 0) {
            Item item = this.getDataModel().getItemList().get(this.jTableMain.convertRowIndexToModel(this.jTableMain.getSelectedRow()));
            if (this.itemFrame == null) {
                this.itemFrame = new JFrameItem(this, item);
                this.itemFrame.setVisible(true);
                this.itemFrame.setLocationRelativeTo(null);
                this.itemFrame.toFront();
            } else {
                this.itemFrame.toFront();
                this.itemFrame.setItemToModify(item);
                this.itemFrame.reloadItem();
                this.itemFrame.repaint();
            }
        }
    }//GEN-LAST:event_jButtonModifyActionPerformed

    private void jTableMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMainMouseClicked
        if (evt.getClickCount() == 2) {
            this.jButtonModifyActionPerformed(null);
        }
    }//GEN-LAST:event_jTableMainMouseClicked

    public void saveModel() {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            this.dataModel.saveData();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not save data JSON file, IOError.\r\nPlease check file permissions in application directory", "Error saving file", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected exception saving JSON file", "Error saving file", JOptionPane.ERROR_MESSAGE);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        this.saveModel();
    }//GEN-LAST:event_jButtonSaveActionPerformed

    private void confirmSaveDataBeforeClosing() {
        // Ask to save data before quitting:
        int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to save your data?", "Save data", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.saveModel();
        }
    }
    
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.confirmSaveDataBeforeClosing();
    }//GEN-LAST:event_formWindowClosing

    private void jButtonSecretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSecretActionPerformed
        try {
            String APIKey = JOptionPane.showInputDialog(this, "Enter API Key", "API Key Requested", JOptionPane.INFORMATION_MESSAGE);
            if (APIKey != null && !APIKey.isEmpty()) {
                Date limit = new Date();
                //limit.setTime(limit.getTime() - (86400*1000));
                limit.setTime(limit.getTime() - (3600*1000));
                List<Item> hist = GW2APIHelper.getTPBuyHistory(APIKey, limit);
                this.logMessage("Found " + hist.size() + " items.");
                this.logMessage("Items older then " + limit.toString());
                for (Item item : hist) {
                    String comp = "Item: " + item.getId() + " ; Qty: " + item.getQty()
                            + " ; Price: " + item.getHighestBuyOrder() + " ; Time of transaction: " + item.getTransactionEndDate().toString();
                    this.logMessage(comp);
                }
            }
        } catch (IOException ex) {
            this.logMessage(ex.getMessage());
        } catch (org.json.simple.parser.ParseException ex) {
            this.logMessage(ex.getMessage());
        }
    }//GEN-LAST:event_jButtonSecretActionPerformed

    private void refreshAction(boolean selected) {
        // There's a whole bunch of stuff to reset here.
        if (this.refreshThread != null && this.refreshThread.isAlive()) {
            // Cancelling:
            int dialogResult = JOptionPane.showConfirmDialog (null, "Refresh still in progress, try stopping the operation?", "Refresh data", JOptionPane.YES_NO_OPTION);
            if (dialogResult == JOptionPane.YES_OPTION) {
                this.jLabelStatus.setText("Waiting for refresh to stop...");
                this.refresher.setAbort(true);
            }
        } else {
            // Starting thread:
            if (selected) {
                // Refresh only selected item:
                if (this.jTableMain.getSelectedRow() >= 0) {
                    Item item = this.getDataModel().getItemList().get(this.jTableMain.convertRowIndexToModel(this.jTableMain.getSelectedRow()));
                    this.refresher = new DataRefresher(this, item);
                } else {
                    // Nothing selected:
                    JOptionPane.showMessageDialog(this, "You need to select a row first.", "No selection", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            } else {
                // Refresh all.
                this.refresher = new DataRefresher(this);
            }
            this.refreshThread = new Thread(this.refresher);
            this.refreshThread.start();
        }
    }
    
    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        this.refreshAction(false);
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    private void jButtonRefreshSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshSelectedActionPerformed
        this.refreshAction(true);
    }//GEN-LAST:event_jButtonRefreshSelectedActionPerformed

    private void jButtonRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRemoveActionPerformed
        if (this.jTableMain.getSelectedRow() >= 0) {
            Item item = this.getDataModel().getItemList().get(this.jTableMain.convertRowIndexToModel(this.jTableMain.getSelectedRow()));
            this.logMessage("Removing item " + item.getId());
            this.getDataModel().getItemList().remove(this.jTableMain.convertRowIndexToModel(this.jTableMain.getSelectedRow()));
            ItemTableDataModel model = (ItemTableDataModel) this.getjTableMain().getModel();
            model.fireTableDataChanged();
        }
    }//GEN-LAST:event_jButtonRemoveActionPerformed

    private void jButtonShowTrnLoggingFrameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonShowTrnLoggingFrameActionPerformed
        if (this.logFrame == null) {
            if (this.watcher == null) {
                this.logFrame = new JFrameTransactionLogging(this);
            } else {
                this.logFrame = new JFrameTransactionLogging(this, this.watcher);
            }
            this.logFrame.setLocationRelativeTo(null);
            this.logFrame.setVisible(true);
            this.logFrame.toFront();
        } else {
            this.logFrame.toFront();
            this.logFrame.repaint();
        }
    }//GEN-LAST:event_jButtonShowTrnLoggingFrameActionPerformed

    private void jButtonTrnLoggingStartSelectedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTrnLoggingStartSelectedActionPerformed
        int[] selectedRows = this.jTableMain.getSelectedRows();
        if (selectedRows != null && selectedRows.length > 0) {
            for (int i : selectedRows) {
                Item item = this.getDataModel().getItemList().get(this.jTableMain.convertRowIndexToModel(i));
                this.logMessage("Starting TP transaction log for item " + item.getId());
                if (this.watcher == null) {
                    this.watcher = new TPTransactionWatcher();
                }
                this.watcher.addItemToWatch(item);
                this.watcher.setLogger(this);
                if (this.watcher.isAbort()) {
                    this.watcher.startWatcherThread();
                }
            }
        }
    }//GEN-LAST:event_jButtonTrnLoggingStartSelectedActionPerformed

    private void jButtonTrnLoggingStartAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTrnLoggingStartAllActionPerformed
        if (!this.dataModel.getItemList().isEmpty()) {
            if (this.watcher == null) {
                this.watcher = new TPTransactionWatcher();
            }
            this.logMessage("Starting TP transaction log for all items...");
            for (Item i : this.dataModel.getItemList()) {
                this.watcher.addItemToWatch(i);
            }
            if (this.watcher.isAbort()) {
                this.watcher.startWatcherThread();
            }
        }
    }//GEN-LAST:event_jButtonTrnLoggingStartAllActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRefreshSelected;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSecret;
    private javax.swing.JButton jButtonShowTrnLoggingFrame;
    private javax.swing.JButton jButtonTrnLoggingStartAll;
    private javax.swing.JButton jButtonTrnLoggingStartSelected;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTrnLogging;
    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JProgressBar jProgressBarStatus;
    private javax.swing.JScrollPane jScrollPaneCenter;
    private javax.swing.JScrollPane jScrollPaneLog;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPaneCenter;
    private javax.swing.JTable jTableMain;
    private javax.swing.JTextArea jTextAreaLog;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the itemFrame
     */
    public JFrameItem getItemFrame() {
        return itemFrame;
    }

    /**
     * @param itemFrame the itemFrame to set
     */
    public void setItemFrame(JFrameItem itemFrame) {
        this.itemFrame = itemFrame;
    }

    /**
     * @return the dataModel
     */
    public JSONDataModel getDataModel() {
        return dataModel;
    }

    /**
     * @param dataModel the dataModel to set
     */
    public void setDataModel(JSONDataModel dataModel) {
        this.dataModel = dataModel;
    }

    /**
     * @return the jTableMain
     */
    public javax.swing.JTable getjTableMain() {
        return jTableMain;
    }

    /**
     * @return the jProgressBarStatus
     */
    public javax.swing.JProgressBar getjProgressBarStatus() {
        return jProgressBarStatus;
    }

    /**
     * @return the jLabelStatus
     */
    public javax.swing.JLabel getjLabelStatus() {
        return jLabelStatus;
    }

    /**
     * @return the refresher
     */
    public DataRefresher getRefresher() {
        return refresher;
    }

    /**
     * @param refresher the refresher to set
     */
    public void setRefresher(DataRefresher refresher) {
        this.refresher = refresher;
    }

    /**
     * @return the refreshThread
     */
    public Thread getRefreshThread() {
        return refreshThread;
    }

    /**
     * @param refreshThread the refreshThread to set
     */
    public void setRefreshThread(Thread refreshThread) {
        this.refreshThread = refreshThread;
    }

    /**
     * @return the logFrame
     */
    public JFrameTransactionLogging getLogFrame() {
        return logFrame;
    }

    /**
     * @param logFrame the logFrame to set
     */
    public void setLogFrame(JFrameTransactionLogging logFrame) {
        this.logFrame = logFrame;
    }

    /**
     * @return the watcher
     */
    public TPTransactionWatcher getWatcher() {
        return watcher;
    }

    /**
     * @param watcher the watcher to set
     */
    public void setWatcher(TPTransactionWatcher watcher) {
        this.watcher = watcher;
    }
}
