package me.robifoxx.itemlibrary.listeners;

import me.robifoxx.itemlibrary.FoxItemLibrary;
import me.robifoxx.itemlibrary.api.*;
import org.bukkit.Material;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class FoxItemListeners implements Listener {
    @EventHandler
    public void interact(PlayerInteractEvent e) {
       /* try {
            if (e.getPlayer().getName().equalsIgnoreCase("RobiFoxx")) {
                e.getPlayer().sendMessage(FoxItemAPI.getInstance().findItem(e.getItem()) + "");
            }
        } catch(Exception ex) {

        }*/
        FoxItem item;
        if(e.getItem() != null
                && (item = FoxItemAPI.getInstance().findItem(e.getItem())) != null) {
            itemClick.put(e.getPlayer().getName(), item);
            if(item instanceof FoxItemInteract) {
                ((FoxItemInteract) item).onInteract(e);
            }
            if(item instanceof FoxItemNoInteraction) {
                e.setCancelled(true);
            }
        } else {
            itemClick.put(e.getPlayer().getName(), null);
        }
    }

    @EventHandler()
    public void interact(BlockBreakEvent e) {
        if(e.isCancelled()) {
            return;
        }
        FoxItem item;
        if(e.getPlayer().getInventory().getItemInMainHand() != null
                && (item = FoxItemAPI.getInstance().findItem(e.getPlayer().getInventory().getItemInMainHand())) != null) {
            if(item instanceof FoxItemMine) {
                ((FoxItemMine) item).onMine(e);
            }
            if(item instanceof FoxItemNoInteraction) {
                e.setCancelled(true);
            }
        }
    }

    private HashMap<String, FoxItem> itemClick = new HashMap<>();

    @EventHandler
    public void throwProj(ProjectileLaunchEvent e) {
        FoxItem item;
        if(e.getEntity().getShooter() != null
                && e.getEntity().getShooter() instanceof Player
                && (item = itemClick.get(((Player) e.getEntity().getShooter()).getName())) instanceof FoxItemProjectile) {
            e.getEntity().setMetadata("FoxItemID", new FixedMetadataValue(FoxItemLibrary.getPlugin(FoxItemLibrary.class), item.getGroup() + ":" + item.getUniqueId()));
            ((FoxItemProjectile) item).onThrow(e);
        }
    }

    @EventHandler
    public void throwProj(ProjectileHitEvent e) {
        FoxItem item;
        if(e.getEntity().getShooter() != null
                && e.getEntity().getShooter() instanceof Player
                && !e.getEntity().getMetadata("FoxItemID").isEmpty()
                && (item = FoxItemAPI.getInstance().findItem(e.getEntity().getMetadata("FoxItemID").get(0).asString())) != null) {
            ((FoxItemProjectile) item).onImpact(e);
        }
    }


    @EventHandler()
    public void interact(PlayerInteractEntityEvent e) {
        if(e.isCancelled()) {
            return;
        }
        FoxItem item;
        if(e.getPlayer().getInventory().getItemInMainHand() != null
                && (item = FoxItemAPI.getInstance().findItem(e.getPlayer().getInventory().getItemInMainHand())) != null) {
            if(item instanceof FoxItemInteract) {
                ((FoxItemInteract) item).onInteractEntity(e);
            }
            if(item instanceof FoxItemNoInteraction) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler()
    public void interact(EntityDamageByEntityEvent e) {
        if(e.isCancelled() || !(e.getDamager() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getDamager();
        FoxItem item;
        if(p.getInventory().getItemInMainHand() != null
                && (item = FoxItemAPI.getInstance().findItem(p.getInventory().getItemInMainHand())) != null) {
            if(item instanceof FoxItemDamageWith) {
                ((FoxItemDamageWith) item).damage(e);
            }
        }
    }

    private static final EquipmentSlot[] SLOTS = new EquipmentSlot[] {EquipmentSlot.FEET, EquipmentSlot.LEGS, EquipmentSlot.CHEST, EquipmentSlot.HEAD, EquipmentSlot.HAND};

    @EventHandler()
    public void damagedWith(EntityDamageByEntityEvent e) {
        if(e.isCancelled() || !(e.getEntity() instanceof Player)) {
            return;
        }
        Player p = (Player) e.getEntity();
        List<ItemStack> items = Arrays.stream(p.getEquipment().getArmorContents()).collect(Collectors.toList());
        if((p.getInventory().getItemInMainHand() != null
                && FoxItemAPI.getInstance().findItem(p.getInventory().getItemInMainHand()) != null)) {
            items.add(p.getInventory().getItemInMainHand());
        }
        int n = 0;
        for(ItemStack i : items) {
            FoxItem item = FoxItemAPI.getInstance().findItem(i);
            if(item instanceof FoxItemDamagedWith) {
                ((FoxItemDamagedWith) item).damagedWith(e, SLOTS[n]);
            }
            n++;
        }
    }

    @EventHandler()
    public void interact(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        FoxItem item;
        if(p != null
                && p.getInventory().getItemInMainHand() != null
                && (item = FoxItemAPI.getInstance().findItem(p.getInventory().getItemInMainHand())) != null) {
            if(item instanceof FoxItemKillWith) {
                ((FoxItemKillWith) item).kill(e);
            }
        }
    }

    @EventHandler
    public void itemDamage(PlayerItemDamageEvent e) {
        FoxItem item;
        if((item = FoxItemAPI.getInstance().findItem(e.getItem())) != null) {
            if(item.getItem().getType() == Material.BOW
                    && e.getPlayer().getInventory().getItemInMainHand() != null
                    && e.getPlayer().getInventory().getItemInMainHand().getType() == Material.BOW
                    && !item.isSimilar(e.getPlayer().getInventory().getItemInMainHand())) return;
            if(item instanceof FoxItemDurable) {
                ((FoxItemDurable) item).itemDamage(e);
            }
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
