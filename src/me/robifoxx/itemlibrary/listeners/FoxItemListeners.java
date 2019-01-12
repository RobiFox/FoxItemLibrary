package me.robifoxx.itemlibrary.listeners;

import me.robifoxx.itemlibrary.api.FoxBlock;
import me.robifoxx.itemlibrary.api.FoxItem;
import me.robifoxx.itemlibrary.api.FoxItemAPI;
import me.robifoxx.itemlibrary.api.FoxItemInteract;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class FoxItemListeners implements Listener {
    @EventHandler
    public void interact(PlayerInteractEvent e) {
        FoxItem item;
        if(e.getItem() != null
                && (item = FoxItemAPI.getInstance().findItem(e.getItem())) != null
                && item instanceof FoxItemInteract) {
            ((FoxItemInteract) item).onInteract(e);
        }
    }

    /*@EventHandler
    public void interact(BlockPlaceEvent e) {
        FoxItem item;
        if(e.getItemInHand() != null
                && (item = FoxItemAPI.getInstance().findItem(e.getItemInHand())) != null
                && item instanceof FoxBlock) {
            ((FoxBlock) item).onPlace(e);
        }
    }*/
}
