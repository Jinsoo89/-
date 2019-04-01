package hw8.test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import hw8.CampusParser.MalformedDataException;
import hw8.CampusPathModel;
import hw8.Coordinate;

/**
 * CampusPathModelTest contains test cases to test CampusPathModel
 */
public class CampusPathModelTest {

    // test build model with valid, invalid data

    @Test(expected = IllegalArgumentException.class)
    public void testBuildCampusPathModelWithNullBuilding() throws MalformedDataException {
        new CampusPathModel(null, "src/hw8/data/campus_paths.dat");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildCampusPathModelWithNullPaths() throws MalformedDataException {
        new CampusPathModel("src/hw8/data/country.dat", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildCampusPathModelWithBothNull() throws MalformedDataException {
        new CampusPathModel(null, null);
    }

    @Test(expected = Exception.class)
    public void testBuildCampusPathModelWithWrongBuildData() throws MalformedDataException {
        new CampusPathModel("src/hw8/data/malformat_tab.dat", "src/hw8/data/campus_paths.dat");
    }

    @Test(expected = Exception.class)
    public void testBuildCampusPathModelWithWrongPathData() throws MalformedDataException {
        new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/malformat_space.dat");
    }

    @Test
    public void testBuildCampusPathModelWithCountryData() throws MalformedDataException {
        new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
    }

    @Test
    public void testBuildCampusPathModelWithCampusData() throws MalformedDataException {
        new CampusPathModel("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
    }

    // test getBuildNames (short, full) methods

    @Test
    public void testgetBuildNamesWithEmptyData() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/empty.dat", "src/hw8/data/empty.dat");

        assertEquals(model.getBuildNames(), new HashMap<String, String>());
    }

    @Test
    public void testgetBuildNamesOfNull() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");

        assertEquals(model.getBuildNames().get(null), null);
    }
    
    @Test
    public void testGetBuildFullNamesOfCountry() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        Map<String, String> names = new HashMap<String, String>();
        
        names.put("KOR", "Korea");
        names.put("CHN", "China");
        names.put("BRZ", "Brazil");
        
        assertEquals(model.getBuildNames(), names);
    }

    // test getLocation method

    @Test (expected = IllegalArgumentException.class)
    public void testGetLocationWithValidDataButNullBuilding() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");

        model.getLocation(null);
    }
    
    @Test
    public void testGetLocationWithValidDataButNonExistBuilding() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");

        assertEquals(model.getLocation("Seattle"), null);
    }
    
    @Test
    public void testGetLocationWithValidDataWithoutException() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        assertEquals(model.getLocation("KOR"), new Coordinate(0, 0));
    }
    
    // tests for findShortestPath method
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithFromNull() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(null, model.getLocation("KOR"));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithToNull() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(model.getLocation("KOR"), null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithBothLocationsNull() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(null, null);
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithNotExistFromBuilding() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(new Coordinate(99,99), model.getLocation("KOR"));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithNotExistToBuilding() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(model.getLocation("KOR"), new Coordinate(99,99));
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void testFindShortestPathWithNotExistFromAndToBuilding() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        
        model.findShortestPath(new Coordinate(1.01,1.01), new Coordinate(99,99));
    }
    
    @Test
    public void testFindShortestPathWithValidData() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_path.dat");
        Map<Coordinate, Double> path = new LinkedHashMap<Coordinate, Double>();
        
        path.put(new Coordinate(0, 0), 0.0);
        path.put(new Coordinate(0, 1), 1.0);
        
        assertEquals(path, model.findShortestPath(model.getLocation("KOR"), model.getLocation("BRZ")));
    }
    
    @Test
    public void testFindShortestPathWithValidDataButNoPath() throws MalformedDataException {
        CampusPathModel model = new CampusPathModel("src/hw8/data/country.dat", "src/hw8/data/country_nopath.dat");
        
        assertEquals(null, model.findShortestPath(model.getLocation("KOR"), model.getLocation("BRZ")));
    }
}
