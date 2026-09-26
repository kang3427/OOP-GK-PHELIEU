package model;

/**
 * Lớp đại diện cho thông tin Khách hàng / Đối tác giao dịch với vựa phế liệu.
 * Quản lý thông tin cá nhân, nhóm khách hàng (CustomerType) và số điểm thưởng tích lũy (rewardPoints).
 */

public abstract class Customer {
    private final String customerId;
    private final String name;
    private final String phone;
    private final String address;

    protected Customer(String customerId, String name, String phone, String address) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public abstract void displayInfo();
}
