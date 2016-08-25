package cwb1a;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


/**
 * The DivisionRegistry uses the design pattern singleton. This class communicates with the database of google.
 * It is very important that you remember that only properties of divisions will be saved.
 * When you ask for a division, the DivisionRegistry searches the properties in the database and creates with
 * those properties a new division object corresponding the type of the division. 
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class DivisionRegistry {

		//The parent key for all divisions in the database.
		private Key allDivisions = KeyFactory.createKey(Division.staticGetKind(), "allDivisions");
		//Design pattern singleton.
		private static DivisionRegistry instance = new DivisionRegistry();
		
		/**
		 * @note The constructor is private. You are not allowed to make an object of this class.
		 */
		private DivisionRegistry() {
		}
		
		/**
		 * This method returns the only possible operational instance of the DivisionRegistry class.
		 * 
		 * @return 	The only possible operational instance of the DivisionRegistry class.
		 */
		public static DivisionRegistry getInstance() {
			return instance;
		 }
		
		/**
		 * Adds a division to the the database.
		 * 
		 * @param 	division 
		 * 			The division that is to be added to the database.
		 */
		public void add(Division division) {
			Entity entity = new Entity(division.getKind(), division.getId(), allDivisions);
			DatabaseCommunicator.getInstance().put(entity, division.getProperties());
		}
		
		/**
		 * Returns the division with the given id.
		 * 
		 * @param 	divisionId 
		 * 			The id of the division.
		 * @return 	The division with the given id.
		 * @throws 	EntityNotFoundException 
		 * 			The activity is not found.
		 */
		public Division get(String DivisionId) throws EntityNotFoundException {
			Key divisionKey = KeyFactory.createKey(allDivisions, Division.staticGetKind(), DivisionId);
			return DivisionFactory.createDivision(DatabaseCommunicator.getInstance().get(divisionKey).getProperties());
		}
		
		/**
		 * Deletes the division with the given id.
		 * 
		 * @param 	divisionId 
		 * 			The id of the division.
		 * @post	The division is deleted
		 * 			| division.getId() == null
		 */
		public void delete(String divisionId) {
			Key divisionKey = KeyFactory.createKey(allDivisions, Division.staticGetKind(), divisionId);
			DatabaseCommunicator.getInstance().delete(divisionKey);
		}
		
		/**
		 * Updates the given division in the database.
		 * 
		 * @param 	division 
		 * 			The division that has to be updated.
		 * @throws 	EntityNotFoundException 
		 * 			The division is not found in the database.
		 */
		public void update(Division division) throws EntityNotFoundException {
			Key divisionKey = KeyFactory.createKey(allDivisions, division.getKind(), division.getId());
			DatabaseCommunicator.getInstance().put(DatabaseCommunicator.getInstance().get(divisionKey), division.getProperties());
		}
		
		/**
		 * Returns all the divisions stored in the database.
		 * 
		 * @return 	All the divisions stored in the database.
		 */
		public List<Division> getAll() {
			ArrayList<Division> divisions = new ArrayList<Division>();
			for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allDivisions, Division.staticGetKind())) {
				divisions.add(DivisionFactory.createDivision(lusEntity.getProperties()));
			}
			return divisions;
		}
		
		/**
		 * Returns a collection with all stored divisions with a given type.
		 * 
		 * @param 	type
		 *        	The type of the divisions.
		 * @return 	Collection of all stored divisions with the given type.
		 */
		public List<Division> getAllType(long type) {
			ArrayList<Division> divisionTypes = new ArrayList<Division>();
			for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allDivisions, Division.staticGetKind())) {
				Division temp = DivisionFactory.createDivision(lusEntity.getProperties());
				if(temp.getType() == type)
				divisionTypes.add(temp);
			}	
			return divisionTypes;
		}
		
		/**
		 * Returns a collection with all the courses of which a given user is a member.
		 * 
		 * @param 	userId
		 *        	The id of the user.
		 * @return 	sCollection with all the courses of which the given user is a member.
		 */
		public List<Course> getAllUser(String userId) {
			ArrayList<Course> userCourses = new ArrayList<Course>();
			
			for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allDivisions, Division.staticGetKind())) {
				Division tempp = DivisionFactory.createDivision(lusEntity.getProperties());;
				if(tempp.getType() == DivisionFactory.COURSE)
				{
					Course temp = (Course) tempp;
				    if(temp.checkStudent(userId))
				    {
				    	userCourses.add(temp);
				    }
				}
			}
			return userCourses;
		}
		
		/**
		 * Cleans the division part of the database, meaning that every stored division is removed from the database.
		 * 
		 */
		public void cleaner() {
			for(Entity lusEntity : DatabaseCommunicator.getInstance().getAll(allDivisions, Division.staticGetKind())) {
				String tempId = DivisionFactory.createDivision(lusEntity.getProperties()).getId();
				delete(tempId);
			}
		}
		
		/**
		 * Returns the amount of divisions stored in the database.
		 * 
		 * @return 	The amount of divisions stored in the database.
		 */
		public long getSize() {
			return getAll().size();
		}
		
		/**
		 * Returns the division structure from super to su of the given user.
		 * 
		 * @param user 
		 *        The user whose division structure have to be deployed.
		 * @param main
		 *        The main (sub)division
		 * @return Collection with the division structure from super to sub of the given user.
		 */
		public ArrayList<Subdivision> deployDivisionStructureUser(User user, Subdivision main) {
			ArrayList<Subdivision> allSubs = new ArrayList<Subdivision>();
			Subdivision pos = main;
			
			while(pos.getSubdivisionList().size()!=0)
			{
				int i = 0;
				boolean changed = false;
				while(i<pos.getSubdivisionList().size() && changed == false)
				{
					String id = pos.getSubdivisionList().get(i);
					try{
						Subdivision temp = (Subdivision) get(id);
						if(temp.checkStudent(user.getId()) == true)
						{
							allSubs.add(temp);
							pos = temp;
							changed = true;
						}
					}
					catch(EntityNotFoundException e)
					{
					}
				i++;
				}
			}
			return allSubs;
		}
}