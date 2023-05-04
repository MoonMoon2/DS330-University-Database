package cs330.lab01;

import java.util.ArrayList;
import java.util.List;

import cs330.lab01.dao.DataHolder;
import cs330.lab01.dao.DepartmentDao;
import cs330.lab01.dao.HandleManager;
import cs330.lab01.dao.InstructorDao;
import cs330.lab01.dao.StudentDao;
import cs330.lab01.dao.TranscriptDao;
import cs330.lab01.domain.Course;
import cs330.lab01.domain.Instructor;
import cs330.lab01.domain.Student;
import cs330.lab01.domain.Transcript;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;

public class EditStudentController {
	
	private Stage stage;
	
	DataHolder dHold = HandleManager.dHold;
	StudentDao sDao = HandleManager.sDao;
	InstructorDao iDao = HandleManager.iDao;
	TranscriptDao tDao = HandleManager.tDao;
	DepartmentDao dDao = HandleManager.dDao;
	
	private ObservableList<String> potentialMajors;
	private ObservableList<Instructor> potentialAdvisors;
	
	Student displayedStudent;
	Transcript studentTranscript;
	
	
	
	@FXML
	private Label studentIDLabel;
	
	@FXML
	private TextField studentNameTextField;
	
	@FXML
	private ChoiceBox<String> studentMajorChoiceBox;
	
	@FXML 
	private ChoiceBox<Instructor> studentAdvisorChoiceBox;
	
	@FXML
	private Accordion transcriptAccordion;
	
	@FXML
	private Button backToStudentButton;
	
	@FXML
	private Button saveChangesButton;
	
	@FXML
	private Button editStudentButton;
	
	@FXML 
	private Button loadDataButton;
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		
		this.setStage(HandleManager.stage);
		
		displayedStudent = dHold.getActiveStudent();
		studentTranscript = displayedStudent.getTranscript();
		
		/*
		 * initialize values of header row
		 */
		studentIDLabel.setText(String.valueOf(displayedStudent.getId()));
		
		studentNameTextField.setText(displayedStudent.getName());
		
		potentialMajors = FXCollections.observableArrayList();
		potentialAdvisors = FXCollections.observableArrayList();
		
		
		
		
		/*
		 * Toggle them to be non-editable
		 */
		
		editStudentButton.setVisible(false);
		
		toggleHeaderEditable();
		
		/*
		 * Add Hover tooltips for all ambiguous fields
		 */
		
		
		studentIDLabel.setTooltip(new Tooltip("Student id number"));
		studentNameTextField.setTooltip(new Tooltip("Student last name"));
		studentMajorChoiceBox.setTooltip(new Tooltip("Student major department"));
		studentAdvisorChoiceBox.setTooltip(new Tooltip("Advisor id, name, and department"));
		
		loadDataButton.setTooltip(new Tooltip("Grab the data about this student from the database and display it"));
		
		generateTranscriptView();
		
		
		
