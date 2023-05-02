package cs330.lab01.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import cs330.lab01.domain.Student;

public class StartupDao extends C3poConnectionHandler {
	
	static {
		
		/*
		 * Create a grades table in the database based on AC grade boundaries
		 */
		try {
			Connection conn = C3poConnectionHandler.getConnection();
			
			PreparedStatement stmt = conn.prepareStatement("drop table if exists grades;");
			
			stmt.executeUpdate();
			
			stmt = conn.prepareStatement("create table grades (let_grade varchar(3) primary key, gp numeric(1,1) not null);");
			
			stmt.executeUpdate();
			
			String[] let_grades = {"A+", "A ", "A-", "B+", "B ", "B-", "C+", "C ", "C-", "D+", "D ", "D-", "F "};
			double[] grade_points = {4.0, 4.0, 3.7, 3.3, 3.0, 3.7, 2.3, 2.0, 1.7, 1.3, 1.0, 0.7, 0.0};
			
			for (int i = 0; i < let_grades.length; i++) {
				stmt = conn.prepareStatement("insert into grades (let_grade, gp) values(?, ?);");
				
				stmt.setString(1, let_grades[i]);
				stmt.setDouble(2, grade_points[i]);
				
				stmt.executeUpdate();
				
				stmt = conn.prepareStatement("select * from grades where let_grade = ?");
				stmt.setString(1, let_grades[i]);
				ResultSet rs = stmt.executeQuery();
				System.out.println(rs.getString("let_grade") + " : " + rs.getDouble("gp"));
			}
			
			stmt.close();
			conn.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		DBStudentDao sDao = new DBStudentDao();
		
		List<Student> students = sDao.getAllStudents();
		
		for (Student s : students) {
			
			s.setTotCred(s.getTranscript().getTotCreds());		// have transcript calculate the students total credits and set the students total creadits
			
			sDao.updateStudent(s);
			
		}
		
	}
}
