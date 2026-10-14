package model;
import enums.*;

/**
 * Lớp đại diện cho thông tin Khách hàng / Đối tác giao dịch với vựa phế liệu.
 * Quản lý thông tin cá nhân, nhóm khách hàng (CustomerType) và số điểm thưởng tích lũy (rewardPoints).
 */

public abstract class Customer {
    private final String customerId;
    private final String name;
    private final String phone;
    private final String address;
    private CustomerType customerType;

    public Customer(String customerId, String name, String phone, String address, CustomerType customerType) {
        // 1. Kiểm tra Mã khách hàng (customerId)
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống!");
        }

        // 2. Kiểm tra Tên khách hàng (name)
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống!");
        }

        // 3. Kiểm tra Số điện thoại (phone)
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Số điện thoại không được để trống!");
        }

        // 4. Kiểm tra Địa chỉ (address)
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Địa chỉ không được để trống!");
        }
        // 5.Kiểm tra loại khách
        if (customerType == null) {
            throw new IllegalArgumentException("Loại khách hàng không được để trống!");
        }

        this.customerId = customerId.trim();
        this.name = name.trim();
        this.phone = phone.trim();
        this.address = address.trim();
        this.customerType = customerType;
    }

    public abstract void displayInfo();
}
