package cs330.lab01.domain;

import java.io.Serializable;
import java.util.Objects;

public class Course implements Serializable {
	
	private static final long serialVersionUID = 1859582728465136180L;
	
	int id;
	String title, dept_name;
	int credits;
	
	
	/**
	 * @param id
	 * @param title
	 * @param dept_name
	 * @param credits
	 */
	public Course(int id, String title, String dept_name, int credits) {
		super();
		this.id = id;
		this.title = title;
		this.dept_name = dept_name;
		this.credits = credits;
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


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDept_name() {
		return dept_name;
	}


	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}


	public int getCredits() {
		return credits;
	}


	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	/*
	 *			HASHCODE AND EQUALS 
	 */
	
	@Override
	public int hashCode() {
		return Objects.hash(credits, dept_name, id, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(dept_name, other.dept_name) && id == other.id
				&& Objects.equals(title, other.title);
	}
	
}
