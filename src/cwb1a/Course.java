package cwb1a;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a course of the university. A course contains a name, id, an instructor and an amount of studyPoints.
 * The course is also a data form that can be stored in the database. Students take on courses and can only execute a 
 * Study, College or Practice Activity for the courses they are enrolled in. Course is a subclass of Division.
 * 
 * @author Aurora
 * @version Griffin
 *
 */
public class Course extends Division
{
	
	//The name of the course.
	private String courseName;
	//The (main) instructor(s) of the course. They will be separated by comma's and "en". 
	private String instructor;
	//The amount of study-points of the course.
	private long studyPoints;
	
	/**
	 * Creates a new Course object.
	 * 
	 * @param 	courseName 
	 * 			The name of the course.
	 * @param 	instructor 
	 * 			The instructor of the course.
	 * @param 	courseId 
	 * 			The Id of the course.
	 * @param 	studyPoints 
	 * 			The amount of study-points of the course.
	 */
    public Course(String courseName, String instructor, String courseId, long studyPoints) {
    	super(courseId, DivisionFactory.COURSE);
    	this.courseName = courseName;
    	this.instructor = instructor;
    	this.studyPoints = studyPoints;
        }
    
    /**
     * Creates a new Course object using a Map with the values for the fields.
     * Be warned their is no control if the keys do or don't match the name of the fields.
     * 
     * @param 	properties 
     * 			A Map containing the next keys: "courseName" with String, "instructor" with String, "courseId" with String, "studyPoints" with Long
     */
    public Course(Map<String,Object> properties) {
    	super(properties);
    	courseName = (String) properties.get("courseName");
    	instructor = (String) properties.get("instructor");
    	studyPoints = (Long) properties.get("studyPoints");
    }
    
    /**
     * Returns a HashMap with the fields of the course and the names of the fields as corresponding key.
     * 
     * @return 	A HashMap containing the fields of the course and the names of the fields as corresponding key.
     */
    public HashMap<String,Object> getProperties() {
    	HashMap<String, Object> properties = super.getProperties();
    	
    	properties.put("courseName", courseName);
    	properties.put("instructor", instructor);
    	properties.put("studyPoints",studyPoints);
    	    	
    	return properties;
    }
    
    /**
     * Returns the name of the course.
     * 
     * @return 	The name of the course.
     */
    public String getCourseName() {
    	return courseName;
    }
    
    /**
     * Returns the instructor(s) of the course.
     * 
     * @return 	The instructor(s) of the course.
     */
    public String getInstructor() {
    	return instructor;
    }
    
    /**
     * Returns the amount of study-points of the course.
     * 
     * @return 	The amount of study-points of the course.
     */
    public long getStudyPoints() {
    	return studyPoints;
    }
}