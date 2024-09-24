package LLD_VendingMachine;

public class Main {
    public static void main(String[] args) {
        VendingMachine vendingMachine = new VendingMachine();

        // Add products
        vendingMachine.addProduct(new Product("Coke", 1.25, 10));
        vendingMachine.addProduct(new Product("Pepsi", 1.00, 8));
        vendingMachine.addProduct(new Product("Water", 0.75, 5));

        // Display products
        vendingMachine.displayProducts();

        // Insert money and purchase a product
        vendingMachine.insertMoney(2.00);
        vendingMachine.selectProduct("Coke");
        vendingMachine.dispenseProduct();

        // Insert money and purchase another product
        vendingMachine.insertMoney(1.00);
        vendingMachine.selectProduct("Pepsi");
        vendingMachine.dispenseProduct();

        // Display remaining products
        vendingMachine.displayProducts();
    }
}

