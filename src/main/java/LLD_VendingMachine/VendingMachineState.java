package LLD_VendingMachine;

public interface VendingMachineState {
    void insertMoney(double amount);
    void selectProduct(String productName);
    void dispenseProduct();
    void refundMoney();
}

