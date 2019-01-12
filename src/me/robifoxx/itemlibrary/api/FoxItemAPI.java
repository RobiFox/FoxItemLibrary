package me.robifoxx.itemlibrary.api;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import me.robifoxx.itemlibrary.exceptions.ItemGroupAlreadyExists;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoxItemAPI {
    private static FoxItemAPI instance;
    
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
        String[] split = id.split(":");
        String plugin = split[0];
        String itemId = split[1];

        return itemGroups.get(plugin).items.get(itemId);
    }

    public FoxItem findItem(ItemStack i) {
        NBTItem nbtItem = new NBTItem(i);
        NBTCompound foxItemData = nbtItem.getCompound("FoxItemData");
        return findItem(foxItemData.getString("FoxItemID"));
    }

    public boolean isFoxItem(ItemStack i) {
        NBTItem nbtItem = new NBTItem(i);
        NBTCompound foxItemData = nbtItem.getCompound("FoxItemData");
        return foxItemData.getString("FoxItemID") != null;
    }

    public List<ItemGroup> getItemGroupList() {
        return new ArrayList<>(itemGroups.values());
    }
}
