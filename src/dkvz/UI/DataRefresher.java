
package dkvz.UI;

import dkvz.model.*;

/**
 *
 * @author william
 */
public class DataRefresher implements Runnable {

    private JFrameMain mainFrame = null;
    
    public DataRefresher(JFrameMain mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
