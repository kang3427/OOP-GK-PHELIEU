public class MetalScrap extends ScrapItem {

    public MetalScrap(String id, String name, double pricePerKg, double weight) {
        super(id, name, pricePerKg, weight); // Gọi constructor của class cha
    }

    @Override
    public void displayInfo() {
        System.out.println("Đây là phế liệu Kim loại: " + getName());
    }
}