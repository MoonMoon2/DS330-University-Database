package cs330.lab01.dao;

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
