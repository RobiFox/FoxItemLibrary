package me.robifoxx.itemlibrary.api;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.InvocationTargetException;

public class ItemNBTManager {
    private static ItemNBTManager instance;

    public static ItemNBTManager getInstance() {
        return instance == null ? (instance = new ItemNBTManager()) : instance;
    }

    private ItemNBTManager() { }

    public ItemStack set(String base, String path, Object i, ItemStack itemStack) {
        try {
            Object item = getBukkitNMSClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            if(!(boolean) item.getClass().getMethod("hasTag").invoke(item)) {
                item.getClass().getMethod("setTag", getNMSClass("NBTTagCompound")).invoke(item, getNMSClass("NBTTagCompound").newInstance());
            }
            Object tag = item.getClass().getMethod("getTag").invoke(item);
            if(!(boolean) tag.getClass().getMethod("hasKey", String.class).invoke(tag, base)) {
                tag.getClass().getMethod("set", String.class, getNMSClass("NBTBase")).invoke(tag, base, getNMSClass("NBTTagCompound").newInstance());
            }
            Object info = tag.getClass().getMethod("getCompound", String.class).invoke(tag, base);
            Class<?> type = i.getClass();
            try {
                type = (Class<?>) i.getClass().getField("TYPE").get(null);
            } catch ( NoSuchFieldException ignored) { }
            info.getClass().getMethod("set" + getObjectForReflection(i), String.class, type).invoke(info, path, i);
            item.getClass().getMethod("setTag", getNMSClass("NBTTagCompound")).invoke(item, tag);
            return (ItemStack) getBukkitNMSClass("inventory.CraftItemStack").getMethod("asCraftMirror", getNMSClass("ItemStack")).invoke(null, item);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

   /*public ItemStack setBad(String base, String path, Object i, ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack.asNMSCopy(itemStack);
        if (!item.hasTag()) {
            item.setTag(new net.minecraft.server.v1_12_R1.NBTTagCompound());
        }
        net.minecraft.server.v1_12_R1.NBTTagCompound tag = item.getTag();
        if (!tag.hasKey(base)) {
            tag.set(base, new net.minecraft.server.v1_12_R1.NBTTagCompound());
        }
        net.minecraft.server.v1_12_R1.NBTTagCompound info = tag.getCompound(base);
        if(i instanceof String) {
            info.setString(path, (String) i);
        } else if(i instanceof Integer) {
            info.setInt(path, (int) i);
        } else {
            throw new RuntimeException("Unknown type (" + i.getClass() + ")! - " + i);
        }
        item.setTag(tag);
        return org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack.asCraftMirror(item);
    }*/

    public Object get(String base, String path, String what, ItemStack itemStack) {
        try {
            Object item = getBukkitNMSClass("inventory.CraftItemStack").getMethod("asNMSCopy", ItemStack.class).invoke(null, itemStack);
            if(!(boolean) item.getClass().getMethod("hasTag").invoke(item)) {
                item.getClass().getMethod("setTag", getNMSClass("NBTTagCompound")).invoke(item, getNMSClass("NBTTagCompound").newInstance());
            }
            Object tag = item.getClass().getMethod("getTag").invoke(item);
            if(!(boolean) tag.getClass().getMethod("hasKey", String.class).invoke(tag, base)) {
                tag.getClass().getMethod("set", String.class, getNMSClass("NBTBase")).invoke(tag, base, getNMSClass("NBTTagCompound").newInstance());
            }
            Object info = tag.getClass().getMethod("getCompound", String.class).invoke(tag, base);
            boolean notNull = (Boolean) info.getClass().getMethod("hasKey", String.class).invoke(info, path);
            if(notNull) {
                return info.getClass().getMethod("get" + what, String.class).invoke(info, path);
            } else {
                return null;
            }
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getString(String base, String path, ItemStack itemStack) {
        return (String) get(base, path, "String", itemStack);
    }

    public Integer getInt(String base, String path, ItemStack itemStack) {
        return (Integer) get(base, path, "Int", itemStack);
    }

    /*public String get(String base, String path, ItemStack itemStack) {
        net.minecraft.server.v1_12_R1.ItemStack item = org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack.asNMSCopy(itemStack);
        if (!item.hasTag()) {
            item.setTag(new net.minecraft.server.v1_12_R1.NBTTagCompound());
        }
        net.minecraft.server.v1_12_R1.NBTTagCompound tag = item.getTag();
        if (!tag.hasKey(base)) {
            tag.set(base, new net.minecraft.server.v1_12_R1.NBTTagCompound());
        }
        net.minecraft.server.v1_12_R1.NBTTagCompound info = tag.getCompound(base);
        return info.getString(path);
    }*/

    public Class<?> getNMSClass(String name) {
        // org.bukkit.craftbukkit.v1_8_R3...
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("net.minecraft.server." + version + "." + name);
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
    public Class<?> getBukkitNMSClass(String name) {
        // org.bukkit.craftbukkit.v1_8_R3...
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        try {
            return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
        }

        catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getObjectForReflection(Object obj) {
        if(obj instanceof String) {
            return "String";
        } else if(obj instanceof Integer) {
            return "Int";
        } else if(obj instanceof Boolean) {
            return "Boolean";
        } else if(obj instanceof Long) {
            return "Long";
        } else {
            String classString = obj.getClass().getName();
            return classString.split("\\.")[classString.split("\\.").length - 1];
            //throw new RuntimeException("Unknown type (" + obj.getClass() + ")! - " + obj);
        }
    }
}
