package thiagod;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

public class Controller implements Initializable {

	@FXML
	private Spinner<Integer> seedSpinner;

	@FXML
	private Spinner<Integer> numberOfTeamsSpinner;

	@FXML
	private Spinner<Integer> numberOfMembersSpinner;

	@FXML
	private TextArea studentsTextArea;

	@FXML
	private TextArea outputTextArea;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		SpinnerValueFactory<Integer> factoryForSeed = new SpinnerValueFactory.IntegerSpinnerValueFactory(
				Integer.MIN_VALUE, Integer.MAX_VALUE, 1);
		SpinnerValueFactory<Integer> factoryForNumberOfTeams = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
				Integer.MAX_VALUE, 4);
		SpinnerValueFactory<Integer> factoryForNumberOfMembers = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
				Integer.MAX_VALUE, 3);

		seedSpinner.setValueFactory(factoryForSeed);
		numberOfTeamsSpinner.setValueFactory(factoryForNumberOfTeams);
		numberOfMembersSpinner.setValueFactory(factoryForNumberOfMembers);

		studentsTextArea.setPromptText("One student per line");
	}

	/**
	 * This method is called when the Build button is pressed.
	 * 
	 * @param event You may ignore this argument for now
	 */
	@FXML
	private void handleButtonBuild(ActionEvent event) {

		// Firstly, retrieve all information provided by the user in the UI and do the
		// basic pre-processing
		int seed = seedSpinner.getValue();
		int numberOfTeams = numberOfTeamsSpinner.getValue();
		int numberOfMembers = numberOfMembersSpinner.getValue();
		String[] rows = studentsTextArea.getText().split("\n");

		// To ease the handle of the rows, we convert it to a list.
		List<String> studentList = Arrays.asList(rows);

		// However, we still need to create an ArrayList since we may not remove element
		// from the list
		studentList = new ArrayList<>(studentList);

		studentList = studentList.stream().filter(e -> !e.isBlank()).collect(Collectors.toList());

		// if the student list is empty, then we cannot build the teams. Let the user
		// knows about this issue by using an alert dialog
		if (studentList.isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);

			alert.setContentText("The student list is empty");
			alert.showAndWait();
			return;
		}

		// Create the random generator by using the seed provided by the user
		Random generator = new Random(seed);

		// Before creating a new team, we always have to clean the output
		outputTextArea.setText("");

		// The main idea here is for each team, select the students. However, once a
		// student got selected, we have to remove it from the our current list then
		// he/she will not be selected again.
		for (int i = 0; i < numberOfTeams; i++) {

			// Do not forget of concatenating "\n". Otherwise, the output will be added in
			// the same line
			outputTextArea.appendText("Team #" + i + "\n");

			for (int j = 0; j < numberOfMembers; j++) {

				// Since we use random index, we need to make sure that at least we have one
				// student in our list. Otherwise, it could break out code. Thus, if the list is
				// empty, there is nothing to do.
				if (studentList.isEmpty()) {
					break;
				}

				// Select a rand index from our student list considering the current size.
				int randIndex = generator.nextInt(studentList.size());

				// Remove the student from the list to make sure he/she will not be selected
				// again
				String student = studentList.remove(randIndex);

				// Only add the student in the screen
				outputTextArea.appendText(student + "\n");
			}
		}
	}
}
