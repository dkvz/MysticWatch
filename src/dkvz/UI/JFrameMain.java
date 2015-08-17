
package dkvz.UI;

import dkvz.mysticWatch.*;
import dkvz.model.*;
import java.awt.Cursor;
import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;

/**
 *
 * @author william
 */
public class JFrameMain extends javax.swing.JFrame {
    
    private JFrameItem itemFrame = null;
    private JSONDataModel dataModel = null;
    
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
    public void logMessage(String message) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	String dateToStr = format.format(new Date());
        this.jTextAreaLog.append(dateToStr.concat(" - ").concat(message).concat("\r\n"));
        this.jTextAreaLog.setCaretPosition(this.jTextAreaLog.getText().length());
    }
    
    private void loadData() {
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
        jScrollPaneCenter = new javax.swing.JScrollPane();
        jTableMain = new javax.swing.JTable();
        jScrollPaneLog = new javax.swing.JScrollPane();
        jTextAreaLog = new javax.swing.JTextArea();
        jPanelTop = new javax.swing.JPanel();
        jButtonAdd = new javax.swing.JButton();
        jButtonRemove = new javax.swing.JButton();
        jButtonModify = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();
        jButtonSave = new javax.swing.JButton();
        jButtonSecret = new javax.swing.JButton();
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mystic Watch");
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanelBottom.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanelBottom.setLayout(new javax.swing.BoxLayout(jPanelBottom, javax.swing.BoxLayout.X_AXIS));

        jLabelStatus.setText("Ready");
        jPanelBottom.add(jLabelStatus);
        jPanelBottom.add(jProgressBarStatus);

        getContentPane().add(jPanelBottom, java.awt.BorderLayout.SOUTH);

        jPanelCenter.setLayout(new java.awt.BorderLayout());

        jTableMain.setAutoCreateRowSorter(true);
        jTableMain.setModel(new ItemTableDataModel(this.dataModel));
        jTableMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMainMouseClicked(evt);
            }
        });
        jScrollPaneCenter.setViewportView(jTableMain);

        jPanelCenter.add(jScrollPaneCenter, java.awt.BorderLayout.CENTER);

        jTextAreaLog.setEditable(false);
        jTextAreaLog.setColumns(20);
        jTextAreaLog.setRows(5);
        jScrollPaneLog.setViewportView(jTextAreaLog);

        jPanelCenter.add(jScrollPaneLog, java.awt.BorderLayout.SOUTH);

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
        jPanelTop.add(jButtonRemove);

        jButtonModify.setText("Modify");
        jButtonModify.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonModifyActionPerformed(evt);
            }
        });
        jPanelTop.add(jButtonModify);

        jButtonRefresh.setText("Refresh Values");
        jPanelTop.add(jButtonRefresh);

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
        MysticWatch.exitApplication();
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        MysticWatch.exitApplication();
    }//GEN-LAST:event_formWindowClosed

    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        if (this.itemFrame == null) {
            this.itemFrame = new JFrameItem(this);
            this.itemFrame.setVisible(true);
            this.itemFrame.setLocationRelativeTo(null);
            this.itemFrame.toFront();
        } else {
            this.itemFrame.toFront();
            this.itemFrame.repaint();
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    private void jButtonModifyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonModifyActionPerformed
        // Find selected item:
        if (this.jTableMain.getSelectedRow() >= 0) {
            Item item = this.getDataModel().getItemList().get(this.jTableMain.convertRowIndexToView(this.jTableMain.getSelectedRow()));
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // Ask to save data before quitting:
        int dialogResult = JOptionPane.showConfirmDialog (null, "Do you want to save your data?", "Save data", JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            this.saveModel();
        }
    }//GEN-LAST:event_formWindowClosing

    private void jButtonSecretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSecretActionPerformed
        try {
            Item item = new Item();
            item.setId(31065);
            GW2APIHelper.getSupplyDemandHighBuyLowSellForItem(item);
            this.logMessage("Item highest buy order: " + item.getHighestBuyOrder());
            this.logMessage("Item lowest sell order: " + item.getLowestSellOrder());
            this.logMessage("Item demand: " + item.getDemand());
            this.logMessage("Item supply: " + item.getOffer());
        } catch (IOException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(JFrameMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonSecretActionPerformed

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRemove;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JButton jButtonSecret;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JMenuBar jMenuBarMain;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JProgressBar jProgressBarStatus;
    private javax.swing.JScrollPane jScrollPaneCenter;
    private javax.swing.JScrollPane jScrollPaneLog;
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
}
