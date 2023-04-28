package cs330.lab01.dao;

import java.util.List;

import cs330.lab01.domain.Student;

/**
 * Interface for all student DAOs. Each DAO must have these methods
 * 
 * @author Tobias Ward
 *
 */
public interface StudentDao {
	
	/**
	 * Get a specific student from the database by the ID. If it does not exist, 
	 * throws an ObjectNotFoundException
	 * 
	 * @param id id of the student you want to find
	 * @return
	 */
	public Student getStudent(int id);
	
	/**
	 * Returns a list of all students in the database, in the order that SQL returns them
	 * 
	 * @return
	 */
	public List<Student> getAllStudents();
	
	/**
	 * Update the student object to match the version in the database 
	 * (refresh your student object with any information in the database)
	 * 
	 * @param stud
	 * @return
	 */
	public boolean refreshStudent(Student stud);
	
	/**
	 * Create a student record in the data structure for the given student object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean createStudent(Student stud);
	
	/**
	 * Update a student record matching the student object
	 * 
	 * @param stud
	 * @return
	 */
	public boolean updateStudent(Student stud);
	
	/**
	 * Deletes a student record from the data matching the student passed in
	 * 
	 * @param stud
	 * @return
	 */
	public boolean deleteStudent(Student stud);
	
	/**
	 * Delete a student record matching the student ID
	 * 
	 * @param id
	 * @return
	 */
	public boolean deleteStudentById(int id);
	
}
