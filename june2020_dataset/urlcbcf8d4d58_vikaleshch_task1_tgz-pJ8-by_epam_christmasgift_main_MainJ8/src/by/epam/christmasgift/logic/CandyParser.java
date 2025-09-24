package by.epam.christmasgift.logic;

import by.epam.christmasgift.candy.Candy;
import by.epam.christmasgift.candy.Caramel;
import by.epam.christmasgift.candy.Chocolate;
import by.epam.christmasgift.candy.Lollipop;
import by.epam.christmasgift.candy.builder.CandyBuilder;
import by.epam.christmasgift.candy.builder.ChocolateBuilder;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vika on 31.07.2014.
 */
public class CandyParser {
    private static Logger logger = Logger.getLogger(CandyParser.class);
    private static CandyParser instance;

    public static CandyParser getInstance(){
        if (instance == null){
            instance = new CandyParser();
        }
        return instance;
    }

    private CandyParser(){};

    public Iterable<Candy> parse(File file){
        if (file == null){
            throw new IllegalArgumentException("File is not initialized");
        }
        List<Candy> resultList = new ArrayList<>();
        Candy c1 = new Chocolate(10, 20, 20, 20, 50, 45,
                Chocolate.ChocolateType.WHITE, Chocolate.StuffingType.SOUFFLE);
        resultList.add(c1);
        Candy c2 = new Lollipop(30, 20, 10, 60, 40, 60, "Strawberry");
        resultList.add(c2);
        Candy c3 = new Caramel(20, 40, 30, 70, 34, 65, Caramel.CaramelType.FRUIT);
        resultList.add(c3);
        ChocolateBuilder chocolateBuilder = new ChocolateBuilder(20, 15, 20, 30,
                40, 45, Chocolate.ChocolateType.MILK, Chocolate.StuffingType.FUDGE);
        chocolateBuilder.setStuffingType(Chocolate.StuffingType.JELLY);
        resultList.add(chocolateBuilder.createCandy());
        logger.info("Parsing Candies");
        return resultList;
    }
}
