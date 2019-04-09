package package1;


import java.util.*;

public class Peripheral {

    public final static ArrayList<String> BRANDS_LIST = new ArrayList<>(Arrays.asList(
        "", "Apacer", "Seagate", "Smasung", "Toshiba", //storages
        "A4Tech", "ACME", "Apple", "BenQ", "Canyon", "Defender", "Dell", "Dialog", "Genesis", "HP", "HyperX",
        "Lenovo", "Logitech", "Microsoft", "MSI", "Razer", "SmartBuy", //input
        "Acer", "AOC", "ASUS", "BenQ", "Dell", "HP", "Lenovo", "LG", "NEC", "Philips", "Samsung" //output
    ))

    ;

    private final String name;
    private final String brandName;
    private final int cost;

    public Peripheral(final String name, final String brandName, final int cost) throws IllegalArgumentException {
        if (!BRANDS_LIST.contains(brandName)) {
            throw new IllegalArgumentException("no such brandName in BRANDS_LIST");
        }
        this.name = name;
        this.brandName = brandName;
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "(\'" + name + "\', \'" + brandName + "\', \'" + cost + "\')";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peripheral that = (Peripheral) o;
        return cost == that.cost &&
                Objects.equals(name, that.name) &&
                Objects.equals(brandName, that.brandName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
