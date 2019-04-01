package hw8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import hw8.CampusParser.MalformedDataException;

/**
 * CampusPathVC represents View, and Controller of CampusPath.
 * This class accepts user input to find shortest paths in campus
 * via model of CampusPath 
 */
public class CampusPathVC {
    
    // to implement eight sectors for classifying directions
    private static final double ONE = Math.PI / 8;
    private static final double THREE = ONE * 3;
    private static final double FIVE = ONE * 5;
    private static final double SEVEN = ONE * 7;
    private static final double NEGSEVEN = SEVEN * -1;
    private static final double NEGFIVE = FIVE * -1;
    private static final double NEGTHREE = THREE * -1;
    private static final double NEGONE = ONE * -1;
    
    /**
     * Displays names of all buildings (Abbreviated name, and full name)
     * 
     * @param model CampusPath model
     * @throws IllegalArgumentException if model is null
     */
    public static void getBuildings(CampusPathModel model) {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null");
        }
        
        String result = "Buildings:";
        
        // get building names from model and extract abbreviated names
        // and store in TreeSet - alphabetical order
        Map<String, String> buildNames = model.getBuildNames();
        Set<String> shortNames = new TreeSet<String>(buildNames.keySet());
        
        for (String shortName : shortNames) {
            String fullName = buildNames.get(shortName);
            result += "\n\t" + shortName + ": " + fullName;
        }
        
        System.out.println(result + "\n");
    }
    
    /**
     * Displays the shortest path from 'from building' to 'to building' in campus.
     *  
     * @param model model of CampusPathModel
     * @param from abbreviated name of 'from' building
     * @param to abbreviated name of 'to' building
     * @throws IllegalArgumentException if model is null, from is null, to is null
     */
    public static void getShortestPath(CampusPathModel model, String from, String to) {
        if (model == null) {
            throw new IllegalArgumentException("model cannot be null");
        }
        if (from == null) {
            throw new IllegalArgumentException("from cannot be null");
        }
        if (to == null) {
            throw new IllegalArgumentException("to cannot be null");
        }
        
        // set coordinates by getting data from model
        Coordinate fromLocat = model.getLocation(from);
        Coordinate toLocat = model.getLocation(to);
        
        // display results
        if (fromLocat == null && toLocat == null) {
            System.out.println("Unknown building: " + from);
            System.out.println("Unknown building: " + to + "\n");
        } else if (fromLocat == null) {
            System.out.println("Unknown building: " + from + "\n");
        } else if (toLocat == null) {
            System.out.println("Unknown building: " + to + "\n");
        } else {
            // case of valid data
            String result = "";
            String fromFullName = model.getBuildNames().get(from);
            String toFullName = model.getBuildNames().get(to);
            
            result += "Path from " + fromFullName + " to " + toFullName + ":\n";
            
            Map<Coordinate, Double> shortestPath = model.findShortestPath(
                    fromLocat, toLocat);
            
            double x = fromLocat.getX();
            double y = fromLocat.getY();
            double distance = 0.0;
            
            List<Coordinate> coordinates = new ArrayList<Coordinate>(
                    shortestPath.keySet());
            // remove location from itself
            coordinates.remove(0);
            
            for (Coordinate path : coordinates) {
                double toX = path.getX();
                double toY = path.getY();
                double curDist = shortestPath.get(path).doubleValue();
                double theta = Math.atan2(toY - y, toX - x);
                
                String direction = findDirection(theta);
                result += String.format("\tWalk %.0f feet %s to (%.0f, %.0f)\n", 
                        (curDist - distance), direction, toX, toY);
                
                // x, y location of destination become current x, y location
                x = toX;
                y = toY;
                distance = curDist;
            }
            
            result += String.format("Total distance: %.0f feet\n", distance);
            
            System.out.println(result);
        }
    }
    
    /**
     * Find direction of theta, possible directions are 
     * N, NE, E, SE, S, SW, W, NW
     * 
     * @param theta angle from coordinates in polar
     * @return found direction based on the angle
     */
    private static String findDirection(double theta) {
        if (theta > NEGFIVE && theta < NEGTHREE) {
            return "N";
        } else if (theta > NEGTHREE && theta < NEGONE) {
            return "NE";
        } else if ((theta > NEGONE && theta < 0) || (theta < ONE && theta > 0)) {
            return "E";
        } else if (theta > ONE && theta < THREE) {
            return "SE";
        } else if (theta > THREE && theta < FIVE) {
            return "S";
        } else if (theta > FIVE && theta < SEVEN) {
            return "SW";
        } else if ((theta > SEVEN && theta < Math.PI) || 
                (theta < NEGSEVEN && theta > Math.PI * -1)) {
            return "W";
        } else {
            return "NW";
        }
    }
    
    /**
     * Main method of CampusPathVC. Takes user inputs and display appropriate outputs
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            CampusPathModel model = new CampusPathModel(
                    "src/hw8/data/campus_buildings.dat", 
                    "src/hw8/data/campus_paths.dat");
            
            String m = "Menu:\n" + "\t" + "r to find a route" + "\n\t" + 
                        "b to see a list of all buildings" + "\n\t" + "q to quit\n";
            
            System.out.println(m);
            Scanner user_input = new Scanner(System.in);
            System.out.print("Enter an option ('m' to see the menu): ");
            
            // run this command line interface until the user enters q in menu
            while (true) {
                String input = user_input.nextLine();
                
                if (input.equals("") || input.startsWith("#")) {
                    System.out.println(input);
                    continue;
                }
                
                if (input.equals("m")) {
                    System.out.println(m);
                } else if (input.equals("b")) {
                    getBuildings(model);
                } else if (input.equals("r")) {
                    System.out.print("Abbreviated name of starting building: ");
                    String from = user_input.nextLine();
                    System.out.print("Abbreviated name of ending building: ");
                    String to = user_input.nextLine();
                    getShortestPath(model, from, to);
                } else if (input.equals("q")) {
                    user_input.close();
                    break;
                } else {
                    System.out.println("Unknown option\n");
                }
                System.out.print("Enter an option ('m' to see the menu): ");
            }
            
        } catch (MalformedDataException e) {
            System.err.println(e.toString());
            e.printStackTrace();
        }
    }
}
