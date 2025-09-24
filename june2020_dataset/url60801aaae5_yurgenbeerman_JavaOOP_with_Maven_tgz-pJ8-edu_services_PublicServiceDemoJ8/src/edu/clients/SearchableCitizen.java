package edu.clients;

import java.util.HashSet;

/**
 * Created by yurii.pyvovarenko on 06.05.14.
 */
public class SearchableCitizen extends Citizen {
    private String hobbies;
    private HashSet<String> hobbiesNormalized;

    public SearchableCitizen(String surname, String name, String secondName) {
        super(surname, name, secondName);
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
        //TODO parse hobbies into separate words and put them into hobbiesNormalized
        hobbiesNormalized = splitToWords(hobbies);
    }

    public HashSet<String> splitToWords(String splitToWords) {
        HashSet<String> result = new HashSet<String>();
        String regex = "\\w|[а-яА-Я_0-9]+\\s*"; //[а-яА-Я_0-9] //page 176, 179
        return result;
    }
}
