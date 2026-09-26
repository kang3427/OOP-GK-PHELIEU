package model;

public abstract class ScrapItem {
    private final String id;
    private final String name;
    private double pricePerKg;
    private double weight;

    public ScrapItem(String id,String name, double pricePerKg, double weight){
        if (id == null || name == null || pricePerKg <=0 || weight <0
                || id.trim().isEmpty() || name.trim().isEmpty()){
            throw new IllegalArgumentException("Thông tin Phế Liệu KHÔNG phù hợp"); }
        this.id=id;
        this.name=name;
        this.pricePerKg=pricePerKg;
        this.weight=weight;
    }

    public String getId(){ return id; }
    public String getName(){ return name; }
    public double getPricePerKg(){ return pricePerKg; }
    public double getWeight(){ return weight; }

    @Override
    public String toString() {
        return "Phế liệu: " + this.name +
                " | Giá: " + this.pricePerKg +
                " | Cân nặng: " + this.weight;
    }

    public void setPricePerKg(double pricePerKg){
        this.pricePerKg=pricePerKg;
    }

    public void setWeight(double weight){
        this.weight=weight;
    }

    public abstract void displayInfo();
}
