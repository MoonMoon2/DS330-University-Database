package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import cs330.lab01.domain.Instructor;

public class DBInstructorDao extends C3poConnectionHandler implements InstructorDao {
	
	protected Map<Integer, Instructor> instructors = new HashMap<>();
	
	@Override
	public Instructor getInstructor(int id) {
		
		if (instructors.containsKey(id)) {
			Instructor instruct = instructors.get(id);
			refreshInstructor(instruct);
			return instruct;
		}
		
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
			
			instructors.put(id, rtnInstructor);
			
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
			
			/*
			 * For each result
			 */
			while (rs.next()) {
				/*
				 * Generate an instructor from the result set
				 */
				Instructor newI = generateInstructorFromResultSet(rs);
				
				/*
				 * If the newI is already in instructors, return the already existing version
				 * else add it to instructors
				 */
				if (instructors.containsKey(newI.getId())) {
					newI = instructors.get(newI.getId());
				} else {	
					instructors.put(newI.getId(), newI);
				}
				
				rtnList.add(newI);		// add the instructor to the return list either way
				
				
				
			}
			
			rs.close();
			statement.close();
			conn.close();
			
			return rtnList;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;			// if there is some error, return null
	}

	@Override
	public boolean refreshInstructor(Instructor instr) {
		/*
		 * 
		 */
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for selecting by id, with one field left blank and replaced using setInt
			 */
			PreparedStatement statement = conn.prepareStatement("select * from instructor where id = ?");
			
			statement.setInt(1, instr.getId());
			
			ResultSet rs = statement.executeQuery();
			
			Instructor newInstr = generateInstructorFromResultSet(rs);
			
			rs.close();
			statement.close();
			conn.close();
			
			instr.setDeptName(newInstr.getDeptName());
			instr.setName(newInstr.getName());
			instr.setSalary(newInstr.getSalary());
			
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean createInstructor(Instructor instr) {
		/*
		 * 
		 */
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for creating an instructor, with the 4 data rows left unfilled
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("insert into instructor (id, name, dept_name, salary) values(?, ?, ?, ?)");
			
			statement.setInt(1, instr.getId());
			statement.setString(2, instr.getName());
			statement.setString(3, instr.getDeptName());
			statement.setDouble(4, instr.getSalary());
			
			statement.executeUpdate();
			
			
			/*
			 * Verify that the change happened
			 * 
			 * WARN: Could cause problem if the wrong person was gotten and made a bad record in the instructors list
			 */
			if (!instr.equals(getInstructor(instr.getId()))) return false;

			statement.close();
			conn.close();
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Instructor could not be created due to: ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean updateInstructor(Instructor instr) {
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for updating an instructor, with the 4 data rows left unfilled
			 * 
			 * Built to be user friendly using the insert or update to make it super easy to user
			 * and also easier to program than an explicit update
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("update instructor set name = ?, dept_name = ?, salary = ? where id = ?");
			
			statement.setString(1, instr.getName());
			statement.setString(2, instr.getDeptName());
			statement.setDouble(3, instr.getSalary());
			statement.setInt(4, instr.getId());
			
			statement.executeUpdate();
			
			/*
			 * Verify that the change happened
			 */
			if (!instr.equals(getInstructor(instr.getId()))) return false;

			statement.close();
			conn.close();
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Instructor could not be created due to: ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteInstructor(Instructor instr) {
		return deleteInstructorById(instr.getId());
	}

	@Override
	public boolean deleteInstructorById(int id) {
		try {
			Connection conn = super.getConnection();
			
			/*
			 * SQLite statement for updating an instructor, with the 4 data rows left unfilled
			 * 
			 * Built to be user friendly using the insert or update to make it super easy to user
			 * and also easier to program than an explicit update
			 * 
			 */
			PreparedStatement statement = conn.prepareStatement("delete from instructor where id = ?");
			
			instructors.remove(id);
			
			statement.setInt(1, id);
			
			statement.executeUpdate();

			statement.close();
			conn.close();
			
			return true;
			
		} catch (SQLException e) {
			System.err.print("Instructor could not be deleted because ");
			System.err.println(e.getSQLState());
			e.printStackTrace();
		}
		
		return false;
	}
	
	private Instructor generateInstructorFromResultSet(ResultSet rs) throws SQLException {
		return new Instructor(rs.getInt("ID"), rs.getString("name"), rs.getString("dept_name"), rs.getDouble("salary"));
	}

}
