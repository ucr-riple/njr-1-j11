package edu.clients;

import edu.services.docs.DocDefaults;

/**
 * Created by yurii.pyvovarenko on 3/4/14.
 */
public class FullName {
    private String name;
    private String secondName;
    private String surname;

    public FullName(String surname, String name, String secondName) {
        if ((name.equals("")) || (null == name) ) {
            this.name = DocDefaults.NO_NAME;
        } else {
            this.name = name;
        }

        if ((secondName.equals("")) || (null == secondName) ){
            this.secondName = DocDefaults.NO_SECOND_NAME;
        } else {
            this.secondName = secondName;
        }

        if ((surname.equals("")) || (null == surname) ){
            this.surname = DocDefaults.NO_SURNAME;
        } else {
            this.surname = surname;
        }
    }

    public String toString() {
        return (surname + " " + name + " " + secondName);
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (! (other instanceof FullName) )
            return false;
        FullName otherFullName = (FullName) other;
        return toString().equals(otherFullName.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public String getName() {
        return name;
    }
}
