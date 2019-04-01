package hw9;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import hw8.CampusPathModel;

/**
 * CampusPathController represents a controller of CampusPathGUI
 */
public class CampusPathsController extends JPanel {

    // model of CampusPath
    private CampusPathModel m;
    // view of CampusPath
    private CampusPathsView v;
    // map of name of buildings (short to full name)
    private Map<String, String> buildnames;
    // JLabels to display starting building
    private JLabel fromBuild;
    // JLabels to display ending building
    private JLabel toBuild;
    // JLabels to display distance
    private JLabel distance;
    // JComboBox (from) that stores building names
    private JComboBox<String> fromBuildBox;
    // JComboBox (to) that stores building names
    private JComboBox<String> toBuildBox;
    // help JButtons to use in this controller
    private JButton help;
    // findPath JButtons to use in this controller
    private JButton findPath;
    // reset JButtons to use in this controller
    private JButton reset;
    // String of helper message
    private String helpMessage;

    /**
     * Builds controller with CampusPathModel, and CampusPathView
     * 
     * @param m model from CampusPathModel
     * @param v view from CampusPathView
     * @throw IllegalArgumetException if m, or v is null
     */
    public CampusPathsController(CampusPathModel m, CampusPathsView v) {
        if (m == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }

        if (v == null) {
            throw new IllegalArgumentException("View cannot be null");
        }

        this.m = m;
        this.v = v;

        // add building selection panel, button panel
        // and, distance panel to control object
        this.add(buildHelpPanel());
        this.add(buildSelectionPanel());
        this.add(buildButtonPanel());
        this.add(buildDistancePanel());

        // set default
        reset();
    }
    
    /**
     * Creates JPanel that contains features of providing
     * help button and information on the button.
     * 
     * @return panel_help created JPanel with added features
     */
    public JPanel buildHelpPanel() {
        // create panel_help that contains help button to provide instruction to user
        JPanel panel_help = new JPanel();
        help = new JButton("Help");
        help.addActionListener(new MainActionListener());
        help.setToolTipText("Click this to see how to use Campus Path Searcher");
        panel_help.add(help);
        helpMessage = "To find the shortest path: " + 
                "\n\tSelect two buildings and press button 'find Shortest Path'"
                + "\n\nTo reset: " + "\n\t" + "press button 'Reset'";
        
        return panel_help;
    }
    
