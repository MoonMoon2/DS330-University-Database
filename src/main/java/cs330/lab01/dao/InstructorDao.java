package cs330.lab01.dao;

import java.util.List;

import cs330.lab01.domain.Instructor;

/**
 * Interface for all Instructor DAOs. Each DAO must have these methods
 * 
 * @author Tobias Ward
 *
 */
public interface InstructorDao {
	
	/**
	 * Get a specific Instructor from the database by the ID. If it does not exist, 
	 * throws an ObjectNotFoundException
	 * 
	 * @param id id of the Instructor you want to find
	 * @return
	 */
	public Instructor getInstructor(int id);
	
	/**
	 * Returns a list of all Instructors in the database, in the order that SQL returns them
	 * 
	 * @return
	 */
	public List<Instructor> getAllInstructors();
	
	/**
	 * Update the Instructor object to match the version in the database 
	 * (refresh your Instructor object with any information in the database)
	 * 
	 * @param stud
	 * @return
	 */
	public boolean refreshInstructor(Instructor stud);
	
	/**
	 * Create a Instructor record in the data structure for the given Instructor object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean createInstructor(Instructor stud);
	
	/**
	 * Update a Instructor record matching the Instructor object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean updateInstructor(Instructor stud);
	
	/**
	 * Deletes a Instructor record from the data matching the Instructor passed in
	 * 
	 * @param stud
	 * @return
	 */
	public boolean deleteInstructor(Instructor stud);
	
	/**
	 * Delete a Instructor record matching the Instructor ID
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteInstructorById(int id);
	
}
