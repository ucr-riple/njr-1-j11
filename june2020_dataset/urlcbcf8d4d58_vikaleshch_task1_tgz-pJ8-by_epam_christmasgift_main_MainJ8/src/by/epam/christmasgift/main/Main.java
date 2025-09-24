package by.epam.christmasgift.main;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.ChristmasGift;
import by.epam.christmasgift.logic.CandyParser;
import by.epam.christmasgift.logic.GiftProcessor;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.util.Comparator;


/**
 * Created by Vika on 27.07.2014.
 */
public class Main {
    static {
        PropertyConfigurator.configure("log4j.properties");
    }
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        ChristmasGift gift = new ChristmasGift();
        CandyParser candyParser = CandyParser.getInstance();
        try {
            gift.addCandy(candyParser.parse(new File("path")));
        } catch (IllegalArgumentException e){
            logger.error(e);
        }
        GiftProcessor processor = new GiftProcessor();
        logger.info("Weight of Gift is "+ processor.getWeight(gift));
        Comparator<Candy> comparator = new Comparator<Candy>() {
            @Override
            public int compare(Candy o1, Candy o2) {
                return Integer.compare(o1.getFat(), o2.getFat());
            }
        };
        gift.sort(comparator);
        logger.info("Sorted Gift: "+ gift.toString());
        int lowerBound = 10;
        int upperBound = 60;
        logger.info("Candies with sugar content between "+ lowerBound + " and "
                + upperBound + " is "
                + processor.getCandyWithSugarContent(gift,lowerBound,upperBound));
    }
}
