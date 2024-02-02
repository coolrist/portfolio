package hostel.ui.controller;

import java.net.URL;
import java.util.ResourceBundle;

import hostel.model.PaymentList;
import hostel.model.Tenant;
import hostel.model.TenantList;
import hostel.ui.util.ObservableTenant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;

public class HostelDisplayListView implements Initializable {

	@FXML
	private TreeTableView<ObservableTenant> table;
	@FXML
	private TreeTableColumn<ObservableTenant, String> col1;
	@FXML
	private TreeTableColumn<ObservableTenant, String> col2;
	@FXML
	private TreeTableColumn<ObservableTenant, String> col3;
	@FXML
	private TreeTableColumn<ObservableTenant, String> col4;

	public void displayInformations(TenantList list) {

		ObservableTenant rootTenant = new ObservableTenant("Room", "Name", "Month", 0);
		TreeItem<ObservableTenant> root = new TreeItem<>();
		double totalOfAllTenants = 0;

		for (Tenant tenant : list.getAllTenants()) {
			PaymentList paymentList = tenant.getPayments();
			double totalPaidForTenant = paymentList.calculateTotalPaid();
			TreeItem<ObservableTenant> parent = new TreeItem<>(new ObservableTenant("" + tenant.getRoom(),
					tenant.getName(), "Total of payments", totalPaidForTenant));

			totalOfAllTenants += totalPaidForTenant;
			paymentList.getAllPayments().forEach(payment -> parent.getChildren()
					.add(new TreeItem<>(new ObservableTenant("", "", payment.getMonth(), payment.getAmount()))));

			root.getChildren().add(parent);
		}

		rootTenant.setAmount(totalOfAllTenants);
		root.setValue(rootTenant);
		table.setRoot(root);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		col1.setCellValueFactory(p -> p.getValue().getValue().getRoomProperty());
		col2.setCellValueFactory(p -> p.getValue().getValue().getNameProperty());
		col3.setCellValueFactory(p -> p.getValue().getValue().getMonthProperty());
		col4.setCellValueFactory(p -> p.getValue().getValue().getAmountProperty());
	}

}
