package cs330.lab01.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourseTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testCourse() {
		Course course = new Course();
		assertNotNull(course);
		assertNotNull(course.getCourseId());
		assertNotNull(course.getSecId());
		assertNotNull(course.getTitle());
		assertNotNull(course.getDeptName());
		assertNotNull(course.getSemester());
		assertNotNull(course.getYear());
		assertNotNull(course.getCredits());
		
		int[] intVars = {course.getCourseId(), course.getSecId(), course.getYear(), course.getCredits()};
		assertArrayEquals(new int[] {0, 0, 0, 0}, intVars);
		
		String[] strVars = {course.getDeptName(), course.getSemester(), course.getTitle()};
		assertArrayEquals(new String[] {"", "", ""}, strVars);
	}

	@Test
	void testCourseIntIntStringStringStringIntInt() {
		Course course = new Course(1, 2, "Course Title", "Dept Name", "Semester", 2020, 4);
		assertNotNull(course);
		assertNotNull(course.getCourseId());
		assertNotNull(course.getSecId());
		assertNotNull(course.getTitle());
		assertNotNull(course.getDeptName());
		assertNotNull(course.getSemester());
		assertNotNull(course.getYear());
		assertNotNull(course.getCredits());
		
		int[] intVars = {course.getCourseId(), course.getSecId(), course.getYear(), course.getCredits()};
		assertArrayEquals(new int[] {1, 2, 2020, 4}, intVars);
		
		String[] strVars = {course.getDeptName(), course.getSemester(), course.getTitle()};
		assertArrayEquals(new String[] {"Course Title", "Dept Name", "Semester"}, strVars);
	}

	
}
