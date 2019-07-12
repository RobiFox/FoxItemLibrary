package me.robifoxx.itemlibrary.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import de.tr7zw.itemnbtapi.NBTCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.UUID;

public abstract class FoxItem {
    private HashMap<String, Object> nbt = new HashMap<>();
    private ItemNBTManager im = ItemNBTManager.getInstance();
    ItemGroup group;
    public abstract String getUniqueId();
    public abstract ItemStack getItem();

    public abstract ItemMeta setItemMeta(ItemStack i);
    //public abstract void storeData(NBTCompound item);

    public final ItemStack createItem() {
        ItemStack i = getItem().clone();
        for(String s : nbt.keySet()) {
            i = im.set("FoxItemCustomData", s, nbt.get(s), i);
        }
        i = im.set("FoxItemData", "FoxItemID", group.getPrefix() + ":" + getUniqueId(), i);
        return updateItem(i);
    }

    public ItemStack updateItem(ItemStack i) {
        ItemMeta m = setItemMeta(i);
        m.setUnbreakable(i.getItemMeta().isUnbreakable());
        i.setItemMeta(m);
        return i;
    }

    public void putDefaultNBT(String path, Object value) {
        nbt.put(path, value);
    }

    /*public final NBTCompound getNBTCompound(NBTItem nbtItem) {
        return (String.valueOf(nbtItem.getCompound("FoxItemData")).equalsIgnoreCase("") || nbtItem.getCompound("FoxItemData") == null) ? nbtItem.addCompound("FoxItemData") : nbtItem.getCompound("FoxItemData");
    }*/

    public final boolean isSimilar(ItemStack i) {
        String r = im.get("FoxItemData", "FoxItemID", i);
        return r != null && r.equalsIgnoreCase(group.getPrefix() + ":" + getUniqueId());
    }

    public ItemStack updateNbt(String path, Object value, ItemStack i) {
        HashMap<String, Object> h = new HashMap<>();
        h.put(path, value);
        return updateNbt(h, i);
    }

    public ItemStack updateNbt(HashMap<String, Object> hash, ItemStack i) {
        ItemStack item = i.clone();
        for(String s : hash.keySet()) {
            item = im.set("FoxItemCustomData", s, hash.get(s), item);
        }
        return updateItem(item);
    }

    public Object getNbt(String path, ItemStack i) {
        if(im.get("FoxItemCustomData", path, i) == null) {
            return nbt.get(path);
        } else {
            if(nbt.get(path) instanceof Integer) {
                return im.getInt("FoxItemCustomData", path, i);
            } else {
                return im.get("FoxItemCustomData", path, i);
            }
        }
    }

    private ItemStack create() {
        ItemStack itemStack = new MaterialData(Material.SKULL_ITEM, (byte) 3).toItemStack(1);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public final ItemStack createSkull(String urlToFormat) {
        String s = urlToFormat.replace("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv", "");
        String url = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv" + s;
        ItemStack head = create();
        if (url.isEmpty()) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", url));

        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public String getGroup() {
        return group.getPrefix();
    }
}
