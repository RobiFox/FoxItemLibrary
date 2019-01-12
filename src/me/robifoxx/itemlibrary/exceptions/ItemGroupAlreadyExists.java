package me.robifoxx.itemlibrary.exceptions;

public class ItemGroupAlreadyExists extends RuntimeException {
    public ItemGroupAlreadyExists() {
        super();
    }
    public ItemGroupAlreadyExists(String s) {
        super(s);
    }
}
