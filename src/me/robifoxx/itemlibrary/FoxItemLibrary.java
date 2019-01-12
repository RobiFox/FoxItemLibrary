package me.robifoxx.itemlibrary;

import me.robifoxx.itemlibrary.listeners.FoxItemListeners;
import me.robifoxx.itemlibrary.listeners.FoxItemUpdates;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class FoxItemLibrary extends JavaPlugin {
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new FoxItemListeners(), this);
        Bukkit.getPluginManager().registerEvents(new FoxItemUpdates(), this);
    }
}
