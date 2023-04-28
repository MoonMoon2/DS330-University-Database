package cs330.lab01.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Instructor implements Serializable {


	private static final long serialVersionUID = 4411008670103001638L;

	int id;
	String name, dept_name;
	double salary;

	List<Student> advisees;

	/**
	 * 
	 */
	public Instructor() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param dept_name
	 * @param salary
	 * @param advisees
	 */
	public Instructor(int id, String name, String dept_name, double salary, List<Student> advisees) {
		super();
		this.id = id;
		this.name = name;
		this.dept_name = dept_name;
		this.salary = salary;
		this.advisees = advisees;
	}

	/*
	 * 					GETTERS AND SETTERS
	 */

	public Instructor(int int1, String string, String string2, double double1) {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public List<Student> getAdvisees() {
		return advisees;
	}

	public void setAdvisees(List<Student> advisees) {
		this.advisees = advisees;
	}
	
	public void addAdvisees(Student ... students) {
		for (Student stud : students) {
			advisees.add(stud);
		}
	}
	
	public void removeAdvisees(Student ... students) {
		for (Student stud : students) {
			advisees.remove(stud);
		}
	}

	/*
	 *			HASHCODE AND EQUALS 
	 */

	@Override
	public int hashCode() {
		return Objects.hash(advisees, dept_name, id, name, salary);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Instructor other = (Instructor) obj;
		return Objects.equals(advisees, other.advisees) && Objects.equals(dept_name, other.dept_name)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}




}
