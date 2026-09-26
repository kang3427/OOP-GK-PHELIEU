package reward;

@FunctionalInterface
public interface Rewardable {
    /*
    weight tong khoi luong phe lieu da ban(kg)
    basePrice tong gia tri don hang(VND)
    */
    double calculateReward(double weight, double basePrice);
}
