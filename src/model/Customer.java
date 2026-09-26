package model;

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
