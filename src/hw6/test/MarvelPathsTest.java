package hw6.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;
import hw5.Graph;
import hw6.MarvelPaths;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the MarvelPaths class.
 * 
 * Some Test Methods are relatively longer in order to simulate proper
 * functionality
 */
public class MarvelPathsTest {

    public Graph<String, String> graph;
    public List<Edge<String, String>> list;
    public Edge<String, String> edge1, edge2, edge3;
    
    @Before
    public void setUp() {
        graph = MarvelPaths.buildGraph("src/hw6/data/soccerPlayers.tsv");
        list = new ArrayList<Edge<String, String>>();
        edge1 = new Edge<String, String>("C.Ronaldo", "D.Maria", "M.United");
        edge2 = new Edge<String, String>("D.Maria", "D.Alves", "PSG");
        edge3 = new Edge<String, String>("D.Alves", "L.Messi", "FC.Barcelona");
        
    }
    
    // tests on BuildGraph method
    
    @Test (expected = IllegalArgumentException.class)
    public void testBuildGraphWithNullFile() {
        graph = MarvelPaths.buildGraph(null);
    }
    
    @Test
    public void testBuildGraphWithValidInput() {
        Edge<String, String> edge = new Edge<String, String>("C.Ronaldo", "L.Modric", "R.Madrid");
        MarvelPaths.findPath(graph, "C.Ronaldo", "L.Modric");
        
        assertTrue(graph.containsEdge(edge));
    }
    
    @Test
    public void testBuildGraphWithValidInputHasRelationShipIndirectly() {
        list.add(edge1);
        list.add(edge2);
        list.add(edge3);
        List<Edge<String, String>> found = MarvelPaths.findPath(graph, "C.Ronaldo", "L.Messi");
        
        assertEquals(found, list);
    }
    
    // tests on FindPath method
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullFrom() {
        MarvelPaths.findPath(graph, null, "C.Ronaldo");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullTo() {
        MarvelPaths.findPath(graph, "C.Ronaldo", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullGraph() {
        MarvelPaths.findPath(null, "C.Ronaldo", "L.Messi");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithInvalidFrom() {
        MarvelPaths.findPath(graph, "UW CSE", "L.Messi");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithInvalidTo() {
        MarvelPaths.findPath(graph, "C.Ronaldo", "HELLO WORLD");
    }
    
    
}
