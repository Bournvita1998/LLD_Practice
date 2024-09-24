package LLD_VendingMachine;

import java.util.HashMap;
import java.util.Map;

public class VendingMachine {
    private VendingMachineState idleState;
    private VendingMachineState acceptingPaymentState;
    private VendingMachineState dispensingProductState;
    private VendingMachineState state;
    private Map<String, Product> products;
    private double insertedAmount;
    private Product selectedProduct;

    public VendingMachine() {
        idleState = new IdleState(this);
        acceptingPaymentState = new AcceptingPaymentState(this);
        dispensingProductState = new DispensingProductState(this);

        state = idleState;
        products = new HashMap<>();
    }

    public void setState(VendingMachineState state) {
        this.state = state;
    }

    public VendingMachineState getIdleState() {
        return idleState;
    }

    public VendingMachineState getAcceptingPaymentState() {
        return acceptingPaymentState;
    }

    public VendingMachineState getDispensingProductState() {
        return dispensingProductState;
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public double getInsertedAmount() {
        return insertedAmount;
    }

    public void setInsertedAmount(double amount) {
        this.insertedAmount = amount;
    }

    public Product getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(Product product) {
        this.selectedProduct = product;
    }

    public void insertMoney(double amount) {
        state.insertMoney(amount);
    }

    public void selectProduct(String productName) {
        state.selectProduct(productName);
    }

    public void dispenseProduct() {
        state.dispenseProduct();
    }

    public void refundMoney() {
        state.refundMoney();
    }

    public void addProduct(Product product) {
        products.put(product.getName(), product);
    }

    public void displayProducts() {
        System.out.println("Available products:");
        for (Product product : products.values()) {
            System.out.println(product.getName() + " - Price: $" + product.getPrice() + " - Quantity: " + product.getQuantity());
        }
    }
}

