package me.robifoxx.itemlibrary.api;

import me.robifoxx.itemlibrary.exceptions.ItemGroupAlreadyExists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class ItemGroup {
    public abstract String getPrefix();

    HashMap<String, FoxItem> items = new HashMap<>();

    public final void registerItem(FoxItem item) {
        //item.group = this;
        String id = item.getUniqueId();
        if(items.get(id) == null) {
            items.put(id, item);
            items.get(id).group = this;
        } else {
            throw new ItemGroupAlreadyExists("Item " + item.getUniqueId() + " already registered");
        }
        item.finishedRegistering();
    }

    public final List<FoxItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public final FoxItem getItem(String id) {
        return items.get(id);
    }
}
