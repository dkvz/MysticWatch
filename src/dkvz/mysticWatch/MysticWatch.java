/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkvz.mysticWatch;

import javax.swing.*;
import dkvz.UI.*;
import dkvz.model.*;

/**
 *
 * @author william
 */
public class MysticWatch {
    
    private static JFrameMain mainFrame;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JSONDataModel dataModel = new JSONDataModel();
        MysticWatch.mainFrame = new JFrameMain(dataModel);
        MysticWatch.mainFrame.setVisible(true);
        MysticWatch.mainFrame.setLocationRelativeTo(null);
    }
    
    public static void exitApplication() {
        System.exit(0);
    }
    
}
