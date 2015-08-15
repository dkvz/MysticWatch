
package dkvz.UI;

import dkvz.model.*;
import javax.swing.*;
import java.util.*;


/**
 *
 * @author william
 */
public class JFrameItem extends javax.swing.JFrame {

    private JFrameMain mainFrame = null;
    private Item itemToModify = null;
    private ArrayList<Item> components = null;
    private ArrayList<Item> toRemove = null;
    
    /**
     * Creates new form JFrameItem
     */
    public JFrameItem(JFrameMain mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        this.itemToModify = new Item();
        this.jTextFieldItemID.setEditable(true);
        this.jTextFieldItemID.setEnabled(true);
        this.toRemove = new ArrayList<Item>();
        this.components = new ArrayList<Item>();
    }
    
    public JFrameItem(JFrameMain mainFrame, Item itemToModify) {
        this.mainFrame = mainFrame;
        initComponents();
        this.itemToModify = itemToModify;
        this.jTextFieldItemID.setText(Long.toString(itemToModify.getId()));
        this.jTextFieldItemID.setEditable(false);
        this.jTextFieldItemID.setEnabled(false);
        this.components = new ArrayList<Item>();
        this.toRemove = new ArrayList<Item>();
        if (itemToModify.getComponents() != null && !itemToModify.getComponents().isEmpty()){
            for (Item item : itemToModify.getComponents()) {
                this.components.add(item);
                this.jComboBoxCraftItems.addItem(item);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelTop = new javax.swing.JPanel();
        jLabelItemID = new javax.swing.JLabel();
        jTextFieldItemID = new javax.swing.JTextField();
        jPanelCenter = new javax.swing.JPanel();
        jLabelCraftID = new javax.swing.JLabel();
        jLabelCraftQty = new javax.swing.JLabel();
        jTextFieldCraftID = new javax.swing.JTextField();
        jButtonCraftAdd = new javax.swing.JButton();
        jButtonCraftRemove = new javax.swing.JButton();
        jComboBoxCraftItems = new javax.swing.JComboBox();
        jTextFieldQty = new javax.swing.JTextField();
        jPanelBottom = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();
        jButtonClose = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanelTop.setBorder(javax.swing.BorderFactory.createTitledBorder("Add item to list"));

        jLabelItemID.setText("Item ID:");
        jPanelTop.add(jLabelItemID);

        jTextFieldItemID.setColumns(12);
        jPanelTop.add(jTextFieldItemID);

        getContentPane().add(jPanelTop, java.awt.BorderLayout.NORTH);

        jPanelCenter.setBorder(javax.swing.BorderFactory.createTitledBorder("Components required to craft item"));

        jLabelCraftID.setText("Item ID");

        jLabelCraftQty.setText("Quantity");

        jButtonCraftAdd.setText("Add/Edit");
        jButtonCraftAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCraftAddActionPerformed(evt);
            }
        });

        jButtonCraftRemove.setText("Remove");
        jButtonCraftRemove.setEnabled(false);
        jButtonCraftRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCraftRemoveActionPerformed(evt);
            }
        });

        jComboBoxCraftItems.setModel(new DefaultComboBoxModel<Item>());

        javax.swing.GroupLayout jPanelCenterLayout = new javax.swing.GroupLayout(jPanelCenter);
        jPanelCenter.setLayout(jPanelCenterLayout);
        jPanelCenterLayout.setHorizontalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jComboBoxCraftItems, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelCenterLayout.createSequentialGroup()
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabelCraftID, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                            .addComponent(jTextFieldCraftID))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelCenterLayout.createSequentialGroup()
                                .addComponent(jLabelCraftQty)
                                .addGap(0, 52, Short.MAX_VALUE))
                            .addComponent(jTextFieldQty))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCraftAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonCraftRemove)))
                .addContainerGap())
        );
        jPanelCenterLayout.setVerticalGroup(
            jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCenterLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCraftID)
                    .addComponent(jLabelCraftQty))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelCenterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCraftID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCraftAdd)
                    .addComponent(jButtonCraftRemove)
                    .addComponent(jTextFieldQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBoxCraftItems, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        getContentPane().add(jPanelCenter, java.awt.BorderLayout.CENTER);

        jPanelBottom.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOK.setText("OK");
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });
        jPanelBottom.add(jButtonOK);

        jButtonClose.setText("Close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });
        jPanelBottom.add(jButtonClose);

        getContentPane().add(jPanelBottom, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        if (this.itemToModify.getId() < 0) {
            // We're trying to add a new item.
            try {
                long id = Long.parseLong(this.jTextFieldItemID.getText());
                itemToModify.setId(id);
                // TODO Process the components, also the components to remove:
                
                
                this.mainFrame.getDataModel().addItem(itemToModify);
            } catch (NumberFormatException ex) {
                // Could not parse the id.
                JOptionPane.showMessageDialog(this, "Could not parse the item ID, make sure it's a number");
            }
        } else {
            // Edit mode.
            
        }
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void jButtonCraftAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCraftAddActionPerformed
        // Must check that qty is not 0, and that we can parse the id.
        // If the item id is already in the list we're replacing that item ("edit mode").
        try {
            long id = Long.parseLong(this.jTextFieldCraftID.getText());
            int qty = Integer.parseInt(this.jTextFieldQty.getText());
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "You're trying to add a quantity of 0.");
                return;
            }
            Item compo = new Item();
            compo.setComponent(true);
            compo.setId(id);
            compo.setQty(qty);
            this.components.add(compo);
            this.jComboBoxCraftItems.addItem(compo);
            // Reset the fields when adding worked.
            this.jTextFieldCraftID.setText("");
            this.jTextFieldQty.setText("0");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Could not parse the item ID, make sure it's a number.");
        }
    }//GEN-LAST:event_jButtonCraftAddActionPerformed

    private void jButtonCraftRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCraftRemoveActionPerformed
        if (this.jComboBoxCraftItems.getSelectedItem() != null) {
            Item item = (Item)this.jComboBoxCraftItems.getSelectedItem();
            this.toRemove.add(item);
            // TODO Remove from the other stuff too:
            
        }
    }//GEN-LAST:event_jButtonCraftRemoveActionPerformed

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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonCraftAdd;
    private javax.swing.JButton jButtonCraftRemove;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JComboBox jComboBoxCraftItems;
    private javax.swing.JLabel jLabelCraftID;
    private javax.swing.JLabel jLabelCraftQty;
    private javax.swing.JLabel jLabelItemID;
    private javax.swing.JPanel jPanelBottom;
    private javax.swing.JPanel jPanelCenter;
    private javax.swing.JPanel jPanelTop;
    private javax.swing.JTextField jTextFieldCraftID;
    private javax.swing.JTextField jTextFieldItemID;
    private javax.swing.JTextField jTextFieldQty;
    // End of variables declaration//GEN-END:variables
}