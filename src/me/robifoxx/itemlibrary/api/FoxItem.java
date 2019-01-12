package me.robifoxx.itemlibrary.api;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class FoxItem {
    ItemGroup group;
    public abstract String getUniqueId();
    public abstract Material getMaterial();
    public abstract byte getDamage();

    public abstract ItemMeta setItemMeta(ItemMeta m);
    public abstract void storeData(NBTCompound item);

    public final ItemStack createItem() {
        ItemStack i = new ItemStack(getMaterial(), 1, getDamage());
        updateItem(i);
        NBTCompound foxItemData = getNBTCompound(i);
        storeData(foxItemData);
        return i;
    }

    public final void updateItem(ItemStack i) {
        i.setItemMeta(setItemMeta(i.getItemMeta()));
        NBTCompound foxItemData = getNBTCompound(i);
        foxItemData.setString("FoxItemID", group + ":" + getUniqueId());
        foxItemData.setLong("FoxItemRefreshTimer", System.currentTimeMillis() + FoxItemAPI.getInstance().getRefreshTimerInMilli());
    }

    public final NBTCompound getNBTCompound(ItemStack i) {
        NBTItem nbtItem = new NBTItem(i);
        return nbtItem.getCompound("FoxItemData") == null ? nbtItem.addCompound("FoxItemData") : nbtItem.getCompound("FoxItemData");
    }

    public final boolean isSimilar(ItemStack i) {
        NBTCompound foxItemData = getNBTCompound(i);
        return foxItemData.getString("FoxItemID").equalsIgnoreCase(group + ":" + getUniqueId());
    }
}
