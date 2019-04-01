package hw8.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;
import hw5.Graph;
import hw8.CampusParser;
import hw8.CampusParser.MalformedDataException;
import hw8.Coordinate;

/**
 * CampusParserTest contains test cases to test CampusParser class
 */
public class CampusParserTest {
    private Map<String, String> buildnames;
    private Map<String, Coordinate> locations;
    private Graph<Coordinate, Double> paths;

    @Before
    public void setUp() {
        buildnames = new HashMap<String, String>();
        locations = new HashMap<String, Coordinate>();
        paths = new Graph<Coordinate, Double>();
    }

    // test parser to load building data
    
    @Test
    public void testBuildParserWithEmptyData() throws MalformedDataException {
        CampusParser.parseBuildData("src/hw8/data/empty.dat", buildnames, locations);
        
        assertEquals("{}", buildnames.toString());
    }

    // test parser to load path data
    
    @Test
    public void testPathParserWithEmptyData() throws MalformedDataException {
        CampusParser.parsePathData("src/hw8/data/empty.dat", paths);
        
        assertEquals("{}", paths.toString());
    }
    
    @Test
    public void testBuildParserWithSoccerTeamData() throws MalformedDataException {
        CampusParser.parseBuildData("src/hw8/data/soccer_team.dat", buildnames, locations);
        Map<String, String> abb = new HashMap<String, String>();
        Map<String, Coordinate> loca = new HashMap<String, Coordinate>();
        abb.put("MUTD", "Manchester United");
        loca.put("MUTD", new Coordinate(1, 0));
        abb.put("RMD", "Real Madrid");
        loca.put("RMD", new Coordinate(0, 0));
        
        assertEquals(buildnames, abb);
        assertEquals(locations, loca);
    }
    
    @Test
    public void testPathParserWithSoccerTeamData() throws MalformedDataException {
        CampusParser.parsePathData("src/hw8/data/soccer_paths.dat", paths);
        Graph<Coordinate, Double> graph = new Graph<Coordinate, Double>();
        Coordinate mutd = new Coordinate(1, 0);
        Coordinate rmd = new Coordinate(0, 0);
        Edge<Coordinate, Double> e1 = new Edge<Coordinate, Double>(mutd, rmd, 1.0);
        Edge<Coordinate, Double> e2 = new Edge<Coordinate, Double>(rmd, mutd, 1.0);
        
        graph.addNode(mutd);
        graph.addNode(rmd);
        
        assertEquals(paths.getNodes(), graph.getNodes());
        assertTrue(paths.containsEdge(e1));
        assertTrue(paths.containsEdge(e2));
    }
    
    // test parser with badly formated data
    
    @Test (expected = Exception.class)
    public void testBuildParserMalFormatedDataSpaceNotTab() throws MalformedDataException {
        CampusParser.parseBuildData("src/hw8/data/malformat_space.dat", buildnames, locations);
    }
    
    @Test (expected = Exception.class)
    public void testBuildParserMalFormatedDataNotString() throws MalformedDataException {
        CampusParser.parseBuildData("src/hw8/data/malformat_string.dat", buildnames, locations);
    }
    
    @Test (expected = Exception.class)
    public void testPathParserMalFormatedDataTabAfterColon() throws MalformedDataException {
        CampusParser.parsePathData("src/hw8/data/malformat_tab.dat", paths);
    }

    @Test (expected = Exception.class)
    public void testPathParserMalFormatedDataNotDouble() throws MalformedDataException {
        CampusParser.parsePathData("src/hw8/data/malformat_double.dat", paths);
    }
    
    // test parser with multiple data
    
    @Test
    public void testBuildParserWithCountryData() throws MalformedDataException {
        CampusParser.parseBuildData("src/hw8/data/country.dat", buildnames, locations);
        Map<String, String> abb = new HashMap<String, String>();
        Map<String, Coordinate> loca = new HashMap<String, Coordinate>();
        
        abb.put("KOR", "Korea");
        abb.put("CHN", "China");
        abb.put("BRZ", "Brazil");
        loca.put("KOR", new Coordinate(0, 0));
        loca.put("BRZ", new Coordinate(0, 1));
        loca.put("CHN", new Coordinate(1, 0));
        
        assertEquals(buildnames, abb);
        assertEquals(locations, loca);
    }

    @Test
    public void testPathParserWithCountryData() throws MalformedDataException {
        CampusParser.parsePathData("src/hw8/data/country_path.dat", paths);
        Graph<Coordinate, Double> graph = new Graph<Coordinate, Double>();
        Coordinate kor = new Coordinate(0, 0);
        Coordinate chn = new Coordinate(1, 0);
        Coordinate brz = new Coordinate(0, 1);
        Edge<Coordinate, Double> e1 = new Edge<Coordinate, Double>(kor, chn, 1.0);
        Edge<Coordinate, Double> e2 = new Edge<Coordinate, Double>(chn, kor, 1.0);
        Edge<Coordinate, Double> e3 = new Edge<Coordinate, Double>(kor, brz, 1.0);
        Edge<Coordinate, Double> e4 = new Edge<Coordinate, Double>(brz, kor, 1.0);
        Edge<Coordinate, Double> e5 = new Edge<Coordinate, Double>(brz, chn, 1.0);
        Edge<Coordinate, Double> e6 = new Edge<Coordinate, Double>(chn, brz, 1.0);
        
        graph.addNode(kor);
        graph.addNode(chn);
        graph.addNode(brz);
        graph.addEdge(kor, chn, 1.0);
        graph.addEdge(chn, kor, 1.0);
        graph.addEdge(brz, chn, 1.0);
        graph.addEdge(chn, brz, 1.0);
        graph.addEdge(kor, brz, 1.0);
        graph.addEdge(brz, kor, 1.0);
        
        assertEquals(paths.getNodes(), graph.getNodes());
        assertTrue(paths.containsEdge(e1));
        assertTrue(paths.containsEdge(e2));
        assertTrue(paths.containsEdge(e3));
        assertTrue(paths.containsEdge(e4));
        assertTrue(paths.containsEdge(e5));
        assertTrue(paths.containsEdge(e6));
    }
}
