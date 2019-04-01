package hw9;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;

import hw8.CampusPathModel;
import hw8.Coordinate;

/**
 * CampusPathView represents a view of CampusPathGUI
 */
public class CampusPathsView extends JPanel {

    // model of CampusPath
    private CampusPathModel m;
    // file location of campus map image
    private String CampusMapImage = "src/hw8/data/campus_map.jpg";
    // buffered image to read
    private BufferedImage image;
    
    // a map to store shortest path 
    private Map<Coordinate, Double> paths;
    // a list of coordinates of shortest path
    private List<Coordinate> coordinates;
    // width of image
    private int width;
    // height of image
    private int height;
    // ratio of width to display path to image correctly
    private double rat_width;
    // ratio of height to display path to image correctly
    private double rat_height;
    // a list of building names that are displayed
    private List<String> buildnames;

    /**
     * Builds a view of CampusPath to use in GUI
     * @param m model of CampusPath
     * @throw new IllegalArgumentException if model is null
     */
    public CampusPathsView(CampusPathModel model) {
        if (model == null) {
            throw new IllegalArgumentException("Model cannot be null");
        }
        
        // read image file to display
        try {
            image = ImageIO.read(new File(CampusMapImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.m = model;
        this.paths = null;

        // set width, and height of view and calculate ratio of image
        width = 1024;
        height = 704;
        rat_width = width / image.getWidth() * 1.0;
        rat_height = height / image.getHeight() * 1.0;
    }

    /**
     * Set this view to default setting
     */
    public void reset() {
        paths = null;
        repaint();
    }

    /**
     * finds the shortest path between two buildings using model's 
     * findShortestPath method, and repaint to display paths
     * 
     * @param from name of building starting from
     * @param to name of building heading to
     * @modify paths fills paths with coordinates and Double 
     * @modify coordinates fills coordinates with keys of paths
     * @return distance of the shortest path
     */
    public Double getShortestPath(String from, String to) {
        this.buildnames = new ArrayList<String>();
        buildnames.add(from);
        buildnames.add(to);
        
        Coordinate fromLoca = m.getLocation(from);
        Coordinate toLoca = m.getLocation(to);this.paths = m.findShortestPath(fromLoca, toLoca);
        this.coordinates = new ArrayList<Coordinate>(this.paths.keySet());
        
        repaint();
        return paths.get(coordinates.get(coordinates.size() - 1));
    }

    /**
     * Paints this components on map of Campus in CampusPathGUI
     * 
     * @effect depends on user's action, displays the shortest path between two 
     *  buildings, or removes displayed path.
     * @param g Graphics to use
     */
    @Override
    public void paintComponent(Graphics g) {
        // ensure any background belonging to container is painted
        super.paintComponent(g);

        // cast g to its actual class to make graphics2d methods available
        Graphics2D g2 = (Graphics2D) g;

        width = getWidth();
        height = getHeight();
        rat_width = (width * 1.0) / image.getWidth();
        rat_height = (height * 1.0) / image.getHeight();

        // displays campus map on GUI
        g2.drawImage(image, 0, 0, width, height, 0, 0, image.getWidth(), 
                image.getHeight(), null);

        if (this.paths != null) {
            g2.setColor(Color.blue);
          
            double x = Math.round(coordinates.get(0).getX() * rat_width);
            double y = Math.round(coordinates.get(0).getY() * rat_height);
            
            double curr_X = x;
            double curr_Y = y;
            
            g2.setStroke(new BasicStroke(2));
            
            // displays path
            for (Coordinate coordinate : coordinates) {
                double next_X = Math.round(coordinate.getX() * rat_width);
                double next_Y = Math.round(coordinate.getY() * rat_height);
                
                g2.draw(new Line2D.Double(curr_X, curr_Y, next_X, next_Y));
                
                curr_X = next_X;
                curr_Y = next_Y;
            }
            
            g2.setFont(new Font("TimesRoman", Font.PLAIN, 15));
            // draw Red Oval on starting point of path
            
            g2.setColor(Color.RED);
            g2.fillOval((int) x - 4, (int) y - 4, 8, 8);
            g2.drawString(buildnames.get(0), (int) x - 8, (int) y - 8);

            // draw Green Oval on end point of path
            g2.setColor(Color.GREEN);
            g2.fillOval((int) curr_X - 4, (int) curr_Y - 4, 8, 8);
            g2.drawString(buildnames.get(1), (int) curr_X - 8, (int) curr_Y - 8);
        }
        
    }
}
