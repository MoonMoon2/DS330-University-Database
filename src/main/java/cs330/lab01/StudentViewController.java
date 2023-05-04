package cs330.lab01;

import java.io.IOException;
import java.util.Comparator;
import java.util.function.UnaryOperator;

import cs330.lab01.dao.DataHolder;
import cs330.lab01.dao.DepartmentDao;
import cs330.lab01.dao.HandleManager;
import cs330.lab01.dao.InstructorDao;
import cs330.lab01.dao.StudentDao;
import cs330.lab01.domain.Instructor;
import cs330.lab01.domain.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Node;
import javafx.scene.effect.GaussianBlur; 
import javafx.scene.effect.ColorAdjust; 

public class StudentViewController {
	
	private Stage primaryStage;


	private StudentDao sDao = HandleManager.sDao;
	private InstructorDao iDao = HandleManager.iDao;
	private DepartmentDao dDao = HandleManager.dDao;
	private DataHolder dHolder = HandleManager.dHold;

	private ObservableList<Student> studentData;
	private ObservableList<Instructor> possibleMentors;
	private ObservableList<String> majors;
	private ObservableList<String> searchTerms;
	private FilteredList<Student> filteredStudents;

	@FXML
	private TableView<Student> studentsTable;

	@FXML
	private ComboBox<String> searchBar;

	@FXML
	private ChoiceBox<String> searchType;

	@FXML
	private TextField newStudIdInput;

	@FXML
	private TextField newStudNameInput;

	@FXML
	private ComboBox<String> newStudMajorDropdown;

	@FXML
	private ComboBox<Instructor> newStudMentorDropdown;

	@FXML
	private Button createStudentBut;
	
	@FXML 
	private VBox screen;

	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {

		/*
		 * Prepare student data
		 */

		studentData = FXCollections.observableArrayList(sDao.getAllStudents());


		TableColumn<Student, Integer> studIdCol = new TableColumn<>("ID");
		studIdCol.setCellValueFactory(
				new PropertyValueFactory<Student, Integer>("id"));

		TableColumn<Student, String> nameCol = new TableColumn<>("Last Name");
		nameCol.setCellValueFactory(
				new PropertyValueFactory<Student, String>("name"));

		TableColumn<Student, String> deptCol = new TableColumn<>("Major");
		deptCol.setCellValueFactory(
				new PropertyValueFactory<Student, String>("deptName"));

		TableColumn<Student, Integer> mentorIdCol = new TableColumn<>("Mentor ID");
		mentorIdCol.setCellValueFactory(studWrapper -> new ReadOnlyObjectWrapper<>(studWrapper.getValue().getAdvisor().getId()));

		TableColumn<Student, String> mentorNameCol = new TableColumn<>("Mentor Name");
		mentorNameCol.setCellValueFactory(studWrapper -> new ReadOnlyObjectWrapper<>(studWrapper.getValue().getAdvisor().getName()));

		TableColumn<Student, String> gpaCol = new TableColumn<>("GPA");
		gpaCol.setCellValueFactory(studWrapper -> new ReadOnlyObjectWrapper<>("%.2f".formatted((studWrapper.getValue().getGpa()))));

		TableColumn<Student, Integer> credCol = new TableColumn<>("Credits");
		credCol.setCellValueFactory(studWrapper -> new ReadOnlyObjectWrapper<>(studWrapper.getValue().getTotCred()));


		filteredStudents = new FilteredList<>(studentData, p -> true);

		studentsTable.setItems(filteredStudents);
		
		
		studIdCol.sortableProperty();


		studentsTable.getColumns().addAll(studIdCol, nameCol, deptCol, gpaCol, credCol, mentorNameCol);

		
		/*
		 * lambda generated single function interface implementation
		 */
		studentsTable.setOnMouseClicked((e) -> {
			dHolder.setActiveStudent(studentsTable.getSelectionModel().getSelectedItem());
			
			if(e.getClickCount() == 2){

				/*
				 * Open the selectedStudent view
				 */
				try {
					viewSelectedStudent(e);
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		studentsTable.setTooltip(new Tooltip("Double click a student to open"));

		/*
		 * Setup search bar
		 */

		searchTerms = FXCollections.observableArrayList();

		studentData.forEach(stud -> {
			searchTerms.add(stud.getName());
		});

		searchBar.setItems(searchTerms);

		

		/*
		 * Setup the search types
		 */
		searchType.getItems().addAll("Student ID", "Student Name", "Major", "GPA", "Mentor Name", "Mentor ID");
		searchType.setValue("Student Name");

		/*
		 * Anonymous listener for changes to searchType
		 */
		searchType.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {		//reset table and search value, and insert the available options depending on the dropdown
			/*
			 * Clear the searchbar
			 */
			if (newVal != null) {
				searchBar.setValue("");
			}
			/*
			 * Remove all values from the searchbar dropdown
			 */
			searchBar.getItems().clear();

			/*
			 * Initiate the searchTerms array
			 */
			searchTerms = FXCollections.observableArrayList();

			/*
			 * Set the searchTerms array to match the search type
			 */
			switch (searchType.getSelectionModel().getSelectedItem()) {
			case "Student ID":
				studentData.forEach(stud -> {
					searchTerms.add(String.valueOf(stud.getId()));
				});
				break;
			case "Student Name":
				studentData.forEach(stud -> {
					searchTerms.add(stud.getName());
				});
				break;
			case "Major":
				studentData.forEach(stud -> {
					searchTerms.add(stud.getDeptName());
				});
				break;
			case "GPA":
				studentData.forEach(stud -> {
					searchTerms.add(String.valueOf("%.2f".formatted(stud.getGpa())));
				});
				break;
			case "Mentor Name":
				studentData.forEach(stud -> {
					searchTerms.add(stud.getAdvisor().getName());
				});
				break;
			case "Mentor ID":
				studentData.forEach(stud -> {
					searchTerms.add(String.valueOf(stud.getAdvisor().getId()));
				});
				break;
			default:
				studentData.forEach(stud -> {
					searchTerms.add(stud.getName());
				});
				break;
			}

			searchBar.setItems(searchTerms);

		});

		/*
		 * Prepare mentor data
		 */


		possibleMentors = FXCollections.observableArrayList();
		possibleMentors.addAll(iDao.getAllInstructors());
		possibleMentors.sort(new Comparator<Instructor>() {

			@Override
			public int compare(Instructor o1, Instructor o2) {
				return (o1.getDeptName().compareTo(o2.getDeptName()));
			}

		});

		newStudMentorDropdown.setItems(possibleMentors);

		/*
		 * Prepare major data
		 */

		majors = FXCollections.observableArrayList();
		majors.addAll(dDao.getAllDepartmentNames());
		majors.sort(new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}

		});

		newStudMajorDropdown.setItems(majors);


		
		/*
		 * Setup integer only field
		 */
		
		// code for unary operator found on StackOverflow
		UnaryOperator<TextFormatter.Change> filter = change -> {
		    String text = change.getText();

		    if (text.matches("[0-9]*")) {
		        return change;
		    }

		    return null;
		};
		
		
		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		newStudIdInput.setTextFormatter(textFormatter);
		
		newStudIdInput.setTooltip(new Tooltip("Integer-only field"));


	}

	@FXML
	private void viewSelectedStudent(MouseEvent event) throws IOException {
		dHolder.setActiveStudent(studentsTable.getSelectionModel().getSelectedItem());
		System.out.println(dHolder.getActiveStudent());
		
		
		Stage stage = new Stage();
		
		HandleManager.stage = stage;
		
        Parent root = FXMLLoader.load(App.class.getResource("displayStudent.fxml"));
        
        screen.setEffect(new GaussianBlur());
        
        
        stage.setScene(new Scene(root));
        stage.setTitle("Model Student Display");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
            ((Node)event.getSource()).getScene().getWindow() );
        stage.show();
	}
	
