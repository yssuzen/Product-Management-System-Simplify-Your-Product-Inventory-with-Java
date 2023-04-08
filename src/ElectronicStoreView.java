import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import java.util.*;

public class ElectronicStoreView extends Pane{
    private ElectronicStore model;
    private ListView<Product> stockList;
    private ListView<String> cartList;
    private ListView<Product> popularList;
    private TextField sales;
    private TextField revenue;
    private TextField sale;
    private Button reset;
    private Button add;
    private Button remove;
    private Button complete;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    private Label label6;
    private Label label7;

    public void update(ElectronicStore model){
        Product[] products = model.getProductsList();
        ArrayList <Product> cartItems = model.getCart();
        ArrayList<String> cart= new ArrayList<String>();
        Product[] stock = new Product[model.getCurProducts()];
        Product[] popular = new Product[3];
        ArrayList<Product> mostPopular = model.getSoldProducts();

        for(int i = 0; i<products.length; i++){
            stock[i] = products[i];
        }
        stockList.setItems(FXCollections.observableArrayList(stock));

        for(Product p: cartItems){
            cart.add(String.format(p.getSoldQuantity() + " x " + String.valueOf(p)));
        }
        cartList.setItems(FXCollections.observableArrayList(cart));

        if(mostPopular.size() == 0){
            for(int i =0; i<3; i++){
                popular[i] = stock[i];
            }
        }
        else if(mostPopular.size() < 3){
            if(mostPopular.size() == 1){
                popular[0] = mostPopular.get(0);
                for(int i =1; i< 3; i++){
                    popular[i] = stock[i];
                }
            }
            if(mostPopular.size() == 2){
                for(int i = 0; i < mostPopular.size(); i++){
                    popular[i] = mostPopular.get(i);
                }
                popular[2] = stock[2];
            }
        }
        else{
            for(int i =0; i<3; i++){
                popular[i] = mostPopular.get(i);
            }
        }
        popularList.setItems(FXCollections.observableArrayList(popular));

        sales.setText(String.valueOf(model.getNumberOfSales()));
        revenue.setText(String.valueOf(model.getRevenue()));
        sale.setText(String.valueOf(model.getAverageOfSales()));
        label6.setText(String.format("Current Cart($%.2f)", model.getCartTotal()));


        int addCart = stockList.getSelectionModel().getSelectedIndex();
        if(addCart == -1){
            add.setDisable(true);
        }
        else{
            add.setDisable(false);
        }
        int removeButton = cartList.getSelectionModel().getSelectedIndex();
        if(removeButton >= 0){
            Product selected = model.getProduct(removeButton);
            remove.setDisable(false);
        }
        else{
            remove.setDisable(true);
        }

        if(cartList.getItems().size() == 0){
            complete.setDisable(true);
        }
        else{
            complete.setDisable(false);
        }

    }

    public ListView<Product> getStockList(){
        return stockList;
    }

    public ListView<String> getCartList() {
        return cartList;
    }

    public Button getAdd(){
        return add;
    }
    public Button getRemove(){
        return remove;
    }
    public Button getReset(){
        return reset;
    }
    public Button getComplete(){
        return complete;
    }

    public ElectronicStoreView(ElectronicStore store){
        this.model = store;
        label1 = new Label("Store Summary:");
        label1.relocate(10,10);
        label2 = new Label("# Sales:");
        label2.relocate(20,30);
        label3 = new Label("Revenue:");
        label3.relocate(20,60);
        label4 = new Label("$ / Sale:");
        label4.relocate(20,90);
        label5 = new Label("Store Stock:");
        label5.relocate(290,10);
        label6 = new Label(String.format("Current Cart($%.2f)", model.getCartTotal()));
        label6.relocate(600,10);
        label7 = new Label("Most Popular Items:");
        label7.relocate(20,120);


        stockList = new ListView<Product>();
        cartList = new ListView<String>();
        popularList = new ListView<Product>();
        sales = new TextField();
        revenue = new TextField();
        sale = new TextField();

        reset = new Button("Reset Store");
        add = new Button("Add to Cart");
        remove = new Button("Remove from Cart");
        complete = new Button("Complete Sale");

        stockList.relocate(170,30);
        cartList.relocate(485,30);
        popularList.relocate(10,140);

        sales.relocate(80,30);
        revenue.relocate(80,60);
        sale.relocate(80,90);

        reset.relocate(18,337);
        add.relocate(248,337);
        remove.relocate(518,337);
        complete.relocate(638,337);

        stockList.setPrefSize(300,300);
        cartList.setPrefSize(300,300);
        popularList.setPrefSize(150,190);

        sales.setPrefSize(80,25);
        revenue.setPrefSize(80,25);
        sale.setPrefSize(80,25);

        reset.setPrefSize(120,50);
        add.setPrefSize(120,50);
        remove.setPrefSize(120,50);
        complete.setPrefSize(120,50);

        add.setDisable(true);
        remove.setDisable(true);
        complete.setDisable(true);

        getChildren().addAll(label1, label2, label3, label4, label5, label6, label7, stockList, cartList,popularList, sales, revenue, sale, reset, add, remove, complete);

        setPrefSize(800,400);


    }
}
