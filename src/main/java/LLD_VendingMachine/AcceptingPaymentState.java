package LLD_VendingMachine;

public class AcceptingPaymentState implements VendingMachineState {
    private VendingMachine vendingMachine;

    public AcceptingPaymentState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(double amount) {
        vendingMachine.setInsertedAmount(vendingMachine.getInsertedAmount() + amount);
        System.out.println("Inserted additional amount: $" + amount);
    }

    @Override
    public void selectProduct(String productName) {
        Product product = vendingMachine.getProducts().get(productName);

        if (product == null) {
            System.out.println("Product not found.");
            vendingMachine.refundMoney();
            vendingMachine.setState(vendingMachine.getIdleState());
        } else if (product.getQuantity() <= 0) {
            System.out.println("Product out of stock.");
            vendingMachine.refundMoney();
            vendingMachine.setState(vendingMachine.getIdleState());
        } else if (vendingMachine.getInsertedAmount() < product.getPrice()) {
            System.out.println("Insufficient funds. Please insert more money.");
        } else {
            vendingMachine.setSelectedProduct(product);
            vendingMachine.setState(vendingMachine.getDispensingProductState());
        }
    }

    @Override
    public void dispenseProduct() {
        System.out.println("Please select a product first.");
    }

    @Override
    public void refundMoney() {
        System.out.println("Refunding amount: $" + vendingMachine.getInsertedAmount());
        vendingMachine.setInsertedAmount(0);
        vendingMachine.setState(vendingMachine.getIdleState());
    }
}

