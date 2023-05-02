package cs330.lab01.dao;

import cs330.lab01.domain.Student;

public class DataHolder {
	
	protected Student activeStudent;

	public Student getActiveStudent() {
		return activeStudent;
	}

	public void setActiveStudent(Student activeStudent) {
		this.activeStudent = activeStudent;
	}
	
	

}
