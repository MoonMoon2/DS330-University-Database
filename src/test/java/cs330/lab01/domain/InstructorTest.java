package cs330.lab01.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

class InstructorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	void testInstructor() {
		Instructor instructor = new Instructor();
		
		
	}

	@Test
	void testInstructorIntStringStringDouble() {
		fail("Not yet implemented");
	}

	@Test
	void testInstructorIntStringStringDoubleListOfStudent() {
		List<Student> advisees = new ArrayList<>();
		
		Instructor instructor = new Instructor(1, "Ward", "CS", 1000.30, advisees);
		
		new Student(34, "Bob", "CS", instructor);
	}

	@Test
	void testAddAdvisees() {
		fail("Not yet implemented");
	}

	@Test
	void testEqualsObject() {
		fail("Not yet implemented");
	}

	@Test
	void testRemoveAdvisees() {
		fail("Not yet implemented");
	}

	@Test
	void testSetAdvisees() {
		fail("Not yet implemented");
	}

	@Test
	void testSetDeptName() {
		fail("Not yet implemented");
	}

	@Test
	void testSetId() {
		fail("Not yet implemented");
	}

	@Test
	void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	void testSetSalary() {
		fail("Not yet implemented");
	}

}
