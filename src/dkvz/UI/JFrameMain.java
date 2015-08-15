
package dkvz.UI;

import dkvz.mysticWatch.*;
import dkvz.model.*;
import java.io.*;
import java.lang.String.*;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    }
    
    private void loadData() {
        try {
            this.logMessage("Loading data...");
            // Try to load the data model from disk.
            this.dataModel.loadData();
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
        jMenuBarMain = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
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

        jTableMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
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
        jPanelTop.add(jButtonModify);

        jButtonRefresh.setText("Refresh");
        jPanelTop.add(jButtonRefresh);

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

        pack();
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

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonModify;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JButton jButtonRemove;
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
}
