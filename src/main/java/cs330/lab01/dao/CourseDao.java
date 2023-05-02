package cs330.lab01.dao;

import java.util.List;

import cs330.lab01.domain.Course;

/**
 * Interface for all Course DAOs. Each DAO must have these methods
 * 
 * @author Tobias Ward
 *
 */
public interface CourseDao {
	
	/**
	 * Get a specific Course from the database by the ID. If it does not exist, 
	 * throws an ObjectNotFoundException
	 * 
	 * @param id id of the Course you want to find
	 * @return
	 */
	Course getCourse(int course_id, int sec_id, String semester, int year);
	
	/**
	 * Returns a list of all Courses in the database, in the order that SQL returns them
	 * 
	 * @return
	 */
	public List<Course> getAllCourses();
	
	/**
	 * Update the Course object to match the version in the database 
	 * (refresh your Course object with any information in the database)
	 * 
	 * @param stud
	 * @return
	 */
	public boolean refreshCourse(Course stud);
	
	/**
	 * Create a Course record in the data structure for the given Course object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean createCourse(Course stud);
	
	/**
	 * Update a Course record matching the Course object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean updateCourse(Course stud);
	
	/**
	 * Deletes a Course record from the data matching the Course passed in
	 * 
	 * @param stud
	 * @return
	 */
	public boolean deleteCourse(Course stud);
	
	/**
	 * Delete a Course record matching the Course ID
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteCourseById(int course_id, int sec_id, String semester, int year);
	
}
