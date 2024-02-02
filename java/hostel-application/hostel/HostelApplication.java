package hostel;

import hostel.ui.controller.HostelMainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class HostelApplication extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("/hostel/ui/controller/hostel_0_main_view.fxml"));
		
		Region root = mainViewLoader.load();
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/hostel/ui/assets/img/hotel.png"));
		primaryStage.setTitle("Hostel Application");
		primaryStage.setMinWidth(850);
		primaryStage.setMinHeight(400);
		primaryStage.setOnCloseRequest(e -> {
			HostelMainView controller = mainViewLoader.getController();
			controller.saveAndQuit();
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
