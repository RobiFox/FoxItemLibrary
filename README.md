# FoxItemLibrary
FoxItemLibrary is an API that lets you create custom 100% server-sided items that can be easily updated or modified.

The plugin takes care of all the backend, so all you have to do is make sure they are registered. If item meta data is changed, the updates will be automatically reflected for all players.

## Usage
Create a class that extends `FoxItem` or a subclass of it.

```Java
public class ClickCounter extends FoxItem {
    @Override
    public String getUniqueId() {
        return "clickcounter";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLD_PICKAXE);
    }

    @Override
    public ItemMeta setItemMeta(Player p, ItemStack i) {
        return i.getItemMeta();
    }
}
```

You can implement [several interfaces](https://github.com/RobiFox/FoxItemLibrary/tree/master/src/me/robifoxx/itemlibrary/api) to add custom functionality to your items

```Java
public class ClickCounter extends FoxItem implements FoxItemInteract {
    @Override
    public String getUniqueId() {
        return "clickcounter";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLD_PICKAXE);
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Click!");
    }

    @Override
    public void onInteractEntity(PlayerInteractEntityEvent e) {

    }
}
```
The above code will send the player a "Click!" message whenever they interact with the item with a left or right click.

It is also possible to store custom data on items that are unique to that instance of the item, and also represent them in ItemMeta. In this case, we can store the amount of clicks performed on this item.

```Java
public class ClickCounter extends FoxItem implements FoxItemInteract {
    public static final String CLICKS = "Clicks";
    public ClickCountr() {
        putDefaultNBT(CLICKS, 0); // assign a default value if it's not stored in the item's nbt yet
    }

    @Override
    public String getUniqueId() {
        return "clickcounter";
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(Material.GOLD_PICKAXE);
    }

    @Override
    public ItemMeta setItemMeta(Player p, ItemStack i) {
        ItemMeta m = i.getItemMeta();
        m.setDisplayName("§6Clicks: §2" + getNbt(CLICKS, i)); // show the amount of clicks in the item's description
        return m;
    }

    @Override
    public void onInteract(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("Click!");
        FoxItem item = FoxItemAPI.getInstance().findItem(e.getItem()); // convert ItemStack to FoxItem
        ItemStack i = item.updateNbt(e.getPlayer(), CLICKS, (int) item.getNbt(CLICKS, e.getItem()) + 1, e.getItem()); // add +1 to the click counter. returns an ItemStack with updated values (updated description and NBT value in this case)
        e.getPlayer().getInventory().setItemInMainHand(i); // replace the itemstack in the player's inventory
    }

    @Override
    public void onInteractEntity(PlayerInteractEntityEvent e) {

    }
}
```

Everything's done! All that's left is registering the ItemGroup and giving the players the item.
```java
public class MyItemGroup extends me.robifoxx.itemlibrary.api.ItemGroup {
    public static final ClickCounter CLICK_COUNTER = new ClickCounter();
    
    public MyItemGroup() {
        registerItem(CLICK_COUNTER);
    }

    @Override
    public String getPrefix() {
        return "foxitemtester";
    }
}
```
```java
FoxItemAPI.getInstance().registerItemGroup(MyItemGroup());
```

You can give the item to the player with 
```Java
player.getInventory().addItem(MyItemGroup.CLICK_COUNTER.createItem(player));
```
You can also give player with pre-set NBT values if you create a HashMap and pass it as a parameter in `createItem(player, hashMap);`

The reason there's a Player parameter in createItem is that some custom items might rely on player's stats (Imagine required level to use item, required skills, etc.)

## Notes
It is highly recommended to create an abstract class extending FoxItem, and handle the backend for anything custom you need there, such as custom damage values with a custom damage system, custom enchants display etc etc.

It is also recommended to use Reflections to automatically find all fields in your ItemGroup, and register them automatically. It is also possible to assign a unique id at run time based on the field's name.