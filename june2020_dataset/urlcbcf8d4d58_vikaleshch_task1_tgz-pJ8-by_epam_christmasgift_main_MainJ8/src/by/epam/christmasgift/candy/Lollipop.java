package by.epam.christmasgift.candy;

/**
 * Created by Vika on 25.07.2014.
 */
public class Lollipop extends Candy {
    String flavor;

    public Lollipop(int protein, int fat, int carbohydrate, int kiloCalories,
                       int weight, int sugarContent, String flavor) {
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        this.flavor = flavor;
    }

    public String getFlavor() {
        return flavor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Lollipop lollipop = (Lollipop) o;

        if (!flavor.equals(lollipop.flavor)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + flavor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Lollipop { ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" flavor: ");
        stringBuilder.append(flavor);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }
}
