package me.robifoxx.itemlibrary.api;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public interface FoxItemMine {
    void onMine(BlockBreakEvent e);
}
