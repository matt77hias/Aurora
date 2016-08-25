package cwb1a;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/** This class represent a division, where a student is part of. This class can be used by the graph engine to
 *  compare a user to the other members of his division. 
 * This class represents a general division of which an active user could be a member (for a limited time).
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public abstract class Division implements Data {
	//The kind of the division.
	private static final String KIND = "Division";
	
	//The id of the division
    //Not allowed to contain spaces.
	private String divisionId;
	//The type of the division
	//Necessary for reconstructing the division from the database by the DivisionRegistry.
	private long divisionType;
	//A collection containing all the students which are member of the division.
	private ArrayListMap<String> students;
	
	/**
	 * Creates a new division.
	 * 
	 * @pre 	The id of the division may not contain spaces.
	 * @param 	divisionId 
	 * 			The id of the division.
	 * @param 	divisionType 
	 * 			The type of the division. 
	 */
	public Division(String divisionId, long divisionType) {
		students = new ArrayListMap<String>();
		this.divisionId = divisionId;
		this.divisionType = divisionType;
	}
	
	/**
     * Makes a new division using the values of the map. 
     * 
     * @pre		the keys must correspond with the field names of the division
     * @param 	values 
     * 			The map with the values, having keys: "divisionId" with String, "divisionType" with long.
     */
	public Division(Map<String, Object> properties) {
		students = new ArrayListMap<String>(properties);
		divisionId = (String) properties.get("divisionId");
		divisionType = (Long) properties.get("divisionType");
	}
	
	/**
     * Returns the fields of the activity in a HashMap, necessary for the DivisionRegistry.
     * 
     * @return 	A HashMap filled with the fields of the division and the key as the name of the field.
     */
	public HashMap<String, Object> getProperties() {
    	HashMap<String,Object> properties = new HashMap<String, Object>(students.getProperties());
    	properties.put("divisionId", divisionId);
    	properties.put("divisionType", divisionType);
    	return properties;
    }
	
	/**
	 * Changes the type of the division by to the given value.
	 * 
	 * @param 	divisionType
	 *        	The new value for the type of the division.
	 */
	protected void setDivisionType(long divisionType) {
		this.divisionType = divisionType;
	}
	
	/**
	 * Returns the type of the division.
	 * 
	 * @return 	The type of the division.
	 */
	public long getType() {
		return divisionType;
	}
	
	/**
     * Adds a user to this division.
     * @param 	user 
     * 			The user to be added.
     */
    public void addUserToDivision(User user) {
    	students.add(user.getId());
    }
    
    /**
     * Returns all the Id's of the users who are enlist to this division.
     * 
     * @return 	The users enlisted to this division.
     */
    public List<String> getStudentsId() {
    	return students;
    }
    
    /**
     * Removes a given user from this division
     * 
     * @param 	user 
     * 			The user that has to be removed from the collection of the division.
     * @post	The user has been removed
     * 			| user.getId() == null
     */
    public void removeUser(User user) {
    	students.remove(user.getId());
    }
    
    /**
     * Returns the kind of the division.
     * 
     * @return The kind of the division.
     */
    public String getKind() {
    	return KIND;
    }
    
    /**
     * Returns the kind of the division.
     * 
     * @return The kind of the division.
     */
    public static String staticGetKind() {
		return KIND;
	}
    
    /**
     * Returns the id of the division.
     * 
     * @return 	The id of the division.
     */
    public String getId() {
    	return divisionId;
    }
    
    /**
     * Controls if this division and a given division are equal.
     * 
     * @param 	division 
     *        	The division to compare with.
     * @return 	True when the divisions have the same id. 
     */
    public boolean equals(Division division) {
    	return divisionId.equals(division.getId());
    }
    
    /**
     * Controls if the owner of a given userId is a member of the division.
     * 
     * @param 	userId
     *        	The userId that has to be found in the collection in case of membership. 
     * @return 	True if the owner of a given userId is a member of the division.
     */
    public boolean checkStudent(String userId) {
    	boolean found = false;
    	Iterator<String> it = students.iterator();
    	while(it.hasNext() && !found){
            String checker = it.next();
            if(checker.equals(userId))
            {
            	found = true;
            }
    	}
    	return found;
    }
}