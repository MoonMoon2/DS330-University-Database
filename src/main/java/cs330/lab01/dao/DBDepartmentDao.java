package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import cs330.lab01.domain.Course;

public class DBDepartmentDao extends C3poConnectionHandler implements DepartmentDao {

	@Override
	public List<String> getAllDepartmentNames() {
		
		List<String> rtnList = new ArrayList<>();
		
		/*
		 * Create the connection and use a preparedstatement to insert the instructor ID into the select statement
		 */
		try {
			Connection conn = super.getConnection();
			
			PreparedStatement statement = conn.prepareStatement("select dept_name from department");
			
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				rtnList.add(rs.getString("dept_name"));
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

}
