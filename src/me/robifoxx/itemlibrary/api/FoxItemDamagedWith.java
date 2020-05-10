package me.robifoxx.itemlibrary.api;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public interface FoxItemDamagedWith {
    void damagedWith(EntityDamageByEntityEvent e, EquipmentSlot slot);
}
