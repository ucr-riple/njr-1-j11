package by.epam.christmasgift.candy;

import org.apache.log4j.Logger;

/**
 * Created by Vika on 25.07.2014.
 */
public abstract class Candy {
    protected static Logger logger = Logger.getLogger(Candy.class);
    private int protein;
    private int fat;
    private int carbohydrate;
    private int kiloCalories;
    private int weight;
    private int sugarContent;  //measured in percent

    protected Candy(int protein, int fat, int carbohydrate, int kiloCalories,
                    int weight, int sugarContent) {
        logger.info("Creating Candy");
        setProtein(protein);
        setFat(fat);
        setCarbohydrate(carbohydrate);
        setKiloCalories(kiloCalories);
        setWeight(weight);
        setSugarContent(sugarContent);
    }

    private void setProtein(int protein) {
        if (protein > 0) {
            this.protein = protein;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setFat(int fat) {
        if (fat > 0) {
            this.fat = fat;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setCarbohydrate(int carbohydrate) {
        if (carbohydrate > 0) {
            this.carbohydrate = carbohydrate;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setKiloCalories(int kiloCalories) {
        if (kiloCalories > 0) {
            this.kiloCalories = kiloCalories;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setWeight(int weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException();
        }
    }

    private void setSugarContent(int sugarContent) {
        if ((0 < sugarContent) && (sugarContent < 100)) {
            this.sugarContent = sugarContent;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int getProtein() {
        return protein;
    }

    public int getFat() {
        return fat;
    }

    public int getCarbohydrate() {
        return carbohydrate;
    }

    public int getKiloCalories() {
        return kiloCalories;
    }

    public int getWeight() {
        return weight;
    }

    public int getSugarContent() {
        return sugarContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Candy candy = (Candy) o;

        if (carbohydrate != candy.carbohydrate) return false;
        if (fat != candy.fat) return false;
        if (kiloCalories != candy.kiloCalories) return false;
        if (protein != candy.protein) return false;
        if (sugarContent != candy.sugarContent) return false;
        if (weight != candy.weight) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = protein;
        result = 31 * result + fat;
        result = 31 * result + carbohydrate;
        result = 31 * result + kiloCalories;
        result = 31 * result + weight;
        result = 31 * result + sugarContent;
        return result;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("protein: ");
        stringBuilder.append(getProtein());
        stringBuilder.append(" fat: ");
        stringBuilder.append(getFat());
        stringBuilder.append(" carbohydrate: ");
        stringBuilder.append(getCarbohydrate());
        stringBuilder.append(" kiloCalories: ");
        stringBuilder.append(getKiloCalories());
        stringBuilder.append(" weight: ");
        stringBuilder.append(getWeight());
        stringBuilder.append(" sugarContent: ");
        stringBuilder.append(getSugarContent());
        return stringBuilder.toString();
    }
}
