package cwb1a;

import java.util.Map;

/**
 * This class creates new divisions depending on the divisionType. It is generally used by the DivisionRegistry. After collecting the 
 * right properties, the DivisionRegistry must recreate the right division. Their are some public fields, used as flags for the 
 * subclasses of division.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class DivisionFactory {
	// Course flag.
	public static final long COURSE = 1;
	// Subdivision flag.
	public static final long SUBDIVISION = 2;
	
	/**
	 * @note The constructor is private. You are not allowed to make an object of this class.
	 * 
	 */
	private DivisionFactory() {
		
	}
	
	/**
	 * Creates the right type of division, using a Map with the properties. 
	 * 
	 * @param 	properties 
	 * 			The properties of the kind of division. 
	 * @return 	The right kind of division corresponding the type of the given division.
	 */
	public static Division createDivision(Map<String, Object> properties) {
		Division division = null;
		if(properties.get("divisionType").equals(COURSE)) {
			division = new Course(properties);
		}
		
		else if(properties.get("divisionType").equals(SUBDIVISION)) {
			division = new Subdivision(properties);
		}
		
		return division;
	}
}