	@FXML
	private void clearBlur() {
		screen.setEffect(null);
	}

	/**
	 * Creates a new student from the form
	 */
	@FXML
	private void createStudent() {
		if (sDao.getStudent(Integer.valueOf(newStudIdInput.getText())) == null) {
			Student newStudent = new Student(Integer.valueOf(newStudIdInput.getText()), newStudNameInput.getText(), newStudMajorDropdown.getValue(), newStudMentorDropdown.getValue());
			sDao.createStudent(newStudent);
		}
		
		updateStudents();

	}
	
	private void updateStudents() {
		studentData.setAll(sDao.getAllStudents());
	}

	/**
	 * Updates the table and search bar dropdown to filter by the selected property
	 */
	@FXML
	private void searchUpdate() {
		
		updateStudents();

		String newValue = searchBar.getEditor().getText();

		if (newValue == null) {
			return;
		}

		searchTerms = FXCollections.observableArrayList();

		/*
		 * 
		 */
		switch (searchType.getValue()) {
		case "Student ID":
			filteredStudents.setPredicate(s -> String.valueOf(s.getId()).contains(newValue.trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(String.valueOf(stud.getId()));
			});
			break;
		case "Student Name":
			filteredStudents.setPredicate(s -> s.getName().toLowerCase().contains(newValue.toLowerCase().trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(stud.getName());
			});
			break;
		case "Major":
			filteredStudents.setPredicate(s -> s.getDeptName().toLowerCase().contains(newValue.toLowerCase().trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(stud.getDeptName());
			});
			break;
		case "GPA":
			filteredStudents.setPredicate(s -> String.valueOf("%.2f".formatted(s.getGpa())).contains(newValue.trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(String.valueOf("%.2f".formatted(stud.getGpa())));
			});
			break;
		case "Mentor Name":
			filteredStudents.setPredicate(s -> s.getAdvisor().getName().toLowerCase().contains(newValue.toLowerCase().trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(stud.getAdvisor().getName());
			});
			break;
		case "Mentor ID":
			filteredStudents.setPredicate(s -> String.valueOf(s.getAdvisor().getId()).contains(newValue.trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(stud.getName());
			});
			break;
		default:
			filteredStudents.setPredicate(s -> s.getName().toLowerCase().contains(newValue.toLowerCase().trim()));
			filteredStudents.forEach(stud -> {
				searchTerms.add(stud.getName());
			});
			break;
		}



		studentsTable.setItems(filteredStudents);
		searchBar.setItems(searchTerms);

	}

}
