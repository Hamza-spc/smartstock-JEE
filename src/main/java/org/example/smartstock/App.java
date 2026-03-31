package org.example.smartstock;

public class App {
    public static void main(String[] args){
       ProductRepository repository = new InMemoryProductRepository();
       InventoryService service = new InventoryService(repository);

        service.registerProduct("LAP-001", "Laptop", 10);
        service.addStock("LAP-001", 5);
        service.removeStock("LAP-001", 3);

        Product product = service.getProduct("LAP-001");
        System.out.println(product.getName() + " -> "+ product.getQuantity());

    }
}
