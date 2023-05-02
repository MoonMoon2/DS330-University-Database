package cs330.lab01.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * Dumb Course Data Wrapper (does no calculations, just holds data)
 * 
 * @author Tobias Ward
 *
 */
public class Course implements Serializable {
	
	private static final long serialVersionUID = 1859582728465136180L;
	
	private int courseId, secId;
	private String title, deptName, semester;
	private int year, credits;
	
	
	
	/**
	 * Basic test course object
	 */
	public Course() {
		super();
		
	}

	/**
	 * @param courseId
	 * @param secId
	 * @param title
	 * @param deptName
	 * @param semester
	 * @param year
	 * @param credits
	 */
	public Course(int courseId, int secId, String title, String deptName, String semester, int year, int credits) {
		super();
		this.courseId = courseId;
		this.secId = secId;
		this.title = title;
		this.deptName = deptName;
		this.semester = semester;
		this.year = year;
		this.credits = credits;
	}
	
	/*
	 * 					GETTERS AND SETTERS
	 */

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public int getSecId() {
		return secId;
	}

	public void setSecId(int secId) {
		this.secId = secId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
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
		return Objects.hash(courseId, deptName, secId, semester, year);
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
		return courseId == other.courseId && Objects.equals(deptName, other.deptName) && secId == other.secId
				&& Objects.equals(semester, other.semester) && year == other.year;
	}
	
	
	
	
	
}
