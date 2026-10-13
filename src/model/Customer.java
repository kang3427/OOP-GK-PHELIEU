package model;

import enums.CustomerType;

/**
 * Thông tin khách hàng/đối tác giao dịch với cửa hàng phế liệu.
 * Lớp có thể dùng trực tiếp cho khách hàng thông thường; vẫn cho phép lớp con
 * override displayInfo() nếu dự án cần các loại khách hàng chuyên biệt.
 */
public class Customer {
    private final String customerId;
    private final String name;
    private final String phone;
    private final String address;
    private final CustomerType customerType;
    private int rewardPoints;

    /** Constructor tương thích với phiên bản trước: mặc định khách thường và 0 điểm. */
    public Customer(String customerId, String name, String phone, String address) {
        this(customerId, name, phone, address, CustomerType.REGULAR);
    }

    public Customer(String customerId, String name, String phone, String address,
                    CustomerType customerType) {
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã khách hàng không được để trống.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên khách hàng không được để trống.");
        }
        if (customerType == null) {
            throw new IllegalArgumentException("Loại khách hàng không được null.");
        }

        this.customerId = customerId.trim();
        this.name = name.trim();
        this.phone = phone == null ? "" : phone.trim();
        this.address = address == null ? "" : address.trim();
        this.customerType = customerType;
        this.rewardPoints = 0;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public CustomerType getCustomerType() { return customerType; }
    public int getRewardPoints() { return rewardPoints; }

    /** Cộng điểm thưởng không âm, đồng thời phát hiện tràn số nguyên. */
    public void addRewardPoints(int points) {
        if (points < 0) {
            throw new IllegalArgumentException("Điểm thưởng được cộng phải không âm.");
        }
        rewardPoints = Math.addExact(rewardPoints, points);
    }

    public void displayInfo() {
        System.out.println("Mã khách hàng: " + customerId
                + " | Họ tên: " + name
                + " | SĐT: " + (phone.isEmpty() ? "Chưa cập nhật" : phone)
                + " | Địa chỉ: " + (address.isEmpty() ? "Chưa cập nhật" : address)
                + " | Loại: " + customerType
                + " | Điểm thưởng: " + rewardPoints);
    }
}
