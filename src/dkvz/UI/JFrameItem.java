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

    /**
     * Creates new form JFrameItem
     */
    public JFrameItem(JFrameMain mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        this.itemToModify = new Item();
        this.jTextFieldItemID.setEditable(true);
        this.jTextFieldItemID.setEnabled(true);
        this.components = new ArrayList<Item>();
    }

    public JFrameItem(JFrameMain mainFrame, Item itemToModify) {
        this.mainFrame = mainFrame;
        initComponents();
        this.itemToModify = itemToModify;
        this.reloadItem();
    }

    public void reloadItem() {
        this.jTextFieldItemID.setText(Long.toString(itemToModify.getId()));
        this.jTextFieldItemID.setEditable(false);
        this.jTextFieldItemID.setEnabled(false);
        this.components = new ArrayList<Item>();
        if (itemToModify.getComponents() != null && !itemToModify.getComponents().isEmpty()) {
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Item Details");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

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
        jComboBoxCraftItems.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxCraftItemsActionPerformed(evt);
            }
        });

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
        this.closeFrame();
    }//GEN-LAST:event_jButtonCloseActionPerformed

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
        if (this.itemToModify.getId() <= 0) {
            // We're trying to add a new item.
            try {
                long id = Long.parseLong(this.jTextFieldItemID.getText());
                itemToModify.setId(id);
                // Process the components:
                if (this.components.size() > 0) {
                    itemToModify.setComponents(this.components);
                }
                this.mainFrame.getDataModel().addItem(itemToModify);
                ItemTableDataModel model = (ItemTableDataModel) this.mainFrame.getjTableMain().getModel();
                model.fireTableRowsInserted(this.mainFrame.getDataModel().count() - 1, this.mainFrame.getDataModel().count() - 1);
                this.mainFrame.logMessage("Added item " + Long.toString(id) + " - Data Model item count: " + this.mainFrame.getDataModel().count());
            } catch (NumberFormatException ex) {
                // Could not parse the id.
                JOptionPane.showMessageDialog(this, "Could not parse the item ID, make sure it's a number");
                return;
            }
        } else {
            // Edit mode.
            // We always assign the component list.
            // Because itemToModify is a reference it should work on the dataModel...
            itemToModify.setComponents(this.components);
            ItemTableDataModel model = (ItemTableDataModel) this.mainFrame.getjTableMain().getModel();
            //model.fireTableRowsUpdated(0, this.mainFrame.getDataModel().count());
            // Previous code doesn't work with the sorter, so here goes:
            model.fireTableDataChanged();
        }
        this.mainFrame.repaint();
        this.closeFrame();
    }//GEN-LAST:event_jButtonOKActionPerformed

    private void closeFrame() {
        this.mainFrame.setItemFrame(null);
        this.dispose();
    }

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
            compo.setName("");
            compo.setId(id);
            compo.setQty(qty);
            // If component already exists we're just changing the quantity:
            for (int i = 0; i < this.components.size(); i++) {
                // Equals just checks the IDs.
                if (this.components.get(i).equals(compo)) {
                    // Already exists, remove and rebuild combo:
                    // Let's try the built in method:
                    this.jComboBoxCraftItems.removeItem(this.components.get(i));
                    this.components.remove(i);
                    break;
                }
            }
            this.components.add(compo);
            this.jComboBoxCraftItems.addItem(compo);
            // Reset the fields when adding worked.
            this.resetCraftControls();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Could not parse the item ID, make sure it's a number.");
        }
    }//GEN-LAST:event_jButtonCraftAddActionPerformed

    private void resetCraftControls() {
        this.jTextFieldCraftID.setText("");
        this.jTextFieldQty.setText("0");
        this.jButtonCraftRemove.setEnabled(false);
    }

    private void rebuildCombo() {
        this.jComboBoxCraftItems.removeAllItems();
        for (Item item : this.components) {
            this.jComboBoxCraftItems.addItem(item);
        }
        this.repaint();
    }

    private void jButtonCraftRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCraftRemoveActionPerformed
        if (this.jComboBoxCraftItems.getSelectedItem() != null) {
            Item item = (Item) this.jComboBoxCraftItems.getSelectedItem();
            // Remove from the other stuff too:
            for (int i = 0; i < this.components.size(); i++) {
                if (this.components.get(i).equals(item)) {
                    this.components.remove(i);
                    break;
                }
            }
            // Rebuild the model for the comboBox...
            //this.rebuildCombo();
            // Let's try the builtin method, I suppose it's using equals so it should work:
            this.jComboBoxCraftItems.removeItem(item);
            this.resetCraftControls();
        }
    }//GEN-LAST:event_jButtonCraftRemoveActionPerformed

    private void jComboBoxCraftItemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxCraftItemsActionPerformed
        if (this.jComboBoxCraftItems.getSelectedItem() != null) {
            Item item = (Item) this.jComboBoxCraftItems.getSelectedItem();
            this.jTextFieldCraftID.setText(Long.toString(item.getId()));
            this.jTextFieldQty.setText(Integer.toString(item.getQty()));
            this.jButtonCraftRemove.setEnabled(true);
        }
    }//GEN-LAST:event_jComboBoxCraftItemsActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        this.closeFrame();
    }//GEN-LAST:event_formWindowClosed

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

    /**
     * @return the itemToModify
     */
    public Item getItemToModify() {
        return itemToModify;
    }

    /**
     * @param itemToModify the itemToModify to set
     */
    public void setItemToModify(Item itemToModify) {
        this.itemToModify = itemToModify;
    }
}
