package cs330.lab01.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cs330.lab01.domain.Transcript;
import cs330.lab01.utils.ObjectNotFoundException;

class DBTranscriptDaoTest {
	
	DBTranscriptDao dao;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
	}

	@BeforeEach
	void setUp() throws Exception {
		dao = new DBTranscriptDao();
	}

	@Test
	void testGetStudentTranscriptByID() {
		
		Transcript trans = null;
		
		try {
			
			trans = dao.getStudentTranscriptById(79352);
			
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
			fail("Should not throw an error for an existing student");
		}
		
		assertEquals(20, trans.getCoursesTaken().size());
	}

}
