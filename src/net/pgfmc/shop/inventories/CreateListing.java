package net.pgfmc.shop.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.core.inventoryAPI.BaseInventory;
import net.pgfmc.core.inventoryAPI.extra.Button;
import net.pgfmc.core.inventoryAPI.extra.SizeData;
import net.pgfmc.core.playerdataAPI.PlayerData;

public class CreateListing extends BaseInventory {

    PlayerData pd;
    ItemStack price;

    public CreateListing(PlayerData pd) {
        super(SizeData.SMALL, "Sell Item!");
        this.pd = pd;

        setOpen();
    }

    private void setPrice(ItemStack sell) {

        buttons = new Button[buttons.length];
        inv.clear();

        setButton(0, new Button(Material.FEATHER, (e, i) -> {
            e.getWhoClicked().getInventory().addItem(sell);
            e.getWhoClicked().openInventory(new MainScreen(pd).getInventory());
        }));

        setButton(4, new Button(sell, (e, i) -> {
            e.setCancelled(false);

        }));

        setButton(3, new Button(Material.GRAY_CONCRETE));
        setButton(5, new Button(Material.GRAY_CONCRETE));

        ItemStack item = new ItemStack(Material.RED_CONCRETE);
        item.setAmount(10);
        ItemMeta imeta = item.getItemMeta();
        imeta.setDisplayName("");
        item.setItemMeta(imeta);
        setButton(10, new Button(item));

        item = new ItemStack(Material.RED_CONCRETE);
        item.setAmount(5);
        imeta = item.getItemMeta();
        imeta.setDisplayName("");
        item.setItemMeta(imeta);
        setButton(11, new Button(item));

        setButton(12, new Button(Material.RED_CONCRETE, ""));

        setButton(13, new Button(Material.AIR, (e, i) -> {
            if (e.getCursor() != null && e.getCursor().getType() != Material.AIR) {
                if (price != null) {
                    price.setType(e.getCursor().getType());
                } else {
                    price = e.getCursor().clone();
                }
            }
        }));

        setButton(14, new Button(Material.GREEN_CONCRETE, ""));

        item = new ItemStack(Material.GREEN_CONCRETE);
        item.setAmount(5);
        imeta = item.getItemMeta();
        imeta.setDisplayName("");
        item.setItemMeta(imeta);
        setButton(15, new Button(item));

        item = new ItemStack(Material.GREEN_CONCRETE);
        item.setAmount(10);
        imeta = item.getItemMeta();
        imeta.setDisplayName("");
        item.setItemMeta(imeta);
        setButton(16, new Button(item));

        setButton(22, new Button(Material.GRAY_CONCRETE, "To set the price of your item, \nput an item in the slot above!"));

    }

    private void setOpen() {

        buttons = new Button[buttons.length];
        inv.clear();

        setButton(0, new Button(Material.FEATHER, (e, i) -> {
            e.getWhoClicked().openInventory(new MainScreen(pd).getInventory());
        }, "§cBack"));

        setButton(4, new Button(Material.GRAY_CONCRETE));
        setButton(12, new Button(Material.GRAY_CONCRETE));
        setButton(14, new Button(Material.GRAY_CONCRETE));
        setButton(22, new Button(Material.GRAY_CONCRETE));

        setButton(13, new Button(Material.AIR, (e, i) -> {
            setPrice(e.getCursor());
        }));
    }
    


    
}
