package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import cs330.lab01.domain.Course;
import cs330.lab01.domain.Student;
import cs330.lab01.domain.Transcript;
import cs330.lab01.utils.ObjectNotFoundException;

/**
 * @author Tobias Ward
 *
 */
public class DBTranscriptDao extends C3poConnectionHandler implements TranscriptDao {

	/**
	 * Returns a fully configured student transcript object using data from the database
	 */
	@Override
	public Transcript getStudentTranscript(Student student) throws ObjectNotFoundException {
		
		Transcript trans = getStudentTranscriptById(student.getId());
		trans.setStudent(student);
		
		return trans;
	}

	/**
	 * Returns a transcript with null as the student, but with the provided student's transcript fully configured otherwise
	 * 
	 * If no student matching the ID is found, it will throw an objectNotFoundException
	 * 
	 * @param studentId integer student ID corresponding to a student in the database
	 * 
	 * @throws ObjectNotFoundException when the given student ID is not found in the database
	 */
	@Override
	public Transcript getStudentTranscriptById(int studentId) throws ObjectNotFoundException{
		/*
		 * Create the connection and use a preparedstatement to insert the student ID
		 */
		try {
			Connection conn = super.getConnection();
			
			/*
			 * Select all of the necessary pieces required to build a transcript
			 * 
			 * this is the full sql prompt
			 * 
			 * select t.course_id, t.id as stud_id, t.sec_id, t.semester, t.year, 
			 * g.gp, c.title, c.dept_name, c.credits 
			 * from takes t inner join grades g 
			 * on t.grade = g.let_grade 
			 * inner join course c 
			 * on t.course_id = c.course_id 
			 * where stud_id = ?
			 */
			PreparedStatement statement = conn.prepareStatement("select t.course_id, t.id as stud_id, t.sec_id, t.semester, t.year, g.gp, c.title, c.dept_name, c.credits from takes t inner join grades g on t.grade = g.let_grade inner join course c on t.course_id = c.course_id where stud_id = ?");
			
			statement.setInt(1, studentId);
			
			ResultSet rs = statement.executeQuery();
			
			List<Course> courseList = new ArrayList<>();
			List<Double> gradesList = new ArrayList<>();
			
			/*
			 * Populate two lists from the resultSet
			 */
			while (rs.next()) {
				courseList.add(generateCourseFromResultSet(rs));
				gradesList.add(rs.getDouble("gp"));
			}
			
			rs.close();
			statement.close();
			conn.close();
			
			Map<Course, Double> gradesMap = createCourseMapping(courseList, gradesList);
			
			return new Transcript(null, courseList, gradesMap);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		throw new ObjectNotFoundException("Could not find a student with any courses taken matching the provided id");
	}
	
	
	
	/**
	 * Takes two lists with matching length and turns them into a map from courses to gradePoints
	 * 
	 * @param courses a list of courses to be mapped
	 * @param gradePoints a list of doubles to be mapped onto
	 * @return a map from courses to gradePoints
	 */
	private Map<Course, Double> createCourseMapping(List<Course> courses, List<Double> gradePoints) {
		Map<Course, Double> map = new HashMap<>();
		
		
		for (int i = 0; i < courses.size(); i++) {
			map.put(courses.get(i), gradePoints.get(i));
		}
		
		return map;
	}
	
	/**
	 * Fully parameterized constructor call using a given result set
	 * 
	 * @param rs resultset containing all of the necesarry fields for a course
	 * @return the course object
	 * @throws SQLException if there is a mistake in the titles it will throw an sql exception
	 */
	private Course generateCourseFromResultSet(ResultSet rs) throws SQLException {
		return new Course(rs.getInt("course_id"), rs.getInt("sec_id"), rs.getString("title"), rs.getString("dept_name"), rs.getString("semester"), rs.getInt("year"), rs.getInt("credits"));
	}

}
