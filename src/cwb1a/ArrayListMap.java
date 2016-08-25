package cwb1a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Modification and extension for the ArrayList<E> class.
 * 
 * @author Aurora
 * @version Griffin
 */
public class ArrayListMap<E> extends ArrayList<E> {
	
	// The tag of the map (in order to stock efficiently more lists in the map).
	private String tag;
	
	/**
	 * Creates a new list with an empty tag.
	 * @note This constructor is for normal use; meaning converting one map to a list.
	 */
	public ArrayListMap() {
		super();
		this.tag="";
	}
	
	/**
	 * Creates a new list from the objects of a Map.
	 * The list with empty tag is in this case already defined.
	 * @note This constructor is for normal use; meaning converting one map to a list.
	 * 
	 * @param 	properties 
	 * 			The map with the objects.
	 */
	public ArrayListMap(Map<String, Object> properties) {
    	super();
    	int i = 0;
    	while(properties.containsKey(""+i))
    	{
    		this.add((E) properties.get("" + i));
    		i++;
    	}
    	this.tag = "";
    }
	
	/**
	 * Creates a new list from the objects of a Map.
	 * The list with not empty tag is in this case already defined.
	 * @note This constructor is advanced use; meaning converting more maps to a list.
	 * 
	 * @param 	properties 
	 * 			The map with the objects.
	 * @param   tag
	 *          The tag for the list.
	 */
	public ArrayListMap(Map<String, Object> properties, String tag) {
		super();
		int i = 0;
		while(properties.containsKey(tag+i))
    	{
    		this.add((E) properties.get(tag + i));
    		i++;
    	}
	}
	
	/**
	 * Creates a new list with a not tag.
	 * @note This constructor is for advanced use; meaning converting more maps to a list.
	 * 
	 * @param tag
	 *        The tag for the list.
	 */
	public ArrayListMap(String tag) {
		super();
		this.tag=tag;
	}
	
	/**
	 * Returns a Map with the objects of this list.
	 * 
	 * @return A Map with the values of this list.
	 */
	public HashMap<String, E> getProperties() {
    	HashMap<String,E> properties = new HashMap<String,E>();
    	
    	for(int i = 0; i < this.size(); i++) {
    		properties.put(tag + i, this.get(i));
    	}
    	
    	return properties;
    }
}