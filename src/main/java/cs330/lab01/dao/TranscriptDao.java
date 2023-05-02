package cs330.lab01.dao;

import cs330.lab01.domain.Student;
import cs330.lab01.domain.Transcript;
import cs330.lab01.utils.ObjectNotFoundException;

public interface TranscriptDao {
	
	public Transcript getStudentTranscript(Student student) throws ObjectNotFoundException;
	public Transcript getStudentTranscriptById(int studentId) throws ObjectNotFoundException;
	
}
