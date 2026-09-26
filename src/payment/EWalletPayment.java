package payment;

public class EWalletPayment implements Payment{
    private String viDienTu;
    private String soTaiKhoan;
    public EWalletPayment(String viDienTu, String soTaiKhoan) {
        this.viDienTu = viDienTu;
        this.soTaiKhoan = soTaiKhoan;
    }
    @Override
    public void pay(double So_Tien) {
        System.out.println("[Thanh toan vi dien tu] cho khach hang:"
        + So_Tien +" "+ viDienTu +" "+ soTaiKhoan);
    }
    @Override
    public String getPayment(){
        return "Vi dien tu: "+viDienTu+" So tai khoan: "+ soTaiKhoan;
    }
}
