package hw9;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import hw8.CampusPathModel;

/**
 * CampusPathGUI represents a Graphic User Interface of CampusPath features.
 */
public class CampusPathsGUI {
    
    // model of CampusPath
    private CampusPathModel m;
    // main frame of GUI
    private JFrame frame;
    // width of this GUI
    private int frame_width;
    // height of this GUI
    private int frame_height;
    
    /**
     * Builds GUI with passed model.
     * 
     * @param model model of CampusPath to connect with View, and Controller
     * @throw IllegalArgumentException if model is null
     */
    public CampusPathsGUI(CampusPathModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        
        // set model, width, and height of this GUI
        m = model;
        frame_width = 1024;
        frame_height = 768;
        
        // set main frame's default spec
        frame = new JFrame("Campus Path Searcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(frame_width, frame_height));
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        // create view, and controller, and add these to frame
        CampusPathsView v = new CampusPathsView(m);
        CampusPathsController c = new CampusPathsController(m, v);
        v.setPreferredSize(new Dimension(1024, 704));
        c.setPreferredSize(new Dimension(1024, 64));
        frame.add(v);
        frame.add(c);
        
        // pack features of frame and display this in center of screen
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
