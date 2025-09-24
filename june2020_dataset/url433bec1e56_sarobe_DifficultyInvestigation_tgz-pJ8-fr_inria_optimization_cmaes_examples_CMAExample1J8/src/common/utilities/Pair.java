package common.utilities;

// Sourced from http://stackoverflow.com/questions/156275/what-is-the-equivalent-of-the-c-pairl-r-in-java
// with a few modifications
// Absolutely baffled as to why there's no generic pair type in Java.

public class Pair<A, B> {
    public A first;
    public B second;

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    public int hashCode() {
        int hashFirst = first != null ? first.hashCode() : 0;
        int hashSecond = second != null ? second.hashCode() : 0;

        return (hashFirst + hashSecond) * hashSecond + hashFirst;
    }

    public boolean equals(Object other) {
        if (other instanceof Pair) {
            Pair otherPair = (Pair) other;
            return
                    ((  this.first == otherPair.first ||
                            ( this.first != null && otherPair.first != null &&
                                    this.first.equals(otherPair.first))) &&
                            (      this.second == otherPair.second ||
                                    ( this.second != null && otherPair.second != null &&
                                            this.second.equals(otherPair.second))) );
        }

        return false;
    }

    public String toString()
    {
        return "(" + first + ", " + second + ")";
    }
}
