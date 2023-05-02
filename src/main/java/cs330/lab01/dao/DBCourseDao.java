package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs330.lab01.domain.Course;

public class DBCourseDao extends C3poConnectionHandler implements CourseDao {

	@Override
	public Course getCourse(int course_id, int sec_id, String semester, int year) {
		/*
		 * Create the connection and use a preparedstatement to insert the instructor ID into the select statement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select course.course_id, sec_id, title, dept_name, semester, year, credits from course inner join section on course.course_id = section.course_id where course.course_id = ? and sec_id = ? and semester = ? and year = ?");
			
			statement.setInt(1, course_id);
			statement.setInt(2, sec_id);
			statement.setString(3, semester);
			statement.setInt(4, year);
			
			
			ResultSet rs = statement.executeQuery();
			
			Course rtnCourse = generateCourseFromResultSet(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			return rtnCourse;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Course> getAllCourses() {
		/*
		 * Create a list of instructors to return 
		 */
		List<Course> rtnList = new ArrayList<>();
		
		/*
		 * Create the connection and use a preparedstatement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select course.course_id, sec_id, title, dept_name, semester, year, credits from course inner join section on course.course_id = section.course_id");
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				rtnList.add(generateCourseFromResultSet(rs));
			}
			
			rs.close();
			statement.close();
			conn.close();
			
			return rtnList;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean refreshCourse(Course course) {
		/*
		 * Create the connection and use a preparedstatement to insert the instructor ID into the select statement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select course.course_id, sec_id, title, dept_name, semester, year, credits from course inner join section on course.course_id = section.course_id where course.course_id = ? and sec_id = ? and semester = ? and year = ?");
			
			statement.setInt(1, course.getCourseId());
			statement.setInt(2, course.getSecId());
			statement.setString(3, course.getSemester());
			statement.setInt(4, course.getYear());
			
			
			ResultSet rs = statement.executeQuery();
			
			Course rtnCourse = generateCourseFromResultSet(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			course.setCredits(rtnCourse.getCredits());
			course.setTitle(rtnCourse.getTitle());
			course.setDeptName(rtnCourse.getDeptName());
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean createCourse(Course stud) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCourse(Course stud) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteCourse(Course stud) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean deleteCourseById(int course_id, int sec_id, String semester, int year) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Course generateCourseFromResultSet(ResultSet rs) throws SQLException {
		return new Course(rs.getInt("course_id"), rs.getInt("sec_id"), rs.getString("title"), rs.getString("dept_name"), rs.getString("semester"), rs.getInt("year"), rs.getInt("credits"));
	}

}
