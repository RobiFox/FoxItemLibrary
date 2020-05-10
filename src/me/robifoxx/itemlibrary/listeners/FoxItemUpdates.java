package me.robifoxx.itemlibrary.listeners;

import me.robifoxx.itemlibrary.api.FoxItem;
import me.robifoxx.itemlibrary.api.FoxItemAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class FoxItemUpdates implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        for(int i = 0; i < e.getPlayer().getInventory().getSize(); i++) {
            ItemStack item;
            FoxItem foxItem;
            if((item = e.getPlayer().getInventory().getItem(i)) != null) {
                if((foxItem = FoxItemAPI.getInstance().findItem(item)) != null
                        /*&& foxItem.getNBTCompound(new NBTItem(item)).getLong("FoxItemRefreshTimer") >= System.currentTimeMillis()*/) {
                    //ItemStack cloned = item.clone();
                    item.setType(foxItem.getItem().getType());
                    ItemStack newItem = foxItem.updateItem(e.getPlayer(), item);
                    e.getPlayer().getInventory().setItem(i, newItem);
                }
            }
        }
    }

   /*@EventHandler
    public void join(EnchantItemEvent e) {
        ItemStack item;
        FoxItem foxItem;

        if((item = e.getItem()) != null) {
            if((foxItem = FoxItemAPI.getInstance().findItem(item)) != null) {
                e.setCancelled(true);
            }
        }
    }*/
}
