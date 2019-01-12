package me.robifoxx.itemlibrary.example;

import de.tr7zw.itemnbtapi.NBTCompound;
import de.tr7zw.itemnbtapi.NBTList;
import me.robifoxx.itemlibrary.api.FoxItem;
import me.robifoxx.itemlibrary.api.ItemGroup;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class TestItem extends FoxItem {
    @Override
    public String getUniqueId() {
        return null;
    }

    @Override
    public Material getMaterial() {
        return null;
    }

    @Override
    public byte getDamage() {
        return 0;
    }

    @Override
    public ItemMeta setItemMeta(ItemMeta m) {
        return null;
    }

    @Override
    public void storeData(NBTCompound item) {

    }
}
