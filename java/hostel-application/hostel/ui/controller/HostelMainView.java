package hostel.ui.controller;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import hostel.model.Tenant;
import hostel.model.TenantFileHandler;
import hostel.model.TenantList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class HostelMainView implements Initializable {

	@FXML
	private BorderPane root;
	@FXML
	private Button makePaymentBTN;
	@FXML
	private Button addTenantBTN;
	@FXML
	private Button searchTenantBTN;
	@FXML
	private Button tenantListBTN;
	@FXML
	private Button modeLightAndNightBTN;
	@FXML
	private Button saveAndQuitBTN;
	@FXML
	private MenuButton moreDetailsBTN;
	@FXML
	private StackPane container;

	private TenantList tList;

	private FXMLLoader searchTenantLoader = new FXMLLoader(getClass().getResource("/hostel/ui/controller/hostel_1_search_tenant_view.fxml"));
	private FXMLLoader addTenantLoader = new FXMLLoader(getClass().getResource("/hostel/ui/controller/hostel_2_add_tenant_view.fxml"));
	private FXMLLoader makePaymentLoader = new FXMLLoader(getClass().getResource("/hostel/ui/controller/hostel_3_make_payment_view.fxml"));
	private FXMLLoader displayTenantLoader = new FXMLLoader(getClass().getResource("/hostel/ui/controller/hostel_4_display_list_view.fxml"));

	public void displayPage(int current) {
		for (int i = 0; i < container.getChildren().size(); i++)
			if (current == i)
				container.getChildren().get(i).setVisible(true);
			else
				container.getChildren().get(i).setVisible(false);
	}

	public void saveAndQuit() {
		TenantFileHandler.saveRecords(tList);
		Platform.exit();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			tList = new TenantList(10, TenantFileHandler.readRecords());

			Region searchTenantBox = searchTenantLoader.load();
			Region addTenantBox = addTenantLoader.load();
			Region makePaymentBox = makePaymentLoader.load();
			Region tenantTableView = displayTenantLoader.load();

			List<Region> boxList = List.of(addTenantBox, searchTenantBox, tenantTableView, makePaymentBox);

			HostelSearchTenantView searchTenantController = searchTenantLoader.getController();
			HostelAddTenantView addTenantController = addTenantLoader.getController();
			HostelMakePaymentView makePaymentController = makePaymentLoader.getController();
			HostelDisplayListView tenantController = displayTenantLoader.getController();

			container.getChildren().addAll(boxList);

			displayPage(0);

			// add tenant control
			addTenantController.addTenantBTN.setOnAction(e -> addTenantController.addTenant(tList));

			// search tenant control
			searchTenantController.searchTenantBTN.setOnAction(e0 -> {
				Optional<Tenant> tenant = searchTenantController.searchTenant(tList);

				tenant.ifPresent(t -> searchTenantController.removeTenantBTN.setOnAction(e1 -> {
					searchTenantController.removeTenant(tList, t);
				}));

				tList.search(tenant.get().getRoom()).ifPresent(t -> {
					// make payment control
					searchTenantController.makePaymentBTN.setOnAction(e1 -> {
						displayPage(3);
						makePaymentController.makePaymentBTN.setOnAction(e2 -> {
							if (makePaymentController.makePayment(t)) {
								searchTenantController.resultTA.setText("Operation success");
								displayPage(1);
							}
						});
					});

					// display payment list of a tenant
					searchTenantController.paymentListBTN.setOnAction(e1 -> {
						tenantController.displayInformations(tList);
						displayPage(2);
					});
				});
			});

			// main menu control
			addTenantBTN.setOnAction(e -> displayPage(0));
			makePaymentBTN.setOnAction(e -> displayPage(1));
			searchTenantBTN.setOnAction(e -> displayPage(1));
			tenantListBTN.setOnAction(e -> {
				tenantController.displayInformations(tList);
				displayPage(2);
			});
			moreDetailsBTN.getItems().get(0).setOnAction(e -> {
				tenantController.displayInformations(tList);
				displayPage(2);
			});
			moreDetailsBTN.getItems().get(1).setOnAction(e -> TenantFileHandler.saveRecords(tList));

		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

	}

}
