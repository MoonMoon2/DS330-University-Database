package cs330.lab01.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Data wrapper for student objects
 * 
 * Updates gpa and totCred based on coursesTaken List
 * 
 * @author Tobias Ward
 *
 */
public class Student implements Serializable {

	private static final long serialVersionUID = 5724166683063692633L;

	protected int id;
	protected String name, deptName;
	protected int totCred;
	protected Instructor advisor;
	protected Transcript trans;


	/**
	 * 
	 */
	public Student() {
		super();
	}

	/**
	 * Constructor for a student without totCred or gpa (if we haven't found that out yet)
	 * 
	 * @param id
	 * @param name
	 * @param deptName
	 * @param advisor
	 */
	public Student(int id, String name, String deptName, Instructor advisor) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.advisor = advisor;
		advisor.addAdvisee(this);
	}
	
	/**
	 * Constructor for a student without gpa 
	 * 
	 * @param id
	 * @param name
	 * @param deptName
	 * @param totCred
	 * @param advisor
	 */
	public Student(int id, String name, String deptName, int totCred, Instructor advisor) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.totCred = totCred;
		this.advisor = advisor;
		advisor.addAdvisee(this);
	}

	/**
	 * Full constructor - creates a student object with all of the options customized
	 * 
	 * @param id integer student id
	 * @param name string for the students last name
	 * @param deptName string for the department name
	 * @param totCred integer for the total number of credit hours
	 * @param gpa double of the students current GPA
	 * @param advisor instructor object for the advisor of the student
	 * @param coursesTaken a list of all the courses a student has taken
	 */
	public Student(int id, String name, String deptName, int totCred, Instructor advisor,
			Transcript trans) {
		super();
		this.id = id;
		this.name = name;
		this.deptName = deptName;
		this.totCred = totCred;
		this.advisor = advisor;
		advisor.addAdvisee(this);
		this.trans = trans;
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
		return Objects.equals(advisor, other.advisor) && Objects.equals(deptName, other.deptName) && id == other.id
				&& Objects.equals(name, other.name) && totCred == other.totCred;
	}

	public Instructor getAdvisor() {
		return advisor;
	}

	public List<Course> getCoursesTaken() {
		return trans.getCoursesTaken();
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public double getGpa() {
		return trans.getGpa();
	}

	public Map<Course, Double> getGradePointsEarned() {
		return trans.getGradePointsEarned();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTotCred() {
		return totCred;
	}

	public Transcript getTranscript() {
		return trans;
	}

	@Override
	public int hashCode() {
		return Objects.hash(advisor, deptName, id, name, totCred);
	}

	public void setAdvisor(Instructor advisor) {
		this.advisor = advisor;
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

	public void setTotCred(int totCred) {
		this.totCred = totCred;
	}

	public void updateTranscript(List<Course> coursesTaken, Map<Course, Double> gradesEarned) {
		this.trans.setCoursesTaken(coursesTaken);
		this.trans.setGradePointsEarned(gradesEarned);
	}
	
	public void updateTranscript(Transcript trans) {
		this.trans = trans;
	}
	
	
	
}
