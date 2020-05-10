package me.robifoxx.itemlibrary.api;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface FoxItemDamageWith {
    void damage(EntityDamageByEntityEvent e);
}
