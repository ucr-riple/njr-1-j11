package by.epam.christmasgift.candy.builder;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.Chocolate;

public class ChocolateBuilder extends CandyBuilder{
    private Chocolate.ChocolateType chocolateType;
    private Chocolate.StuffingType stuffingType;

    public ChocolateBuilder(int protein, int fat, int carbohydrate,
                               int kiloCalories, int weight, int sugarContent,
                               Chocolate.ChocolateType chocolateType,
                               Chocolate.StuffingType stuffingType) {
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        setChocolateType(chocolateType);
        setStuffingType(stuffingType);
    }

    public void setChocolateType(Chocolate.ChocolateType chocolateType) {
        this.chocolateType = chocolateType;
    }

    public void setStuffingType(Chocolate.StuffingType stuffingType) {
        this.stuffingType = stuffingType;
    }

    public Chocolate.ChocolateType getChocolateType() {
        return chocolateType;
    }

    public Chocolate.StuffingType getStuffingType() {
        return stuffingType;
    }

    @Override
    public Candy createCandy() {
        return new Chocolate(getProtein(), getFat(), getCarbohydrate(),
                getKiloCalories(), getWeight(), getSugarContent(),
                getChocolateType(), getStuffingType());
    }
}