		updateFieldsFromDB();
		
	}

	/**
	 * Generates all of the transcript tables and adds them to a single view 
	 */
	@SuppressWarnings("unchecked")
	private void generateTranscriptView() {
		/*
		 * Create and setup panels for different semesters
		 */
		
		List<String[]> semesterAndYear = displayedStudent.getTranscript().getSemestersAndYears();
		
		
		/*
		 * Make different list views for each semester of school with two tables 
		 */
		for (String[] semAndYear : semesterAndYear) {
			
			/*
			 * Create table and columns
			 */
			TableView<Course> coursesInSemester = new TableView<>();
			
			TableColumn<Course, Integer> courseIdCol = new TableColumn<>("Course ID");
			courseIdCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseId"));
			
			TableColumn<Course, String> courseTitleCol = new TableColumn<>("Course Title");
			courseTitleCol.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
			
			TableColumn<Course, String> courseGradeEarnedCol = new TableColumn<>("Student Grade");
			courseGradeEarnedCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(displayedStudent.getTranscript().getLetterGradeEarned().get(courseWrapper.getValue())));
			
			TableColumn<Course, Integer> courseCreditsCol = new TableColumn<>("Credits");
			courseCreditsCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credits"));
			
			coursesInSemester.getColumns().addAll(courseIdCol, courseTitleCol, courseGradeEarnedCol, courseCreditsCol);
			
			
			/*
			 * Get a list of courses in the semester
			 */
			List<Course> courseList = new ArrayList<>();
			double avgGPA = 0;
			int totalCred = 0;
			
			for (Course c : displayedStudent.getCoursesTaken()) {
				if (c.getSemester().equals(semAndYear[0]) && String.valueOf(c.getYear()).equals(semAndYear[1])) {
					courseList.add(c);
					avgGPA += displayedStudent.getTranscript().getGradePointsEarned().get(c);			// grab the students grade for the given course
					totalCred += c.getCredits();
					
				}
			}
			
			avgGPA /= courseList.size();		// finish the average calculation
			
			
			/*
			 * Create an identical table for the average values and bind the sizes
			 */

			TableView<String> semesterAverages = new TableView<>();
			
			TableColumn<String, String> firstCol = new TableColumn<>("");
			firstCol.setCellValueFactory(wrapper -> new ReadOnlyObjectWrapper<>(""));
			
			TableColumn<String, String> secondCol = new TableColumn<>("");
			secondCol.setCellValueFactory(wrapper -> new ReadOnlyObjectWrapper<>(""));
			
			TableColumn<String, Double> gpaCol = new TableColumn<>("Average GPA");
			final Double avgGPADouble = Double.valueOf(avgGPA);
			gpaCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(avgGPADouble));
			
			TableColumn<String, Integer> credsCol = new TableColumn<>("Credits");
			final Integer totCredInteger = Integer.valueOf(totalCred);
			credsCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(totCredInteger));
			
			semesterAverages.getColumns().addAll(firstCol, secondCol, gpaCol, credsCol);
			/*
			 * Bind the tables together
			 * 
			 * code from https://gist.github.com/Roland09/5833b61419b5ddb2b440 used for this binding
			 * 
			 */
			for( int i=0; i < coursesInSemester.getColumns().size(); i++) {
				
				TableColumn<Course,?> mainColumn = coursesInSemester.getColumns().get(i);
				TableColumn<String,?> sumColumn = semesterAverages.getColumns().get(i);
				
				// sync column widths
				sumColumn.prefWidthProperty().bind( mainColumn.widthProperty());
				
				// sync visibility
				sumColumn.visibleProperty().bind( mainColumn.visibleProperty());
				
			}
			
			
			/*
			 * Set up the table datas
			 */
			ObservableList<Course> tableInfo = FXCollections.observableArrayList(courseList);
			coursesInSemester.setItems(tableInfo);
			
			ObservableList<String> avgTableInfo = FXCollections.observableArrayList(new String[] {"Blank String"});
			semesterAverages.setItems(avgTableInfo);
			
			/*
			 * Set up the table heights
			 */
			coursesInSemester.setFixedCellSize(25);
			coursesInSemester.setPrefHeight(coursesInSemester.getItems().size()*(coursesInSemester.getFixedCellSize()) + 95);
			
			semesterAverages.setFixedCellSize(25);
			semesterAverages.setPrefHeight(semesterAverages.getItems().size()*(semesterAverages.getFixedCellSize()) + 95);
			
			/*
			 * Create the frame title
			 */
			StringBuilder titleString = new StringBuilder();
			
			titleString.append(semAndYear[0]);
			titleString.append(", ");
			titleString.append(semAndYear[1]);
			
			/*
			 * Setup border pane holding the tables
			 */
			BorderPane innerPane = new BorderPane();
			
			innerPane.setCenter(coursesInSemester);
			innerPane.setBottom(semesterAverages);
			
			/*
			 * Final setup of the titled pane
			 */
			
			TitledPane pane = new TitledPane(titleString.toString(), innerPane);
			
			pane.setPrefWidth(605);
			transcriptAccordion.getPanes().add(pane);
			
		}
		
		/*
		 * Create table and columns
		 */
		TableView<Course> coursesInSemester = new TableView<>();
		
		TableColumn<Course, Integer> courseIdCol = new TableColumn<>("Course ID");
		courseIdCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("courseId"));
		
		TableColumn<Course, String> courseTitleCol = new TableColumn<>("Course Title");
		courseTitleCol.setCellValueFactory(new PropertyValueFactory<Course, String>("title"));
		
		TableColumn<Course, String> courseGradeEarnedCol = new TableColumn<>("Student Grade");
		courseGradeEarnedCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(displayedStudent.getTranscript().getLetterGradeEarned().get(courseWrapper.getValue())));
		
		TableColumn<Course, Integer> courseCreditsCol = new TableColumn<>("Credits");
		courseCreditsCol.setCellValueFactory(new PropertyValueFactory<Course, Integer>("credits"));
		
		coursesInSemester.setPadding(new Insets(10, 10, 0, 10));
		
		coursesInSemester.getColumns().addAll(courseIdCol, courseTitleCol, courseGradeEarnedCol, courseCreditsCol);
		
		/*
		 * Get a list of courses in the semester
		 */
		List<Course> courseList = displayedStudent.getCoursesTaken();
		double avgGPA = 0;
		int totalCred = 0;
		
		for (Course c : courseList) {
				avgGPA += displayedStudent.getTranscript().getGradePointsEarned().get(c);			// grab the students grade for the given course
				totalCred += c.getCredits();
		}
		
		avgGPA /= courseList.size();		// finish the average calculation
		
		/*
		 * Create an identical table for the average values
		 */

		TableView<String> semesterAverages = new TableView<>();
		
		TableColumn<String, String> firstCol = new TableColumn<>("");
		firstCol.setCellValueFactory(wrapper -> new ReadOnlyObjectWrapper<>(""));
		
		TableColumn<String, String> secondCol = new TableColumn<>("");
		secondCol.setCellValueFactory(wrapper -> new ReadOnlyObjectWrapper<>(""));
		
		TableColumn<String, Double> gpaCol = new TableColumn<>("Average GPA");
		final Double avgGPADouble = Double.valueOf(avgGPA);
		gpaCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(avgGPADouble));
		
		TableColumn<String, Integer> credsCol = new TableColumn<>("Credits");
		final Integer totCredInteger = Integer.valueOf(totalCred);
		credsCol.setCellValueFactory(courseWrapper -> new ReadOnlyObjectWrapper<>(totCredInteger));
		
		semesterAverages.getColumns().addAll(firstCol, secondCol, gpaCol, credsCol);
		
		/*
		 * Bind the tables together
		 * 
		 * code from https://gist.github.com/Roland09/5833b61419b5ddb2b440 used for this binding
		 * 
		 */
		for( int i=0; i < coursesInSemester.getColumns().size(); i++) {
			
			TableColumn<Course,?> mainColumn = coursesInSemester.getColumns().get(i);
			TableColumn<String,?> sumColumn = semesterAverages.getColumns().get(i);
			
			// sync column widths
			sumColumn.prefWidthProperty().bind( mainColumn.widthProperty());
			
			// sync visibility
			sumColumn.visibleProperty().bind( mainColumn.visibleProperty());
			
		}
		
		/*
		 * Set up the table datas
		 */
		ObservableList<Course> tableInfo = FXCollections.observableArrayList(courseList);
		coursesInSemester.setItems(tableInfo);
		
		ObservableList<String> avgTableInfo = FXCollections.observableArrayList(new String[] {"Blank String"});
		semesterAverages.setItems(avgTableInfo);
		
		/*
		 * Set up the table heights
		 */
		coursesInSemester.setFixedCellSize(25);
		coursesInSemester.setPrefHeight(coursesInSemester.getItems().size()*(coursesInSemester.getFixedCellSize()) + 95);
		
		semesterAverages.setFixedCellSize(25);
		semesterAverages.setPrefHeight(semesterAverages.getItems().size()*(semesterAverages.getFixedCellSize()) + 95);

		/*
		 * Setup border pane holding the tables
		 */
		BorderPane innerPane = new BorderPane();
		
		
		innerPane.setCenter(coursesInSemester);
		innerPane.setBottom(semesterAverages);
		
		/*
		 * Final setup of the titled pane
		 */
		
		TitledPane pane = new TitledPane("All Time", coursesInSemester);
		
		pane.setPrefWidth(605);
		transcriptAccordion.getPanes().add(pane);
	}
	
	@FXML
	private void toggleHeaderEditable() {
		studentNameTextField.setEditable(! studentNameTextField.isEditable());
		
		studentMajorChoiceBox.setDisable(! studentMajorChoiceBox.isDisable());
		
		studentAdvisorChoiceBox.setDisable(! studentAdvisorChoiceBox.isDisable());
		
		saveChangesButton.setVisible(! saveChangesButton.isVisible());
		
		editStudentButton.setVisible(! editStudentButton.isVisible());
		
		
		
	}
	
	@FXML
	private void saveUpdatedStudent() {
		toggleHeaderEditable();
		
		displayedStudent.setName(studentNameTextField.getText());
		displayedStudent.setDeptName(studentMajorChoiceBox.getValue());
		displayedStudent.setAdvisor(studentAdvisorChoiceBox.getValue());
		
		sDao.updateStudent(displayedStudent);
	}
	
	@FXML
	private void updateFieldsFromDB() {
		
		sDao.refreshStudent(displayedStudent);
		
		studentNameTextField.setText(displayedStudent.getName());
		
		potentialMajors.setAll(dDao.getAllDepartmentNames());
		potentialAdvisors.setAll(iDao.getAllInstructors());
		
		studentMajorChoiceBox.setItems(potentialMajors);
		studentAdvisorChoiceBox.setItems(potentialAdvisors);
		
		studentMajorChoiceBox.setValue(displayedStudent.getDeptName());
		studentAdvisorChoiceBox.setValue(displayedStudent.getAdvisor());
		
		
	}
	
	@FXML
	private void closeWindow() {
		stage.close();
	}
	
	public void setStage(Stage newStage) {
		this.stage = newStage;
	}

}
