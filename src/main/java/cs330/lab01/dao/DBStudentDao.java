package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cs330.lab01.domain.Instructor;
import cs330.lab01.domain.Student;
import cs330.lab01.domain.Transcript;
import cs330.lab01.utils.ObjectNotFoundException;

public class DBStudentDao extends C3poConnectionHandler implements StudentDao {

	@Override
	public Student getStudent(int id) {
		Student rtnStud = new Student();
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
			 * select s.id, s.name, s.dept_name, s.tot_cred, a.i_id
			 * from student s inner join advisor a
			 * on s.id = a.s_id
			 * where s.id = ?
			 */
			PreparedStatement statement = conn.prepareStatement("select s.id, s.name, s.dept_name, s.tot_cred, a.i_id from student s inner join advisor a on s.id = a.s_id where s.id = ?");
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();

			rtnStud = createStudFromRS(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rtnStud;
	}

	@Override
	public List<Student> getAllStudents() {
		ArrayList<Student> rtnStuds = new ArrayList<>();
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
			 * select s.id, s.name, s.dept_name, s.tot_cred, a.i_id
			 * from student s inner join advisor a
			 * on s.id = a.s_id
			 * where s.id = ?
			 */
			PreparedStatement statement = conn.prepareStatement("select s.id, s.name, s.dept_name, s.tot_cred, a.i_id from student s inner join advisor a on s.id = a.s_id");
			
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				rtnStuds.add(createStudFromRS(rs));
			}
			
			
			rs.close();
			statement.close();
			conn.close();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rtnStuds;
	}

	@Override
	public boolean refreshStudent(Student stud) {
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
			 * select s.id, s.name, s.dept_name, s.tot_cred, a.i_id
			 * from student s inner join advisor a
			 * on s.id = a.s_id
			 * where s.id = ?
			 */
			PreparedStatement statement = conn.prepareStatement("select s.id, s.name, s.dept_name, s.tot_cred, a.i_id from student s inner join advisor a on s.id = a.s_id where s.id = ?");
			
			statement.setInt(1, stud.getId());
			
			ResultSet rs = statement.executeQuery();

			Student rtnStud = createStudFromRS(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			stud.setAdvisor(rtnStud.getAdvisor());
			stud.setDeptName(rtnStud.getDeptName());
			stud.setName(rtnStud.getName());
			stud.updateTranscript(rtnStud.getTranscript());
			stud.setTotCred(rtnStud.getTotCred());

			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}

	@Override
	public boolean createStudent(Student stud) {
		if (stud.getAdvisor() == null) {
			return false;						// fail if the student has no advisor
		}
		
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for creating an instructor, with the 4 data rows left unfilled
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("insert into student (id, name, dept_name, tot_cred) values(?, ?, ?, ?)");
			
			statement.setInt(1, stud.getId());
			statement.setString(2, stud.getName());
			statement.setString(3, stud.getDeptName());
			statement.setDouble(4, stud.getTotCred());
			
			statement.executeUpdate();
			
			statement = conn.prepareStatement("insert into advisor (s_id, i_id) values (?, ?)");
			
			statement.setInt(1, stud.getId());
			statement.setInt(2, stud.getAdvisor().getId());
			
			statement.executeUpdate();
			
			statement.close();
			conn.close();
			
			/*
			 * Verify that the change happened
			 * 
			 * WARN: Could cause problem if the wrong person was gotten and made a bad record in the instructors list
			 */
			if (!stud.equals(getStudent(stud.getId()))) return false;
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Student could not be created due to: ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateStudent(Student stud) {
		if (stud.getAdvisor() == null) {
			return false;						// fail if the student has no advisor
		}
		
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for creating an instructor, with the 4 data rows left unfilled
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("update student set name = ?, dept_name = ?, tot_cred = ? where id = ?");
			
			statement.setString(1, stud.getName());
			statement.setString(2, stud.getDeptName());
			statement.setDouble(3, stud.getTotCred());
			statement.setInt(4, stud.getId());
			
			statement = conn.prepareStatement("update advisor set i_id = ? where s_id = ?");
			
			statement.setInt(2, stud.getId());
			statement.setInt(1, stud.getAdvisor().getId());
			
			statement.executeUpdate();
			
			/*
			 * Could update courses taken to match the transcript here, but I do not have the time
			 * to do it before tuesday
			 */
			
			statement.close();
			conn.close();
			
			/*
			 * Verify that the change happened
			 * 
			 */
			if (!stud.equals(getStudent(stud.getId()))) return false;
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Student could not be created due to: ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteStudent(Student stud) {
		return deleteStudentById(stud.getId());
	}

	@Override
	public boolean deleteStudentById(int id) {
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for updating an instructor, with the 4 data rows left unfilled
			 * 
			 * Built to be user friendly using the insert or update to make it super easy to user
			 * and also easier to program than an explicit update
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("delete from student where id = ?");
			
			statement.setInt(1, id);
			
			statement.executeUpdate();

			statement.close();
			conn.close();
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Student could not be deleted because ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Creates a student object from a given result set.
	 * 
	 * The result set must include student id as "id", "name", "dept_name", "tot_cred", and advisor.i_id as "i_id"
	 * 
	 * @param rs
	 * @return
	 * @throws SQLException
	 */
	protected Student createStudFromRS(ResultSet rs) throws SQLException {
		Instructor inst = HandleManager.iDao.getInstructor(rs.getInt("i_id"));
		Transcript trans = new Transcript();
		try {
			trans = HandleManager.tDao.getStudentTranscriptById(rs.getInt("id"));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		return new Student(rs.getInt("id"), rs.getString("name"), rs.getString("dept_name"), rs.getInt("tot_cred"), inst, trans);
	}
}
