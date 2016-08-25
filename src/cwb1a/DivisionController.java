package cwb1a;

import java.util.LinkedHashMap;
import java.util.ArrayList;

import com.google.appengine.api.datastore.EntityNotFoundException;

/**
 * This class controls and creates the hierarchy of the subdivisions.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class DivisionController {
	
	//Design pattern Singleton
	private static DivisionController instance = new DivisionController();
	
	/**
	 * @note Not allowed to create objects of this class.
	 */
	private DivisionController() {
		
	}

	/**
     * This method returns the only possible operational instance of the DivisionController class.
     * 
     * @return The only possible operational instance of the DivisionController class.
     */
	public static DivisionController getInstance() {
		return instance;
	}
	
	/**
	 * Returns a map with a main group (head of hierarchy) as key and a list of subgroups as entity.
	 * 
	 * @return A map with a main group (head of hierarchy) as key and a list of subgroups as entity.
	 */
	public LinkedHashMap<String,ArrayList<String>> generateMainTree() {
		LinkedHashMap<String, ArrayList<String>> tree = new LinkedHashMap<String, ArrayList<String>>();
		
		try {
			Subdivision general = (Subdivision) DivisionRegistry.getInstance().get("General");
			generateTree(general,tree);
		}
		catch (EntityNotFoundException e) {
			
		}
		
		return tree;
	}
	
	/**
	 * Recursive algorithm that creates a map with a main group (head of hierarchy) as key and a list of subgroups as entity.
	 * 
	 * @param trunk
	 *        Start subdivision (head of hierarchy)
	 * @param tree
	 *        A map with a main group (head of hierarchy = trunk) as key and a list of subgroups as entity.
	 */
	private void generateTree(Subdivision trunk, LinkedHashMap<String,ArrayList<String>> tree) {
		if(trunk.getSubdivisionList().size() == 0) {
			//trivial situation
		}
		else {
			generateSelectOption(trunk,tree);
			for(String lusItem : trunk.getSubdivisionList()) {
				try {
					Subdivision lusTrunk = (Subdivision) DivisionRegistry.getInstance().get(lusItem);
					generateTree(lusTrunk,tree);

				}
				catch(EntityNotFoundException e) {
					
				}

			}
		}
	}
	
	/**
	 * Creates a map with a the given subdivision as key and a list of subgroups as entity.
	 * 
	 * @param division
	 *        The division whose under hierarchy has to be created.
	 * @param tree
	 *        A map with a main group (head of hierarchy = trunk) as key and a list of subgroups as entity.
	 *        The part of the hierarchy above the given division is already stocked in the map.
	 */
	private void generateSelectOption(Subdivision division, LinkedHashMap<String,ArrayList<String>> tree ) {
		
		ArrayList<String> subs = new ArrayList<String>();
		for(String id : division.getSubdivisionList()) {
			subs.add(id);	
		}
		tree.put(division.getId(),subs);
		
	}
}
