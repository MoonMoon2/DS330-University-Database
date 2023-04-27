package cs330.lab01.domain;

import java.io.Serializable;
import java.util.List;

public class Instructor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String id, name, dept_name;
	double salary;
	
	List<Student> advisees;
	
	
}
