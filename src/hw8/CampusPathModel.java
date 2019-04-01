package hw8;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import hw5.Graph;
import hw5.Edge;
import hw7.MarvelPaths2;
import hw8.CampusParser.MalformedDataException;

/**
 * <p>CampusPathModel</p> represents a model of path finding of campus
 */
public class CampusPathModel {
    
    // Representation Invariant:
    //  buildnames, locations, paths != null
    //  every key and value of buildnames, locations are not null
    
    // Abstract Function:
    //  AF(this) = 
    //          buildnames = {"short name"="full name"...}, or {} if data is empty
    //          locations = {"short name"="coordinate"...}, or {} if data is empty

    /*
     * constant debugging variable for checkRep()
     */
    private static final boolean CHECK_DEBUG = false;

    // map that stores abbreviated name to full name of buildings
    private Map<String, String> buildnames;
    // map that stores buildings name to their locations
    private Map<String, Coordinate> locations;
    // graph that stores paths in campus
    private Graph<Coordinate, Double> paths;

    /**
     * Build a campus path model
     * 
     * @param buildData filename of data that contains campus buildings
     * @param pathData filename of data that contains campus paths
     * @throws MalformedDataException if data files are not expected format
     * @throws IllegalArgumentException if data file(s) is(are) null
     */
    public CampusPathModel(String buildData, String pathData) throws MalformedDataException {
        if (buildData == null) {
            throw new IllegalArgumentException("buildData cannot be null");
        }
        if (pathData == null) {
            throw new IllegalArgumentException("buildData cannot be null");
        }

        buildnames = new HashMap<String, String>();
        locations = new HashMap<String, Coordinate>();
        paths = new Graph<Coordinate, Double>();

        CampusParser.parseBuildData(buildData, buildnames, locations);
        CampusParser.parsePathData(pathData, paths);

        checkRep();
    }

    /**
     * Returns a read-only map that contains abbreviated name to full name of
     * buildings
     * 
     * @return read-only map that contains abbreviated name to full name of
     *         buildings
     */
    public Map<String, String> getBuildNames() {
        checkRep();
        return Collections.unmodifiableMap(buildnames);
    }

    /**
     * Returns a coordinate that represents location of building
     * 
     * @param buildName name of building to get location
     * @return coordinate that represents location of building
     * @throws IllegalArgumentException if name of building is null
     */
    public Coordinate getLocation(String buildName) {
        checkRep();
        if (buildName == null) {
            throw new IllegalArgumentException("buildName cannot be null");
        }

        checkRep();
        return locations.get(buildName);
    }

    /**
     * Finds the shortest path from two buildings
     * 
     * @param from location of building of beginning of path
     * @param to location of building of ending of path
     * @return the shortest path from 'from' building to 'to' building, or null if
     *         there is no path between two buildings
     */
    public Map<Coordinate, Double> findShortestPath(Coordinate from, Coordinate to) {
        checkRep();
        List<Edge<Coordinate, Double>> foundPaths = MarvelPaths2.findMinCostPath(paths, from, to);

        if (foundPaths == null) {
            return null;
        }

        Map<Coordinate, Double> route = new LinkedHashMap<Coordinate, Double>();

        for (Edge<Coordinate, Double> path : foundPaths) {
            route.put(path.getChild(), path.getLabel());
        }
        
        checkRep();
        return route;
    }

    /**
     * Check representation invariant holds
     */
    private void checkRep() {
        if (CHECK_DEBUG) {
            assert (buildnames != null) : "map of abbreviated name to full name is null";
            assert (locations != null) : "locations cannot be null";
            assert (paths != null) : "paths cannot be null";
            
            // assert calls for buildnames
            Set<String> keys1 = buildnames.keySet();
            
            for (String key : keys1) {
                assert (key != null) : "abbreviated name cannot be null";
                assert (buildnames.get(key) != null) : "full name cannot be null";
            }
            
            // assert calls for locations
            Set<String> keys2 = locations.keySet();
            
            for (String key : keys2) {
                assert (key != null) : "abbreviated name cannot be null";
                assert (locations.get(key) != null) : "location cannot be null";
            }
        }
    }
}
