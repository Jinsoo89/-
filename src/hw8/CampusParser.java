package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import hw5.Graph;

/**
 * CampusParser class contains parsers to read data about buildings,
 * and paths
 */
public class CampusParser {

    /**
     * A checked exception class for bad data files
     */
    @SuppressWarnings("serial")
    public static class MalformedDataException extends Exception {
        public MalformedDataException() {
        }

        public MalformedDataException(String message) {
            super(message);
        }

        public MalformedDataException(Throwable cause) {
            super(cause);
        }

        public MalformedDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Reads the Campus buildings dataset. Each line of the input file contains a
     * abbreviated name (short name), full name of building, and coordinate that
     * represents location of the building entrance.
     * 
     * @param filename the file that will be read
     * @param buildnames map that maps building's short name to full name
     * @param locations map that maps building's short name to it's location
     * @modifies buildnames, locations
     * @effects fills buildnames with short name to full name, and fills locations.
     * @throws MalformedDataException if the file is not well-formed: each line 
     * contains exactly four tokens separated by a tab, or else starting with a # symbol 
     * to indicate a comment line.
     */
    public static void parseBuildData(String filename, Map<String, String> buildnames, 
            Map<String, Coordinate> locations) throws MalformedDataException {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));

            // Construct the collections of name of buildings, and their locations
            // <short name, full name>, and
            // <short name, location> pairs at a time.
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {

                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }

                // Parse the data, stripping out quotation marks and throwing
                // an exception for malformed lines.
                inputLine = inputLine.replace("\"", "");
                String[] tokens = inputLine.split("\t");
                if (tokens.length != 4) {
                    throw new MalformedDataException(
                            "Line should contain exactly one tab: " + inputLine);
                }

                String shortname = tokens[0];
                String fullname = tokens[1];
                double xCord = Double.parseDouble(tokens[2]);
                double yCord = Double.parseDouble(tokens[3]);

                // Add the parsed data to building names collections
                buildnames.put(shortname, fullname);

                // Add the parsed data to building location collections
                locations.put(shortname, new Coordinate(xCord, yCord));
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }

    /**
     * Reads the Campus paths dataset, and build campus paths
     * 
     * @param filename the file that will be read
     * @param paths graph that represents campus paths
     * @modifies paths
     * @effects fills paths with coordinate as node (locations) and edges as paths between
     * locations 
     * @throws MalformedDataException if the file is not well-formed: starting with
     * non-indented line with coordinate, and followed indented line with coordinate and 
     * distance between them. Each coordinate has x, and y points with comma between them.
     * indented line has colon with a space between coordinate and the distance.
     */
    public static void parsePathData(String filename, Graph<Coordinate, Double> paths) 
            throws MalformedDataException {

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filename));

            // Construct the collections of coordinates, and distance
            // between coordinates set
            String inputLine;

            Coordinate coord = null;

            while ((inputLine = reader.readLine()) != null) {

                // Ignore comment lines.
                if (inputLine.startsWith("#")) {
                    continue;
                }

                // Parse the data, stripping out quotation marks and throwing
                // an exception for malformed lines.
                inputLine = inputLine.replace("\"", "");
                inputLine = inputLine.replace("\t", "");

                String[] tokens = inputLine.split(": ");
                String[] subTokens = tokens[0].split(",");

                double x = Double.parseDouble(subTokens[0]);
                double y = Double.parseDouble(subTokens[1]);

                Coordinate coordinate = new Coordinate(x, y);

                // the line represents coordinate node
                if (tokens.length == 1) {
                    if (!paths.containsNode(coordinate)) {
                        paths.addNode(coordinate);
                    }
                    coord = coordinate;
                } else if (tokens.length == 2) {
                    if (coord == null) {
                        throw new MalformedDataException(
                                "There is no Non-indented line before this line");
                    }

                    if (!paths.containsNode(coordinate)) {
                        paths.addNode(coordinate);
                    }

                    double distance = Double.parseDouble(tokens[1]);
                    paths.addEdge(coord, coordinate, distance);

                } else {
                    throw new MalformedDataException(
                            "Line should contain exactly one tab: " + inputLine);
                }
            }
        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    System.err.println(e.toString());
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}
