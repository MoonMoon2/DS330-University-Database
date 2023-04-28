package cs330.lab01.domain;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {

	private static final long serialVersionUID = 5724166683063692633L;

	int id;
	String name, dept_name;
	int tot_cred;
	Instructor advisor;


	/**
	 * 
	 */
	public Student() {
		super();
	}

	/**
	 * @param id
	 * @param name
	 * @param dept_name
	 * @param tot_cred
	 * @param advisor
	 */
	public Student(int id, String name, String dept_name, int tot_cred, Instructor advisor) {
		super();
		this.id = id;
		this.name = name;
		this.dept_name = dept_name;
		this.tot_cred = tot_cred;
		this.advisor = advisor;
	}

	/*
	 * 					GETTERS AND SETTERS
	 */

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

	public int getTot_cred() {
		return tot_cred;
	}

	public void setTot_cred(int tot_cred) {
		this.tot_cred = tot_cred;
	}

	public Instructor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(Instructor advisor) {
		this.advisor = advisor;
	}

	/*
	 *			HASHCODE AND EQUALS 
	 */

	@Override
	public int hashCode() {
		return Objects.hash(advisor, dept_name, id, name, tot_cred);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		return Objects.equals(advisor, other.advisor) && Objects.equals(dept_name, other.dept_name) && id == other.id
				&& Objects.equals(name, other.name) && tot_cred == other.tot_cred;
	}







}
