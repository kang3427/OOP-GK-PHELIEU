package payment;

public interface Payment {
    //in ra so tien tra cho khach hang
    void pay(double So_Tien);
    //lay phuong thuc thanh toan
    String getPayment();
}
