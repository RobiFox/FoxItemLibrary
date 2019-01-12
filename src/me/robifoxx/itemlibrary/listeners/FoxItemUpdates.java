package me.robifoxx.itemlibrary.listeners;

import me.robifoxx.itemlibrary.api.FoxItemAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class FoxItemUpdates implements Listener {
    @EventHandler
    public void join(PlayerJoinEvent e) {
        for(ItemStack i : e.getPlayer().getInventory().getContents()) {
            if(i != null) {
                if(FoxItemAPI.getInstance().isFoxItem(i)) {
                    FoxItemAPI.getInstance().findItem(i).updateItem(i);
                }
            }
        }
    }
}