    /**
     * Creates JPanel that contains features of providing
     * building selection boxes and labels for them
     * 
     * @return panel_help created JPanel with added features
     */
    public JPanel buildSelectionPanel() {
        // create panel_select that contains two selection boxes (ComboBox)
        JPanel panel_select = new JPanel(new GridBagLayout());

        // create Labels, and ComboBoxes that represent user selection of two buildings
        buildnames = new HashMap<String, String>(this.m.getBuildNames());
        fromBuild = new JLabel("(R) From:");
        toBuild = new JLabel("(G) To:");
        fromBuildBox = new JComboBox<String>();
        toBuildBox = new JComboBox<String>();

        // add names (Abbreviated name - Full name) of buildings to JComboBoxes
        fromBuildBox.addItem("--------------- Select Starting Building ---------------");
        toBuildBox.addItem("--------------- Select Ending Building ---------------");
        for (String name : new TreeSet<String>(buildnames.keySet())) {
            fromBuildBox.addItem(name + " - " + buildnames.get(name));
            toBuildBox.addItem(name + " - " + buildnames.get(name));
        }

        fromBuildBox.addActionListener(new MainActionListener());
        toBuildBox.addActionListener(new MainActionListener());
        fromBuildBox.setActionCommand("from building selected");
        toBuildBox.setActionCommand("to building selected");

        // add JLabels, and JComboBoxes of selection of buildings with format
        panel_select.add(fromBuild, new GridBagConstraints(
                1, 1, 3, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        panel_select.add(fromBuildBox, new GridBagConstraints(
                4, 1, 5, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        panel_select.add(toBuild, new GridBagConstraints(
                1, 2, 3, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        panel_select.add(toBuildBox, new GridBagConstraints(
                4, 2, 5, 1, 0.0, 0.0, 10, 0, new Insets(0, 0, 0, 0), 0, 0));
        
        return panel_select;
    }
    
    /**
     * Creates JPanel that contains features of providing
     * button for user to use and functions on the button.
     * 
     * @return panel_help created JPanel with added features
     */
    public JPanel buildButtonPanel() {
     // create panel_button that contains two buttons
        JPanel panel_button = new JPanel(new GridLayout(2, 1));

        // add buttons to panel
        findPath = new JButton("Find Shortest Path");
        reset = new JButton("Reset");

        // add ActionListner to buttons
        findPath.addActionListener(new MainActionListener());
        reset.addActionListener(new MainActionListener());

        // add mouse-over tool-tip to buttons
        findPath.setToolTipText("Find shortest path of two chosen building");
        reset.setToolTipText("Clear map, and set building options default");

        // add buttons to panel of buttons
        panel_button.add(findPath);
        panel_button.add(reset);
        
        return panel_button;
    }
    
    /**
     * Creates JPanel that contains features of displaying
     * distance and label of it.
     * 
     * @return panel_help created JPanel with added features
     */
    public JPanel buildDistancePanel() {
     // create distance panel that displays total distance between buildings
        JPanel panel_distance = new JPanel(new GridLayout(2, 1));
        JLabel label_distance = new JLabel("Distance:");

        distance = new JLabel();
        panel_distance.setToolTipText("Displays distance between two chosen buildings");
        panel_distance.add(label_distance);
        panel_distance.add(distance);
        
        return panel_distance;
    }
    
    /**
     * reset to default setting of this controller
     */
    public void reset() {
        fromBuildBox.setSelectedItem(
                "--------------- Select Starting Building ---------------");
        toBuildBox.setSelectedItem(
                "--------------- Select Ending Building ---------------");
        distance.setText("");
        findPath.setEnabled(false);
    }

    /**
     * Action Listener class of this controller. Based on user's usage, let
     * controller find the shortest path, and display it, or show dialog to provide
     * instruction of GUI, or make GUI default setup.
     */
    private class MainActionListener implements ActionListener {

        /**
         * Called when an action occurs by the user.
         * 
         * @param e an action event
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            String whatAction = e.getActionCommand();

            String fromBuild = fromBuildBox.getSelectedItem().toString();
            String toBuild = toBuildBox.getSelectedItem().toString();

            // user clicked 'Find Shortest Path' button
            if (whatAction.equals("Find Shortest Path")) {
                // find shortest path, and display distance
                double dst = v.getShortestPath(fromBuild.substring(0, 
                        fromBuild.indexOf("-") - 1), toBuild.substring(0, 
                                toBuild.indexOf("-") - 1));
                distance.setText(String.format("%.0f", dst) + " feet");

                // user clicked 'Help' to get instruction of GUI
            } else if (whatAction.equals("Help")) {
                JOptionPane.showMessageDialog(null, helpMessage, 
                        "How to use Campus Path Searcher",
                        JOptionPane.INFORMATION_MESSAGE);
                // user changes selection of buildings
            } else if (whatAction.equals("from building selected") || 
                    whatAction.equals("to building selected")) {
                if ((fromBuildBox.getSelectedItem() != 
                        "--------------- Select Starting Building ---------------")
                        && toBuildBox.getSelectedItem() != 
                        ("--------------- Select Ending Building ---------------")) {
                    
                    findPath.setEnabled(true);
                } else if ((fromBuildBox.getSelectedItem() == 
                        "--------------- Select Starting Building ---------------")
                        || toBuildBox.getSelectedItem() == 
                        ("--------------- Select Ending Building ---------------")) {
                    
                    findPath.setEnabled(false);
                }
                distance.setText("");
                v.reset();
                // user clicked reset button
            } else {
                v.reset();
                reset();
            }
        }
    }
}
