package me.robifoxx.itemlibrary.api;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;

public interface FoxItemDurable {
    void itemDamage(PlayerItemDamageEvent e);
}
