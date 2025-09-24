package ru.spbau.tinydb.table;

import ru.spbau.tinydb.bufferManager.BufferView;

public class ViewWithId {
    private final BufferView view;
    private final int id;
    
    public ViewWithId(BufferView view, int id) {
        this.view = view;
        this.id = id;
    }

    public BufferView getView() {
        return view;
    }

    public int getId() {
        return id;
    }
}