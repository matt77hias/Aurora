package cwb1a;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a subdivision. 
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Subdivision extends Division
{
	// The id's of the subdivisions of this subdivision.
	ArrayListMap<String> subIds;
	// The name of the subdivision
	private String subdivisionName;
	/**
	 * This method makes a subdivision object.
	 * 
	 * @param subdivisionName
	 *        The name of the subdivision
	 * @param subdivisionId
	 *        The id of the subdivision
	 */
    public Subdivision(String subdivisionName, String subdivisionId) {
    	super(subdivisionId, DivisionFactory.SUBDIVISION);
    	this.subdivisionName = subdivisionName;
    	
    	subIds = new ArrayListMap<String>("S");
    }
    
    /**
     * Makes a new subdivision using the values of the map. 
     * Be careful that the keys correspond with the field names of the subdivision. There is no control.
     * 
     * @param 	values 
     * 			The map with the values, having keys: "subdivisionName" with String.
     */
    public Subdivision(Map<String,Object> properties) {
    	super(properties);
    	subIds = new ArrayListMap<String>(properties, "S");
    	subdivisionName = (String) properties.get("subdivisionName");
    }
    
    /**
     * Returns the fields of the subdivision in a HashMap, necessary for the DivisionRegistry.
     * 
     * @return 	A HashMap filled with the fields of the subdivision and the key as the name of the field.
     */
    public HashMap<String, Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	properties.putAll(subIds.getProperties());
    	properties.put("subdivisionName", subdivisionName);
    	return properties;
    }
    
    /**
     * This method returns the name of the subdivision.
     * 
     * @return The name of the subdivision.
     */
    public String getSubdivisionName() {
    	return subdivisionName;
    }
    
    /**
     * Adds an id of a subdivision of this subdivision.
     * 
     * @param divisionId
     *        The id of the subdivision that has to be added.
     */
    public void add(String divisionId) {
    	subIds.add(divisionId);
    }
    
    /**
     * Returns the collection with all the id's of the subdivisions of this subdivision.
     * 
     * @return The collection with all the id's of the subdivisions of this subdivision.
     */
    public ArrayListMap<String> getSubdivisionList() {
    	return subIds;
    }

}
