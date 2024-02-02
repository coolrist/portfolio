package hostel.ui.controller;

import hostel.model.Payment;
import hostel.model.Tenant;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class HostelMakePaymentView {

	@FXML
	private TextField monthTF;
	@FXML
	private TextField amountTF;
	@FXML
	protected Button makePaymentBTN;

	public boolean makePayment(Tenant tenant) {
		boolean result = false;
		try {
			String month = monthTF.getText();
			double amount = Double.parseDouble(amountTF.getText());

			result = tenant.makePayment(new Payment(month, amount));

			if (result) {
				monthTF.clear();
				amountTF.clear();
			}
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}
		return result;
	}

}
