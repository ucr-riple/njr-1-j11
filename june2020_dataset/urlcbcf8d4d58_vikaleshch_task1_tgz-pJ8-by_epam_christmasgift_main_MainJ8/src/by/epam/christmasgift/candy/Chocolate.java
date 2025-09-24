package by.epam.christmasgift.candy;

/**
 * Created by Vika on 25.07.2014.
 */
public class Chocolate  extends Candy {
    private ChocolateType chocolateType;
    private StuffingType stuffingType;

    public Chocolate(int protein, int fat, int carbohydrate, int kiloCalories,
                      int weight, int sugarContent, ChocolateType chocolateType,
                      StuffingType stuffingType) {
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        this.chocolateType = chocolateType;
        this.stuffingType = stuffingType;
    }

    public enum ChocolateType {
        DARK, MILK,WHITE
    }

    public enum StuffingType {
        PRALINE, FUDGE, LIQUOR, SOUFFLE, JELLY, WAFFLE
    }

    public ChocolateType getChocolateType() {
        return chocolateType;
    }

    public StuffingType getStuffingType() {
        return stuffingType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Chocolate chocolate = (Chocolate) o;

        if (chocolateType != chocolate.chocolateType) return false;
        if (stuffingType != chocolate.stuffingType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + chocolateType.hashCode();
        result = 31 * result + stuffingType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chocolate { ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" chocolateType: ");
        stringBuilder.append(chocolateType);
        stringBuilder.append(" stuffingType: ");
        stringBuilder.append(stuffingType);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
