package cwb1a;

import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Query;

/**
 * This class represents the general transmission from data between the application and the database.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class DatabaseCommunicator {
	
	//Google datastoreService
	private DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
	
	//Maximum value of a kind that can be stored in the database.
	private final int INFINITY = Integer.MAX_VALUE;
	
	//Design pattern singleton
	private static DatabaseCommunicator instance = new DatabaseCommunicator();
	
	/**
	 * @note	The constructor is private, because not allowed to create objects of this class.
	 */
	private DatabaseCommunicator() {
	}
	
	/**
	 * This method returns the only possible operational instance of the DatabaseCommunicator class.
	 * 
	 * @return 	The only possible operational instance of the DatabaseCommunicator class.
	 */
	public static DatabaseCommunicator getInstance() {
		return instance;
	}
	
	/**
	 * Puts an entity in the google database.
	 * 
	 * @param 	entity 
	 * 			The entity that has to be added to the database.
	 * @param 	properties 
	 * 			The properties that have to be added to the database with the given entity.
	 */
	public void put(Entity entity, Map<String,Object> properties) {
		for(String lusKey : properties.keySet())
		{
			entity.setProperty(lusKey, properties.get(lusKey));
		}
		datastore.put(entity);
	}
	
	/**
	 * Returns the entity from the database with the given key.
	 * 
	 * @param 	key 
	 * 			The key corresponding with the entity that has to be returned.
	 * @return 	The entity corresponding to the given key.
	 * @throws 	EntityNotFoundException 
	 * 			There is no entity.
	 */
	public Entity get(Key key) throws EntityNotFoundException {
		return datastore.get(key);
	}
	
	/**
	 * Updates an entity with given key and properties.
	 * 
	 * @param 	key 
	 * 			The key of the entity.
	 * @param 	properties 
	 * 			The data which properties that have to be updated.
	 * @throws 	EntityNotFoundException 
	 * 			The key is not found.
	 */
	public void update(Key key, Map<String,Object> properties) throws EntityNotFoundException {
		put(get(key),properties);
	}
	
	/**
	 * Deletes an entity with given key from the database.
	 * 
	 * @param 	key 
	 * 			The key of the entity that have to be deleted.
	 * @post	key is deleted
	 * 			| datastore.get(key) == null
	 */
	public void delete(Key key) {
		datastore.delete(key);
	}
	
	/**
	 * Returns all the entities with given parent-key and kind.
	 * 
	 * @param 	parent 
	 * 			The parent-key of all the entities.
	 * @param 	kind 
	 * 			The kind of all the entities.
	 * @return 	List with all the entities with given kind and parent-key.
	 */
	public List<Entity> getAll(Key parent, String kind) {
		Query query = new Query(kind, parent);
		return datastore.prepare(query).asList(FetchOptions.Builder.withLimit(INFINITY));
	}
}