import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.event.*;
import java.util.*;
import javafx.scene.input.MouseEvent;
public class ElectronicStoreApp extends Application{
    private ElectronicStore model;

    public void start(Stage primaryStage){
        Pane aPane = new Pane();

        model = ElectronicStore.createStore();
        ElectronicStoreView view = new ElectronicStoreView(model);


        aPane.getChildren().add(view);

        view.update(model);

        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.getAdd().setDisable(false);
            }
        });

        view.getAdd().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product selected = view.getStockList().getSelectionModel().getSelectedItem();
                model.addCart(selected);
                view.update(model);
            }
        });

        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                view.getRemove().setDisable(false);
            }
        });

        view.getRemove().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                int selected = view.getCartList().getSelectionModel().getSelectedIndex();
                if(selected >= 0){
                    model.removeProductCart(selected);
                    view.update(model);
                }
            }
        });

        view.getReset().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                start(primaryStage);
            }
        });

        view.getComplete().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.completeSale();
                view.update(model);
            }
        });

        primaryStage.setResizable(false);
        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setScene(new Scene(aPane));
        primaryStage.show();


    }
    public static void main(String[] args) {
        launch(args);
    }

}
