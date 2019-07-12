package me.robifoxx.itemlibrary.api;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemNBTManager {
    private static ItemNBTManager instance;
    
    public static ItemNBTManager getInstance() {
        return instance == null ? (instance = new ItemNBTManager()) : instance;
    }
    
    private ItemNBTManager() { }

    public ItemStack set(String base, String path, Object i, ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        if (!item.hasTag()) {
            item.setTag(new NBTTagCompound());
        }
        NBTTagCompound tag = item.getTag();
        if (!tag.hasKey(base)) {
            tag.set(base, new NBTTagCompound());
        }
        NBTTagCompound info = tag.getCompound(base);
        if(i instanceof String) {
            info.setString(path, (String) i);
        } else if(i instanceof Integer) {
            info.setInt(path, (int) i);
        } else {
            throw new RuntimeException("Unknown type (" + i.getClass() + ")! - " + i);
        }
        item.setTag(tag);
        return CraftItemStack.asCraftMirror(item);
    }

    public String get(String base, String path, ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        if (!item.hasTag()) {
            item.setTag(new NBTTagCompound());
        }
        NBTTagCompound tag = item.getTag();
        if (!tag.hasKey(base)) {
            tag.set(base, new NBTTagCompound());
        }
        NBTTagCompound info = tag.getCompound(base);
        return info.getString(path);
    }

    public int getInt(String base, String path, ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = CraftItemStack.asNMSCopy(itemStack);
        if (!item.hasTag()) {
            item.setTag(new NBTTagCompound());
        }
        NBTTagCompound tag = item.getTag();
        if (!tag.hasKey(base)) {
            tag.set(base, new NBTTagCompound());
        }
        NBTTagCompound info = tag.getCompound(base);
        return info.getInt(path);
    }
}
