package cs330.lab01.domain;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs330.lab01.App;

public class Transcript {
	
	protected Student student;
	protected List<Course> coursesTaken;
	protected Map<Course, Double> gradePointsEarned;
	protected Map<Course, String> letterGradeEarned;
	protected double gpa;
	
	/**
	 * @param student
	 * @param coursesTaken
	 * @param gradePointsEarned
	 */
	public Transcript(Student student, List<Course> coursesTaken, Map<Course, Double> gradePointsEarned, Map<Course, String> letterGradeEarned) {
		super();
		this.student = student;
		this.coursesTaken = coursesTaken;
		this.gradePointsEarned = gradePointsEarned;
		this.letterGradeEarned = letterGradeEarned;
		calcGPA();
	}

	public Transcript() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Calculates the GPA based on the coursesTaken and gradePointsEarned
	 */
	protected void calcGPA() {
		if (this.coursesTaken.size() > 0 && this.gradePointsEarned.size() > 0 && this.coursesTaken.size() == this.gradePointsEarned.size()) {
			double sum = 0;
			for (Course course: coursesTaken) {
				sum += gradePointsEarned.get(course);
			}
			
			this.gpa = sum / (double) (coursesTaken.size());
			
		} else {
			this.gpa = 0;
		}
		
	}

	public List<Course> getCoursesTaken() {
		return coursesTaken;
	}

	public double getGpa() {
		return gpa;
	}

	public Map<Course, Double> getGradePointsEarned() {
		return gradePointsEarned;
	}

	public Student getStudent() {
		return student;
	}

	public void setCoursesTaken(List<Course> coursesTaken) {
		this.coursesTaken = coursesTaken;
		calcGPA();
	}
	
	public void setGpa(double gpa) {
		this.gpa = gpa;
	}
	
	public void setGradePointsEarned(Map<Course, Double> gradePointsEarned) {
		this.gradePointsEarned = gradePointsEarned;
		calcGPA();
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Map<Course, String> getLetterGradeEarned() {
		return letterGradeEarned;
	}

	public void setLetterGradeEarned(Map<Course, String> letterGradeEarned) {
		this.letterGradeEarned = letterGradeEarned;
	}

	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		
		for (Course course : coursesTaken) {
			str.append(course);
			str.append(" : ");
			str.append(gradePointsEarned.get(course));
			str.append("\n");
		}
		
		return str.toString();
	}
	
	public int getTotCreds() {
		
		int creds = 0;
		
		for (Course c : coursesTaken) {
			
			if (gradePointsEarned.get(c).compareTo(App.PASSING_GRADE) >= 0) {		// if they didn't fail
				creds += c.getCredits();
			}
			
		}
		
		return creds;
		
	}
	
	/**
	 * List of the semester and year combinations in the transcript
	 * 
	 * @return
	 */
	public List<String[]> getSemestersAndYears() {
		Map<String, Integer> rtnSet = new HashMap<>();
		for (Course c : coursesTaken) {
			rtnSet.put(c.getSemester() + "," + c.getYear(), 0);
		}
		
		List<String> rtnList = new ArrayList<>(rtnSet.keySet());
		
		rtnList.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
			
		});

		List<String[]> semesterAndYear = new ArrayList<>();
		
		for (String str : rtnList) {
			semesterAndYear.add(str.split(","));
		}
		
		
		return semesterAndYear;
	}
	
	

}
