package hw7.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hw5.Edge;
import hw5.Graph;
import hw7.MarvelPaths2;

/**
 * This class contains a set of test cases that can be used to test the
 * implementation of the MarvelPaths2 class.
 * 
 * Some Test Methods are relatively longer in order to simulate proper
 * functionality
 */
public class MarvelPaths2Test {

    public Graph<String, Double> graph;
    public List<Edge<String, Double>> list;
    
    @Before
    public void setUp() {
        graph = MarvelPaths2.buildGraph("src/hw7/data/soccerPlayers.tsv");
    }
    
    // tests on BuildGraph method
    
    @Test (expected = IllegalArgumentException.class)
    public void testBuildGraphWithNullFile() {
        graph = MarvelPaths2.buildGraph(null);
    }
    
    // tests on FindMinCostPath method
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullFrom() {
        MarvelPaths2.findMinCostPath(graph, null, "C.Ronaldo");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullTo() {
        MarvelPaths2.findMinCostPath(graph, "C.Ronaldo", null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithNullGraph() {
        MarvelPaths2.findMinCostPath(null, "C.Ronaldo", "L.Messi");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithInvalidFrom() {
        MarvelPaths2.findMinCostPath(graph, "UW CSE", "L.Messi");
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindPathWithInvalidTo() {
        MarvelPaths2.findMinCostPath(graph, "C.Ronaldo", "CSE331");
    }
}