package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import cs330.lab01.domain.Instructor;

public class DBInstructorDao extends C3poConnectionHandler implements InstructorDao {
	
	@Override
	public Instructor getInstructor(int id) {
		/*
		 * Create the connection and use a preparedstatement to insert the instructor ID into the select statement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select * from instructor where id = ?");
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			Instructor rtnInstructor = generateInstructorFromResultSet(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			return rtnInstructor;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Instructor> getAllInstructors() {
		
		/*
		 * Create a list of instructors to return 
		 */
		List<Instructor> rtnList = new ArrayList<>();
		
		/*
		 * Create the connection and use a preparedstatement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select * from instructor");
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				rtnList.add(generateInstructorFromResultSet(rs));
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
	public boolean refreshInstructor(Instructor instr) {
		/*
		 * 
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select * from instructor where id = ?");
			
			statement.setInt(1, instr.getId());
			
			ResultSet rs = statement.executeQuery();
			
			instr = generateInstructorFromResultSet(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean createInstructor(Instructor instr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateInstructor(Instructor instr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteInstructor(Instructor instr) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteInstructorById(int id) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private Instructor generateInstructorFromResultSet(ResultSet rs) throws SQLException {
		return new Instructor(rs.getInt("ID"), rs.getString("name"), rs.getString("dept_name"), rs.getDouble("salary"));
	}

}
