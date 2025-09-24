package by.epam.christmasgift.candy;

/**
 * Created by Vika on 25.07.2014.
 */
public class Caramel extends Candy {
    private CaramelType caramelType;

    public Caramel(int protein, int fat, int carbohydrate, int kiloCalories,
                      int weight, int sugarContent, CaramelType caramelType) {
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        this.caramelType = caramelType;
    }

    public enum CaramelType {
        FRUIT, CHOCOLATE, LIQUOR, BERRY
    }

    public CaramelType getCaramelType() {
        return caramelType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Caramel caramel = (Caramel) o;

        if (caramelType != caramel.caramelType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + caramelType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Caramel {");
        stringBuilder.append(super.toString());
        stringBuilder.append(" caramelType: ");
        stringBuilder.append(caramelType);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
