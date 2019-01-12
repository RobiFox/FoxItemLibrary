package me.robifoxx.itemlibrary.api;

import me.robifoxx.itemlibrary.api.FoxItem;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public abstract class FoxBlock extends FoxItem {
    // TODO
    public abstract void onPlace(BlockPlaceEvent e);
    public abstract void onBreak(BlockBreakEvent e);
}
