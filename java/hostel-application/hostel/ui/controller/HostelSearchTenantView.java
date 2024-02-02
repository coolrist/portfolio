package hostel.ui.controller;

import java.text.MessageFormat;
import java.util.Optional;

import hostel.model.Tenant;
import hostel.model.TenantList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class HostelSearchTenantView {

	@FXML
	private TextField roomTF;
	@FXML
	protected TextArea resultTA;
	@FXML
	protected Button searchTenantBTN;
	@FXML
	protected Button removeTenantBTN;
	@FXML
	protected Button makePaymentBTN;
	@FXML
	protected Button paymentListBTN;
	

	public Optional<Tenant> searchTenant(TenantList listIn) {
		Optional<Tenant> result = Optional.empty();
		try {
			String stringRoom = roomTF.getText();
			int room = Integer.parseInt(stringRoom);

			// check for errors
			if (stringRoom.isBlank())
				resultTA.setText("Room field is blank");
			else {
				result = listIn.search(room);
				result.ifPresentOrElse(value -> {
					StringBuilder resultTxt = new StringBuilder("Room: " + value.getRoom() + "\n");
					resultTxt.append("Name: " + value.getName());
					
					resultTA.setText(resultTxt.toString());
				}, () -> resultTA.setText("Room number " + room + " is empty"));
			}			
		} catch (RuntimeException e) {
			resultTA.setText("Invalid room number " + e.getMessage() + "\nEnter whole numbers only!");
		}
		
		roomTF.clear();
		return result;
	}
	
	public void removeTenant(TenantList listIn, Tenant tenant) {
		try {
			String message = listIn.removeTenant(tenant.getRoom()) ? tenant.getName() + " is removed!" : tenant.getName() + " is not removed successfully!";
			StringBuilder result = new StringBuilder("Success message:\n");
			result.append(message);
			resultTA.setText(result.toString());
		} catch (RuntimeException e) {
			resultTA.setText(MessageFormat.format("Error message:\n{0}", e));
		}
	}

}
