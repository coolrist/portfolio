package hostel.ui.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableTenant {

	private StringProperty name;
	private StringProperty room;
	private StringProperty month;
	private StringProperty amount;
	private NumberFormat df = DecimalFormat.getInstance();

	public ObservableTenant(String roomIn, String nameIn, String monthIn, double amountIn) {
		name = new SimpleStringProperty(this, "name", nameIn);
		room = new SimpleStringProperty(this, "room", roomIn + "");
		month = new SimpleStringProperty(this, "month", monthIn);
		amount = new SimpleStringProperty(this, "amount", df.format(amountIn));
	}

	public StringProperty getNameProperty() {
		return name;
	}

	public StringProperty getRoomProperty() {
		return room;
	}

	public StringProperty getMonthProperty() {
		return month;
	}

	public StringProperty getAmountProperty() {
		return amount;
	}

	public void setAmount(double amountIn) {
		amount.set(df.format(amountIn));
	}

}
