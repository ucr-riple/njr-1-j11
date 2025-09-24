package by.epam.christmasgift.candy.builder;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.Caramel;

/**
 * Created by Vika on 31.07.2014.
 */
public class CaramelBuilder extends CandyBuilder{
    private Caramel.CaramelType caramelType;

    public CaramelBuilder(int protein, int fat, int carbohydrate,
                          int kiloCalories, int weight, int sugarContent,
                          Caramel.CaramelType caramelType){
        super(protein, fat, carbohydrate, kiloCalories, weight, sugarContent);
        setCaramelType(caramelType);
    }

    public Caramel.CaramelType getCaramelType() {
        return caramelType;
    }

    public void setCaramelType(Caramel.CaramelType caramelType) {
        this.caramelType = caramelType;
    }


    @Override
    public Candy createCandy() {
        return new Caramel(getProtein(), getFat(), getCarbohydrate(),
                getKiloCalories(), getWeight(), getSugarContent(),
                getCaramelType());
    }
}
