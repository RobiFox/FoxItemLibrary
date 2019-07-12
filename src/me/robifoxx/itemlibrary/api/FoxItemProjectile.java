package me.robifoxx.itemlibrary.api;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public interface FoxItemProjectile {
    void onThrow(ProjectileLaunchEvent e);
    void onImpact(ProjectileHitEvent e);
}
