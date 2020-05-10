package me.robifoxx.itemlibrary.api;

import me.robifoxx.itemlibrary.exceptions.ItemGroupAlreadyExists;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoxItemAPI {
    private static FoxItemAPI instance;
    private ItemNBTManager im = ItemNBTManager.getInstance();

    public static FoxItemAPI getInstance() {
        return instance == null ? (instance = new FoxItemAPI()) : instance;
    }

    private HashMap<String, ItemGroup> itemGroups = new HashMap<>();

    public void registerItemGroup(ItemGroup ig) {
        String name = ig.getPrefix();
        if(itemGroups.get(name) == null) {
            itemGroups.put(name, ig);
        } else {
            throw new ItemGroupAlreadyExists("Item Group " + ig.getPrefix() + ": already exists");
        }
    }

    public long getRefreshTimerInMilli() {
        return 21600000;
    }

    public FoxItem findItem(String id) {
        if(id == null || !id.contains(":")) return null;

        String[] split = id.split(":");
        String plugin = split[0];
        String itemId = split[1];

        if(itemGroups.get(plugin) == null
                || itemGroups.get(plugin).items.get(itemId) == null) {
            return null;
        }
        return itemGroups.get(plugin).items.get(itemId);
    }

    public FoxItem findItem(ItemStack i) {
        String id = im.getString("FoxItemData", "FoxItemID", i);
        if(id == null) {
            return null;
        }
        return findItem(id);
    }

    public boolean isSimilar(FoxItem f1, FoxItem f2) {
        return (f1.getGroup() + ":" + f1.getUniqueId()).equalsIgnoreCase(f2.getGroup() + ":" + f2.getUniqueId());
    }

    public boolean isFoxItem(ItemStack i) {
        return im.getString("FoxItemData", "FoxItemID", i) != null;
    }

    public List<ItemGroup> getItemGroupList() {
        return new ArrayList<>(itemGroups.values());
    }

    public ItemGroup findGroupById(String id) {
        return itemGroups.get(id);
    }
}
