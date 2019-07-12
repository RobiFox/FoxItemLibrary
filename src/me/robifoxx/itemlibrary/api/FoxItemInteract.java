package me.robifoxx.itemlibrary.api;

import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface FoxItemInteract {
    void onInteract(PlayerInteractEvent e);
    void onInteractEntity(PlayerInteractEntityEvent e);
}
