package cs330.lab01.utils;

import cs330.lab01.dao.DBDepartmentDao;
import cs330.lab01.dao.DBInstructorDao;
import cs330.lab01.dao.DBStudentDao;
import cs330.lab01.dao.DBTranscriptDao;
import cs330.lab01.dao.DepartmentDao;
import cs330.lab01.dao.InstructorDao;
import cs330.lab01.dao.StudentDao;
import cs330.lab01.dao.TranscriptDao;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HandleManager {
	
	public static StudentDao sDao;
	public static TranscriptDao tDao;
	public static InstructorDao iDao;
	public static DataHolder dHold;
	public static Scene scene;
	public static DepartmentDao dDao;
	public static Stage stage;
	
	static {
		sDao = new DBStudentDao();
		tDao = new DBTranscriptDao();
		iDao = new DBInstructorDao();
		dHold = new DataHolder();
		dDao = new DBDepartmentDao();
	}

}
