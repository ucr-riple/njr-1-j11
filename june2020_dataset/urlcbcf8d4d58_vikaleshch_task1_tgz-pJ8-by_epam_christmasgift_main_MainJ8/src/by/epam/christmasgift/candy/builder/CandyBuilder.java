package by.epam.christmasgift.candy.builder;

import by.epam.christmasgift.candy.Candy;

public abstract class CandyBuilder {
    private int protein;
    private int fat;
    private int carbohydrate;
    private int kiloCalories;
    private int weight;
    private int sugarContent;

    protected CandyBuilder(int protein, int fat, int carbohydrate,
                           int kiloCalories, int weight, int sugarContent) {
        setProtein(protein);
        setFat(fat);
        setCarbohydrate(carbohydrate);
        setKiloCalories(kiloCalories);
        setWeight(weight);
        setSugarContent(sugarContent);
    }

    public void setProtein(int protein) {
        if (protein > 0) {
            this.protein = protein;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setFat(int fat) {
        if (fat > 0) {
            this.fat = fat;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setCarbohydrate(int carbohydrate) {
        if (carbohydrate > 0) {
            this.carbohydrate = carbohydrate;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setKiloCalories(int kiloCalories) {
        if (kiloCalories > 0) {
            this.kiloCalories = kiloCalories;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setWeight(int weight) {
        if (weight > 0) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void setSugarContent(int sugarContent) {
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

    public abstract Candy createCandy();
}