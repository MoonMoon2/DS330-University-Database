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
	 * throws a 
	 * 
	 * @param id
	 * @return
	 */
	public Student getStudent(int id);
	public List<Student> getAllStudents();
	public boolean createStudent(Student stud);
	public boolean updateStudent(Student stud);
	public boolean deleteStudent(Student stud);
	
}
