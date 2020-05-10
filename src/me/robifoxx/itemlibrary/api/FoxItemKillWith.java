package me.robifoxx.itemlibrary.api;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public interface FoxItemKillWith {
    void kill(EntityDeathEvent e);
}
