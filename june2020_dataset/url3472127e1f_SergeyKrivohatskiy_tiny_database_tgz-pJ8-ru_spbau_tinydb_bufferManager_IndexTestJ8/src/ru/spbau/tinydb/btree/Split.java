package ru.spbau.tinydb.btree;

class Split {
    public final int key;
    public final int left;
    public final int right;

    public Split(int k, int l, int r) {
        key = k;
        left = l;
        right = r;
    }
}