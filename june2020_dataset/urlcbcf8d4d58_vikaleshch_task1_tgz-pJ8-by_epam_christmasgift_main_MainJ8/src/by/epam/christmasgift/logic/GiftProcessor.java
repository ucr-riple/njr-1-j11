package by.epam.christmasgift.logic;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.ChristmasGift;

import java.util.ArrayList;

/**
 * Created by Vika on 27.07.2014.
 */
public class GiftProcessor {
    public GiftProcessor() {}

    public int getWeight(ChristmasGift gift){
        ArrayList<Integer> list = new ArrayList<Integer>();
        int weight = 0;
        list.stream().reduce((a, b) -> a + b);
        for (Candy candy: gift){
            weight += candy.getWeight();
        }
        return weight;
    }

    public ArrayList<Candy> getCandyWithSugarContent(ChristmasGift gift,
                                                     int lowerBound, int upperBound){
        ArrayList<Candy> candies = new ArrayList<>();
        for (Candy candy: gift){
            boolean isInBounds = (lowerBound < candy.getSugarContent()) &&
                            (candy.getSugarContent() < upperBound);
            if (isInBounds){
                candies.add(candy);
            }
        }
        return candies;
    }
}
