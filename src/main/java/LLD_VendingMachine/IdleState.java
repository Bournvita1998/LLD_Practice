package LLD_VendingMachine;

public class IdleState implements VendingMachineState {
    private VendingMachine vendingMachine;

    public IdleState(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    @Override
    public void insertMoney(double amount) {
        vendingMachine.setInsertedAmount(amount);
        vendingMachine.setState(vendingMachine.getAcceptingPaymentState());
        System.out.println("Inserted amount: $" + amount);
    }

    @Override
    public void selectProduct(String productName) {
        System.out.println("Please insert money first.");
    }

    @Override
    public void dispenseProduct() {
        System.out.println("Please insert money and select a product first.");
    }

    @Override
    public void refundMoney() {
        System.out.println("No money to refund.");
    }
}

