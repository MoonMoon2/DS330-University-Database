package cs330.lab01;

import java.util.Comparator;

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
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentViewController {
	
	
	private StudentDao sDao = HandleManager.sDao;
	private InstructorDao iDao = HandleManager.iDao;
	private DepartmentDao dDao = HandleManager.dDao;
	private DataHolder dHolder = HandleManager.dHold;
	
	private ObservableList<Student> studentData;
	private ObservableList<Instructor> possibleMentors;
	private ObservableList<String> majors;
	private ObservableList<String> searchTerms;
	
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
	
	@SuppressWarnings("unchecked")
	@FXML
	private void initialize() {
		
		
		/*
		 * Prepare student data
		 */
		
		studentData = FXCollections.observableArrayList();
		
		studentData.addAll(sDao.getAllStudents());
		
		
		TableColumn<Student, Integer> studIdCol = new TableColumn<>("Student ID Number");
        studIdCol.setCellValueFactory(
                new PropertyValueFactory<Student, Integer>("id"));
 
        TableColumn<Student, String> nameCol = new TableColumn<>("Last Name");
        nameCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("name"));
 
        TableColumn<Student, String> deptCol = new TableColumn<>("Major");
        deptCol.setCellValueFactory(
                new PropertyValueFactory<Student, String>("deptName"));
        
        TableColumn<Student, Integer> mentorIdCol = new TableColumn<>("Mentor ID");
        mentorIdCol.setCellValueFactory(tf -> new ReadOnlyObjectWrapper<>(tf.getValue().getAdvisor().getId()));
        
        TableColumn<Student, String> mentorNameCol = new TableColumn<>("Mentor Name");
        mentorNameCol.setCellValueFactory(tf -> new ReadOnlyObjectWrapper<>(tf.getValue().getAdvisor().getName()));
        
        TableColumn<Student, String> gpaCol = new TableColumn<>("GPA");
        gpaCol.setCellValueFactory(tf -> new ReadOnlyObjectWrapper<>("%.2f".formatted((tf.getValue().getGpa()))));
        
        TableColumn<Student, Integer> credCol = new TableColumn<>("Total Credits");
        credCol.setCellValueFactory(tf -> new ReadOnlyObjectWrapper<>(tf.getValue().getTotCred()));
        
        
        FilteredList<Student> filteredStudents = new FilteredList<>(studentData, p -> true);
        
        studentsTable.setItems(filteredStudents);
        
        
        studentsTable.getColumns().addAll(studIdCol, nameCol, deptCol, gpaCol, credCol, mentorNameCol);
        
        
        /*
         * Setup search bar
         */
        
        searchBar.setEditable(true);
        
        searchType.getItems().addAll("Student ID", "Student Name", "Major", "GPA", "Mentor Name", "Mentor ID");
        searchType.setValue("Student Name");
        
        
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
            case "GPA at least":
            	break;
            case "GPA at most":
            	break;
            case "GPA matches":
            	break;
            case "Mentor Name":
            	break;
            case "Mentor ID":
            	break;
            default:
            	break;
            }
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
        
        
        
        
	}
	
	@FXML
	private void viewSelectedStudent() {
		dHolder.setActiveStudent(studentsTable.getSelectionModel().getSelectedItem());
		
	}
	
	@FXML
	private void createStudent() {
		
	}
	
	@FXML
	private void searchUpdate() {
		
	}
	
}