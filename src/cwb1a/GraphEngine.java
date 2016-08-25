package cwb1a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;
import java.util.Map;

import com.google.appengine.api.datastore.EntityNotFoundException;


/**
 * This is a class that creates matrices with certain data in order to create graphics.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class GraphEngine {
	//Design pattern singleton.
	private static GraphEngine instance = new GraphEngine();
	
	/**
	 * @note The constructor is private. You are not allowed to make an object of this class.
	 */
	private GraphEngine() {
	}
	
	/**
	 * This method returns the only possible operational instance of the GraphEngine class.
	 * 
	 * @return 	The only possible operational instance of the GraphEngine class.
	 */
	public static GraphEngine getInstance() {
	        return instance;
	    }
	
	/**
	 * Returns 1x2 matrix A 
	 * with A[0,0] contains the total duration spend on all activities of given type.
	 * with A[0,1] contains the total pause duration spend on all activities of given type.
	 * 
	 * @param 	user 
	 *        	The owner of the data.
	 * @param 	type 
	 *        	The type of the activity.
	 * @return 	The matrix A with the data corresponding the given user and the given type of activity.
	 */
	public long[] getSpecificActivityVSPause(User user, long type) {
		long[] data = {0,0};
		for(Activity lusActivity : ActivityRegistry.getInstance().getAllActivityUserType(user, type)) {
			data[0] = data[0] + lusActivity.getDuration();
			data[1] = data[1] + lusActivity.getPauseDuration();
		}
		return data;
	}
	
	/**
	 * Returns 7x2 matrix A
	 * with for every i in A[i,j] form 0 till 6 corresponding with the type of activity minus one.
	 * with A[i,0] contains the total duration spend on all activities of given type.
	 * with A[i,1] contains the total pause duration spend on all activities of given type.
	 * @param 	user 
	 *        	The owner of the data.
	 * @return 	The matrix A with the data corresponding the given user and the given type of activity.
	 */
	public long[][] getSeperateActivityVSPause(User user){
		long[][] data = new long[7][2];
		
		for(Activity element : ActivityRegistry.getInstance().getAllActivitiesUser(user.getId()))
		{
			int temp = (int) element.getType();
			
			data[temp-1][0]=data[temp-1][0] + element.getDuration();
			data[temp-1][1]=data[temp-1][1] + element.getPauseDuration();
		}
		return data;
	}
	
	/**
	 * Returns 1x7 matrix A
	 * with A[0,i] contains the total duration spend on all activities of type i minus one
	 * for i from 0 till 6.
	 * 
	 * @param 	user 
	 *        	The owner of the data.
	 * @return 	The matrix A with the data corresponding the given user.
	 */
	public long[] getSeperateActivityDuration(User user) {
		long[] data = {0,0,0,0,0,0,0};
		for(Activity element : ActivityRegistry.getInstance().getAllActivitiesUser(user.getId()))
		{
			int temp = (int) element.getType();
			data[temp-1]=data[temp-1] + element.getDuration();
		}
		return data;
	}
	
	/**
	 * Returns 1x7 matrix A
	 * with A[0,i] contains the total duration spend on all activities by all users of type i minus one
	 * for i from 0 till 6.
	 * 
	 * @return 	The matrix A with the data.
	 */
	public long[] getSeperateActivityDurationAll() {
		long[] data = {0,0,0,0,0,0,0};
		for(Activity lusActivity : ActivityRegistry.getInstance().getAll()) {
			int index = (int) lusActivity.getType() - 1;
			data[index] = data[index] + lusActivity.getDuration();
		}

		return data;
	}
	
	/**
	 * Returns yx3 matrix A
	 * with y the amount of different courses attended
	 * with A[i,0] the name of the course as String
	 * with A[i,1] the total duration spent on the course as long (Practice, College, Study)
	 * with A[i,2] the study-points of the course as long
	 * for i from 0 till y minus one
	 * 
	 * @param 	user 
	 *        	The owner of the data.
	 * @return 	The matrix A with the data corresponding the give user.
	 */
	public Object[][] getSeperateTimeVSStudyPoints(User user) {		
		ArrayList<Course> courses = (ArrayList<Course>) DivisionRegistry.getInstance().getAllUser(user.getId());
		
		int size = courses.size();
		
		System.out.println("size succes: "+size);
		
		Object[][] data = new Object[size][3];
		
		for(int i = 0; i < size; i++) {
			data[i][1] = new Long(0);
			data[i][2] = new Long(0);
		}
		
		for(Course element: courses)
		{
			int temp = getIndexCourse(courses, element);
			long duration = getDurationCourse(user, element, ActivityFactory.COLLEGE) + getDurationCourse(user, element, ActivityFactory.PRACTICE)
					+ getDurationCourse(user, element, ActivityFactory.STUDY);;
			
			data[temp][0] = element.getCourseName();
			data[temp][1]= (Long) data[temp][1] + duration;
			data[temp][2]= element.getStudyPoints();
		}
		return data;
	}
	
	/**
	 * Returns a size by 3 matrix, with
	 * 		(i,0): course name
	 * 		(i,1): average study time
	 * 		(i,3): study-points of the course
	 * warning: high complexity: many data must be collected from the database.
	 * @param user The user. Only the courses of the user will be calculated.
	 * @return a matrix, or null when an internal error occurs.
	 */
	public Object[][] getAverageTimeVSStudyPointsAllUsers(User user) {
		List<Course> courses = (List<Course>) DivisionRegistry.getInstance().getAllUser(user.getId());
		//System.out.println("amount of courses:" + courses.size() );
		Object[][] data = new Object[courses.size()][3];
		
		//data met 0 vullen.
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				data[i][j] = new Long(0);
			}
		}
		
		try {
			for(int i = 0; i < courses.size(); i++ ) {
				Course lusCourse = courses.get(i);
				//System.out.println("buitenlus:" + i +": " + lusCourse.getId());
				data[i][0] = lusCourse.getCourseName();
				data[i][2] = lusCourse.getStudyPoints();
				List<String> userIds = lusCourse.getStudentsId();
				System.out.println("amount of users:" + userIds.size());
				for(int j = 0; j < userIds.size(); j++) {
					User lusUser = UserRegistry.getInstance().get(userIds.get(j));
					//System.out.println("binnenlus:" + j);
					//System.out.println("user:" + lusUser.getId());
					long duration = 0;
					duration = duration + getDurationCourse(lusUser, lusCourse, ActivityFactory.COLLEGE);
					duration = duration + getDurationCourse(lusUser, lusCourse, ActivityFactory.PRACTICE);
					duration = duration + getDurationCourse(lusUser, lusCourse, ActivityFactory.STUDY);
					//System.out.println("totale duur:" + duration);
					data[i][1] = (Long) data[i][1] + duration; 
				}
				data[i][1] = (Long) data[i][1] / userIds.size();
			}
		}
		
		//when the lusUsers can not be collected from the database.
		catch(EntityNotFoundException e) {
			Object[][] error = new Object[1][2];
			error[0][0] = "error";
			error[0][1] = 0;
			error[0][2] = 0;
			return error;
		}
		
		return data;
	}
	
	/**
	 * Returns yx4 matrix A
	 * with y the amount of different courses attended
	 * with A[i,0] the course name as String
	 * with A[i,1] the total duration spent on the course as long (College)
	 * with A[i,2] the total duration spent on the course as long (Practice)
	 * with A[i,3] the total duration spent on the course as long (Study)
	 * for i from 0 till y minus one
	 * 
	 * @param 	user 
	 *        	The owner of the data.
	 * @return 	The matrix A with the data corresponding the give user.
	 */
	public Object[][] getSeperateTimeVSStudyPointsSeperateActivities(User user) {
		ArrayList<Course> courses = (ArrayList<Course>) DivisionRegistry.getInstance().getAllUser(user.getId());
		int size = courses.size();
		
		Object[][] data = new Object[size][4];
		//standaard de matrix met 0'en vullen.
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < data[i].length; j++) {
				data[i][j] = new Long(0);
			}
		}
		
		for(Course element: courses)
		{
			int temp = getIndexCourse(courses, element);
			data[temp][0] = element.getCourseName();
			data[temp][1]=(Long) data[temp][1] + getDurationCourse(user, element, ActivityFactory.COLLEGE);
			data[temp][2]=(Long) data[temp][2] + getDurationCourse(user, element, ActivityFactory.PRACTICE);
			data[temp][3]=(Long) data[temp][3] + getDurationCourse(user, element, ActivityFactory.STUDY);
		}
		return data;
	}
    
	/**
	 * Returns the index of a specific given course which is an element of a given arrayList of courses
	 * 
	 * @param 	courses
	 *        	The arrayList of courses.
	 * @param 	course
	 *        	The element of the arrayList for which the index has to be found.
	 * @return 	The index of the course in the arrayList.
	 */
	private int getIndexCourse(ArrayList<Course> courses, Course course) {
		int index = -1;
		boolean found = false;
		Iterator<Course> it = courses.iterator();
		
		while(found != true && it.hasNext() == true)
		{
			index++;
			if(it.next().equals(course))
			{
				found = true;
			}
		}
		
		return index;
	}
	
	/**
	 * Returns the total duration spent of a given user on a given course in learning time activities of a given type.
	 * @note 	There's no control if the type is compatible with learning time types.
	 * 
	 * @param 	user
	 *        	The user for which the duration has to be found.
	 * @param 	course
	 *        	The course for which the duration has to be found.
	 * @param 	learningType The type of the learningType element.
	 *        	The learning type for which the duration has to be found.
	 * @return 	The total duration spent of the given user on the given course in learning time activities of the given type.
	 */
	private long getDurationCourse(User user, Course course, long learningType) {
		long duration = 0;
		List<Activity> tempList =  ActivityRegistry.getInstance().getAllActivityUserType(user, learningType);
		for(Activity element: tempList)
		{
			LearningTime temp = (LearningTime) element;
			if(temp.getCourseId().equals(course.getId())) {
				duration = duration + element.getDuration();
			}
		}
		return duration;
	}
	
	/**
	 * Returns 1x8 matrix A
	 * with A[0,0] contains the total amount of users that aren't tracking an activity.
	 * with A[0,i] contains the total amount of users that are tracking an activity of type i
	 * for i from 1 till 7.
	 * 
	 * @return 	The matrix A with the data.
	 */
	public long[] getActiveActivities() {
		long[] data= {0,0,0,0,0,0,0,0};
		ArrayList<Activity> activeActivities = (ArrayList<Activity>) ActivityRegistry.getInstance().getActiveActivity();
		for(Activity element: activeActivities)
		{
			int temp = (int) element.getType();
			data[temp]++;
		}
		data[0]=UserRegistry.getInstance().getSize()-activeActivities.size();
		
		return data;
	}
	
	/**
     * Returns a map with 2D long arrays of the following form [number of the activity (type dependent); duration of the activity; rating or other long value (type dependent)]
     * with the Activity_fieldname as key.
     * More than one key for certain activityTypes is possible.
     * 
     * Integrated keys: College_usefulnessRate; College_position; Job_fatigueRate; Nightlife_alcoholRate; Practice_usefulnessRate; Practice_preparationFeelingRate;
     * Sleep_wakeUpRate; Sport_intensityRate; Sport_intensityRate; Study_quantityRate; Study_qualityRate.
     * 
     * @param 	user 
     * 			The user for which the data is collected.
     * @return 	The map containing the data.
     */
	public Map<String, long[][]> getRateData(User user) {
		final int LENGTH = 3;
		Map<String, long[][]> dataMap = new HashMap<String, long[][]>();
		dataMap.put("College_usefulnessRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.COLLEGE).size()][LENGTH]);
		dataMap.put("College_position", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.COLLEGE).size()][LENGTH]);
		dataMap.put("Job_fatigueRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.JOB).size()][LENGTH]);
		dataMap.put("Nightlife_alcoholRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.NIGHTLIFE).size()][LENGTH]);
		dataMap.put("Practice_usefulnessRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.PRACTICE).size()][LENGTH]);
		dataMap.put("Practice_preparationFeelingRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.PRACTICE).size()][LENGTH]);
		dataMap.put("Sleep_wakeUpRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.SLEEP).size()][LENGTH]);
		dataMap.put("Sport_intensityRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.SPORT).size()][LENGTH]);
		dataMap.put("Study_quantityRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.STUDY).size()][LENGTH]);
		dataMap.put("Study_qualityRate", new long[ActivityRegistry.getInstance().getAllActivityUserType(user, ActivityFactory.STUDY).size()][LENGTH]);
		int[] numeratorStock = {0,0,0,0,0,0,0};
		
		for(Activity element : ActivityRegistry.getInstance().getAllActivitiesUser(user.getId()))
		{
			int position = (int) element.getType()-1;
			dataMap = element.visitorBubble(dataMap, numeratorStock[position]);
			numeratorStock[position]++;
		}
		return dataMap;
	}
}