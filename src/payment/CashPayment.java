package payment;

public class CashPayment implements Payment{
    private String ten_ThuNgan;
    public CashPayment(String ten_ThuNgan) {
        this.ten_ThuNgan = ten_ThuNgan;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan tien mat] cho khach hang:"
                + So_Tien + " Thu ngan: " + ten_ThuNgan);
    }
    @Override
    public String getPaymentType() {
        return "Tien mat (Thu ngan: " + ten_ThuNgan+")";
    }
}
