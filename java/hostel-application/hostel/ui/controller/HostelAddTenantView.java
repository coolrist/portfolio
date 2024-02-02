package hostel.ui.controller;

import java.text.MessageFormat;

import hostel.model.Tenant;
import hostel.model.TenantList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HostelAddTenantView {

	@FXML
	private TextField roomTF;
	@FXML
	private TextField nameTF;
	@FXML
	private TextArea resultTA;
	@FXML
	protected Button addTenantBTN;

	
	public void addTenant(TenantList listIn) {
		try {
			String roomString = roomTF.getText();
			String name = nameTF.getText();
			int room = Integer.parseInt(roomString);

			if (roomString.isBlank())
				resultTA.setText("Room field is blank");
			else if (name.isBlank())
				resultTA.setText("Name field is blank");
			else if (listIn.addTenant(new Tenant(name, room))) {
				resultTA.setText(name + " is added successfully!");

				nameTF.clear();
				roomTF.clear();
			} else
				resultTA.setText("Room already occupied");
		} catch (RuntimeException e) {
			resultTA.setText(MessageFormat.format("Error message:\n{0}", e));
		}
	}

}
