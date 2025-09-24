package by.epam.christmasgift.candy.builder;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.Lollipop;

/**
 * Created by Vika on 31.07.2014.
 */
public class LollipopBuilder extends CandyBuilder{
    private String flavor;

    public LollipopBuilder(int protein, int fat, int carbohydrate,
                           int kiloCalories, int weight, int sugarContent,
                           String flavor){
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        setFlavor(flavor);
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getFlavor() {
        return flavor;
    }

    @Override
    public Candy createCandy() {
        return new Lollipop(getProtein(), getFat(), getCarbohydrate(),
                getKiloCalories(),getWeight(),getSugarContent(),
                getFlavor());
    }
}
