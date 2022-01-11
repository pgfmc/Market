package net.pgfmc.shop.Inventories;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.core.inventoryAPI.BaseInventory;
import net.pgfmc.core.inventoryAPI.extra.Butto;
import net.pgfmc.core.inventoryAPI.extra.Button;
import net.pgfmc.core.inventoryAPI.extra.SizeData;
import net.pgfmc.core.playerdataAPI.PlayerData;
import net.pgfmc.shop.Listing;

public class ListingBuy extends BaseInventory {

    PlayerData player;
    Listing listing;

    public ListingBuy(Listing listing, PlayerData pd) {
        super(SizeData.SMALL, "");
        this.player = pd;
        this.listing = listing;

        setButton(0, new Button(Material.FEATHER, (e, d) -> {
            e.getWhoClicked().openInventory(new MainScreen().getInventory()); // opens back to Main screen
        }, "§cBack"));

        removeItem();
    }

    private void removeItem() {
        setButton(2, new Button(Material.GRAY_CONCRETE, ""));
        setButton(10, new Button(Material.GRAY_CONCRETE, ""));
        setButton(12, new Button(Material.GRAY_CONCRETE, ""));
        setButton(20, new Button(Material.GRAY_CONCRETE, ""));

        setButton(11, new Button(Material.AIR, (e, i) -> {
            enterItem(e.getCursor());
        })); // slot in the center off the above slots :)

        setButton(13, new Button(listing.getTrade(), Butto.defaultButto));

        setButton(6, new Button(Material.RED_CONCRETE, ""));
        setButton(14, new Button(Material.RED_CONCRETE, ""));
        setButton(16, new Button(Material.RED_CONCRETE, ""));
        setButton(24, new Button(Material.RED_CONCRETE, ""));

        setButton(15, new Button(listing.getItem().getType(), Butto.defaultButto, null, 
                                            "§dPlace " + listing.getPrice() + "\n§dinto the slot to the left\n§dto buy.")
            );
    }

    private void enterItem(ItemStack item) {
        
        setButton(11, new Button(item, (e, i) -> {
            e.setCancelled(false);
            removeItem();
        }));

        setButton(2, new Button(Material.YELLOW_CONCRETE, ""));
        setButton(10, new Button(Material.YELLOW_CONCRETE, ""));
        setButton(12, new Button(Material.YELLOW_CONCRETE, ""));
        setButton(20, new Button(Material.YELLOW_CONCRETE, ""));

        setButton(6, new Button(Material.GREEN_CONCRETE, ""));
        setButton(14, new Button(Material.GREEN_CONCRETE, ""));
        setButton(16, new Button(Material.GREEN_CONCRETE, ""));
        setButton(24, new Button(Material.GREEN_CONCRETE, ""));

        setButton(15, new Button(listing.getItem(), (e, i) -> {
            e.setCancelled(false);
            buyItem();
        }));
    }

    private void buyItem() {
        listing.deleteListing();
    }
    


    
}
