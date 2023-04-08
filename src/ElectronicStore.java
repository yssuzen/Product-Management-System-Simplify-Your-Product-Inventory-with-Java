//Class representing an electronic store
//Has an array of products that represent the items the store can sell
import java.util.*;

public class ElectronicStore {
    public final int MAX_PRODUCTS = 10; //Maximum number of products the store can have
    private int curProducts;
    private String name;
    private Product[] stock; //Array to hold all products
    private double revenue;
    private double cartTotal;
    private ArrayList<Product> cart;
    private ArrayList<Product> soldProducts;
    private int cartCounter;
    private int numberOfSales;
    private double averageOfSales;

    public ElectronicStore(String initName) {
        revenue = 0.0;
        name = initName;
        stock = new Product[MAX_PRODUCTS];
        curProducts = 0;
        cartTotal = 0.0;
        cartCounter = 0;
        cart = new ArrayList<Product>();
        numberOfSales = 0;
        averageOfSales = revenue/numberOfSales;
        soldProducts = new ArrayList<Product>();
    }

    public String getName() {
        return name;
    }

    public int getNumberOfSales(){
        return numberOfSales;
    }

    public double getAverageOfSales(){
        return revenue/numberOfSales;
    }

    public double getCartTotal(){
        return cartTotal;
    }

    public int getNumberOfCart(Product p){
        int i = 0;
        for(Product p1: cart){
            if(p1 == p){
                i++;
            }
        }
        return i;
    }

    public ArrayList<Product> getCart(){
        return cart;
    }
    public ArrayList<Product> getSoldProducts(){
        sortedSoldProducts(soldProducts);
        return soldProducts;
    }
    public double getRevenue(){
        return revenue;
    }
    public Product[] getStock(){
        return stock;
    }

    public Product getProduct(int index){
        if(index >= 0 && index < cartCounter){
            return cart.get(index);
        }
        return null;
    }

    public Product[] getProductsList(){
        Product[] products = new Product[curProducts];
        for(int i = 0; i<curProducts; i++){
            products[i] = stock[i];
        }
        return products;
    }

    public int getIndexofProduct(Product p){
        for(int i = 0; i < MAX_PRODUCTS; i++){
            if(p == stock[i]){
                return i;
            }
        }
        return 0;
    }
    public int getCurProducts(){
        return curProducts;
    }

    //Adds a product and returns true if there is space in the array
    //Returns false otherwise
    public boolean addProduct(Product newProduct) {
        if (curProducts < MAX_PRODUCTS) {
            stock[curProducts] = newProduct;
            curProducts++;
            return true;
        }
        return false;
    }

    public void removeProductCart(int index){
        if(getProduct(index) != null){
            if(index >= 0 && index < cartCounter){
                if(cart.contains(getProduct(index))){
                    getProduct(index).setStockQuantity(1);
                    getProduct(index).setSoldQuantity(-1);
                    cartTotal -= getProduct(index).getPrice();
                    if(getProduct(index).getSoldQuantity() == 0){
                        cart.remove(getProduct(index));
                        cartCounter--;
                    }
                }
                if(!stockContain(getProduct(index))){
                    addProduct(getProduct(index));
                }
            }
        }
    }

    public boolean stockContain(Product p){
        for(Product p1: stock){
            if(p1 == p){
                return true;
            }
        }
        return false;
    }

    public void removeProductStock(int i){
        if(curProducts > i && i >= 0){
            stock[i] = null;
            for(int m = i+1; m < curProducts; m++){
                Product temp = stock[m-1];
                stock[m-1] = stock[m];
                stock[m] = temp;
            }
            curProducts--;
        }
    }

    public void addCart(Product p){
        if(p != null) {
            cartTotal += p.getPrice();
            if (p.getStockQuantity() > getNumberOfCart(p)) {
                if(cart.contains(p)){
                    p.setStockQuantity(-1);
                    p.setSoldQuantity(1);
                }
                else{
                    if(p.getStockQuantity() == 1 && getNumberOfCart(p) == 0){
                        removeProductStock(getIndexofProduct(p));
                    }
                    p.setStockQuantity(-1);
                    cart.add(p);
                    cartCounter++;
                    p.setSoldQuantity(1);
                }
            }
            else {
                if (p.getStockQuantity() == getNumberOfCart(p)) {
                    if(cart.contains(p)){
                        p.setStockQuantity(-1);
                        p.setSoldQuantity(1);
                        removeProductStock(getIndexofProduct(p));
                    }
                    else{
                        p.setStockQuantity(-1);
                        removeProductStock(getIndexofProduct(p));
                        cart.add(p);
                        cartCounter++;
                        p.setSoldQuantity(1);
                    }
                }
            }
        }
    }

    public void sortedSoldProducts(ArrayList<Product> soldProducts){
        int x = soldProducts.size();
        for(int i = 0; i < x-1; i++){
            int minimum = i;
            for(int j = i + 1; j < x; j++){
                if(soldProducts.get(j).getSoldQuantity() > soldProducts.get(minimum).getSoldQuantity()){
                    minimum = j;
                    Collections.swap(soldProducts,minimum, i);
                }
            }
        }
    }

    public void completeSale(){
        this.numberOfSales++;
        for(Product p: cart){
            revenue += p.getPrice() * p.getSoldQuantity();
            p.setSoldQuantity(-(p.getSoldQuantity()));
            p.setStockQuantity(-(p.getSoldQuantity()));
            if (!soldProducts.contains(p)) {
                soldProducts.add(p);
            }
        }
        cart.clear();
        cartTotal = 0;
        cartCounter = 0;
    }

    public static ElectronicStore createStore() {
        ElectronicStore store1 = new ElectronicStore("Watts Up Electronics");
        Desktop d1 = new Desktop(100, 10, 3.0, 16, false, 250, "Compact");
        Desktop d2 = new Desktop(200, 10, 4.0, 32, true, 500, "Server");
        Laptop l1 = new Laptop(150, 10, 2.5, 16, true, 250, 15);
        Laptop l2 = new Laptop(250, 10, 3.5, 24, true, 500, 16);
        Fridge f1 = new Fridge(500, 10, 250, "White", "Sub Zero", false);
        Fridge f2 = new Fridge(750, 10, 125, "Stainless Steel", "Sub Zero", true);
        ToasterOven t1 = new ToasterOven(25, 10, 50, "Black", "Danby", false);
        ToasterOven t2 = new ToasterOven(75, 10, 50, "Silver", "Toasty", true);
        store1.addProduct(d1);
        store1.addProduct(d2);
        store1.addProduct(l1);
        store1.addProduct(l2);
        store1.addProduct(f1);
        store1.addProduct(f2);
        store1.addProduct(t1);
        store1.addProduct(t2);
        return store1;
    }
} 