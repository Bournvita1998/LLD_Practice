package LLD_VendingMachine;

public class DispensingProductState implements VendingMachineState {
    private VendingMachine vendingMachine;

    public DispensingProductState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(double amount) {
        System.out.println("Please wait, dispensing the product.");
    }

    @Override
    public void selectProduct(String productName) {
        System.out.println("Please wait, dispensing the product.");
    }

    @Override
    public void dispenseProduct() {
        Product product = vendingMachine.getSelectedProduct();
        if (product != null) {
            product.setQuantity(product.getQuantity() - 1);
            double change = vendingMachine.getInsertedAmount() - product.getPrice();
            vendingMachine.setInsertedAmount(0);
            vendingMachine.setSelectedProduct(null);
            vendingMachine.setState(vendingMachine.getIdleState());
            System.out.println("Dispensed: " + product.getName());
            if (change > 0) {
                System.out.println("Change returned: $" + change);
            }
        } else {
            System.out.println("Error dispensing product.");
        }
    }

    @Override
    public void refundMoney() {
        System.out.println("Cannot refund while dispensing product.");
    }
}

