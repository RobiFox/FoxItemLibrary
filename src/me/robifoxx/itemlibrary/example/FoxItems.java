package me.robifoxx.itemlibrary.example;

import me.robifoxx.itemlibrary.api.ItemGroup;

public class FoxItems extends ItemGroup {
    public FoxItems() {
        registerItem(new TestItem());
    }

    @Override
    public String getPrefix() {
        return "foxxitem";
    }
}
