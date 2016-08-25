package cwb1a;

import java.util.HashMap;


/**
 * This interface represents a data that can be used by the interface. 
 * All the classes that creates objects with the purpose to store those objects in the database are
 * obliged to extend this class.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public interface Data {
	
	/**
	 * Returns the fields of an object in a Map with the names of the fields as corresponding key.
	 * 
	 * @return 	the fields of an object in a Map with the names of the fields as corresponding key.
	 */
	abstract public HashMap<String,Object> getProperties();
	
	/**
	 * Returns the Id of the data
	 * 
	 * @return 	The id of the data.
	 */
	abstract public String getId();
	
	/**
	 * Returns the kind of the data.
	 * 
	 * @return 	The kind of the data.
	 */
	abstract public String getKind();
}