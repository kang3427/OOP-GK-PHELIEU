import payment.*;
public class Main {
    public static void main(String[] args) {
        Payment p1 = new CashPayment("Nguyen Hoang Luan");
        Payment p2 = new BankPayment("Vietcombank", "1012345678", "NGUYEN VAN A");
        Payment p3 = new EWalletPayment("MoMo", "0909123456");
        p1.pay(300000);
        p2.pay(300000);
        p3.pay(300000);
    }
}
