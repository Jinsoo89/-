package hw9;

import hw8.CampusParser.MalformedDataException;
import hw8.CampusPathModel;

/**
 * <p>CampusPathMain</p> builds CampusPathModel and connects the
 * model, view, and controller via Graphic User Interface to have the 
 * user to use this and, get displayed shortest path in the campus.
 */
public class CampusPathsMain {
    
    /**
     * Main method of this class. Build model and run GUI with the model.
     * 
     * @param args
     * @throws MalformedDataException if passed data in CampusPathModel is with
     *          wrong format
     */
    public static void main(String[] args) throws MalformedDataException {
        CampusPathModel m = new CampusPathModel(
                "src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
        
        new CampusPathsGUI(m);
    }
}
