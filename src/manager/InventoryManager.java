package manager;

public class InventoryManager {
    private String idItem;
    private double khoiLuong;
    private double sucChuaToiDa;
    public InventoryManager(String idItem, double khoiLuong, double sucChuaToiDa) {
        this.idItem = idItem;
        this.khoiLuong = khoiLuong;
        this.sucChuaToiDa = sucChuaToiDa;
    }

    //nhap them phe lieu khi thu mua tu khach hang(tang ton kho)
    public void addStock(String idItem, double khoiLuong){
        if (khoiLuong<0){
            throw new IllegalArgumentException("Phe lieu nhap vao co khoi luong khong duoc am");
        }

    }
}
