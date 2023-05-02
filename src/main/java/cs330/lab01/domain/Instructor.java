package cs330.lab01.domain;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Dumb data-wrapper for instructor objects.
 * 
 * Keeps a record of advisees as well.
 * 
 * @author Tobias Ward
 *
 */
public class Instructor implements Serializable {


	private static final long serialVersionUID = 4411008670103001638L;

	int id;
	String name, deptName;
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
	 * @param deptName
	 * @param salary
	 */
	public Instructor(int id, String name, String deptName, double salary) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.salary = salary;
		
		advisees = new ArrayList<Student>();
	}



	/**
	 * @param id
	 * @param name
	 * @param deptName
	 * @param salary
	 * @param advisees
	 */
	public Instructor(int id, String name, String deptName, double salary, List<Student> advisees) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.salary = salary;
		this.advisees = advisees;
	}

	/*
	 * 	Getters, Setters, equals, and hashCode
	 */

	public void addAdvisee(Student student) {
		advisees.add(student);
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
		return Objects.equals(deptName, other.deptName) && id == other.id && Objects.equals(name, other.name)
				&& Double.doubleToLongBits(salary) == Double.doubleToLongBits(other.salary);
	}

	public List<Student> getAdvisees() {
		return advisees;
	}

	public String getDeptName() {
		return deptName;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	@Override
	public int hashCode() {
		return Objects.hash(deptName, id, name, salary);
	}

	public void removeAdvisee(Student student) {
		advisees.remove(student);
	}

	public void setAdvisees(List<Student> advisees) {
		this.advisees = advisees;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return id + ", " + name + ", " + deptName;
	}


	


}
