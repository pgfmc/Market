package net.pgfmc.shop.events;

import java.io.File;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.inventories.Base;
import net.pgfmc.shop.inventories.Confirm;
import net.pgfmc.shop.inventories.MyListings;
import net.pgfmc.shop.inventories.NewListing;
import net.pgfmc.shop.inventories.ViewOwnListing;

public class InventoryEvents implements Listener {
	
	File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@EventHandler
	public void onInventoryClickBase(InventoryClickEvent e) // Main menu interface manager (list of listings)
	{
		Inventory inv = e.getClickedInventory();
		if ((inv == null || !(inv.getHolder() instanceof Base)) || e.getCurrentItem() == null) { return; } // If the inventory isn't of Base.java then kick us out
		
		Base inventory = (Base) inv.getHolder();
		
		// If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		
		e.setCancelled(true);
		
		int slot = e.getSlot();
		
		if (slot >= 0 && slot < 35) { // -------------------- its 35, not 36 because 4 x 9 - 1 = 35, and we -1 because lists start at 0
			// code here for buying an item / offering to buy an item
			
			e.getWhoClicked().openInventory(new Confirm(Listing.getListings().get(slot)).getInventory()); // opens a buy interface to buy an item
			return;
		}
		
		switch(slot) { // switch statement
			
		case 46: 	return; // Emerald
		
		case 48: 	inventory.flipPage(false);
			return; // Iron hoe
		
		case 49: 	e.getWhoClicked().closeInventory(); // Close their inventory
		
					Base gui = new Base();
					((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Open a new instance of the inventory
					return;
		
		case 50:	return; // Feather // idk still idk
		
		case 52:	e.getWhoClicked().closeInventory(); // Close their inventory // Oak Sign
		
					NewListing gui1 = new NewListing();
					((Player) e.getWhoClicked()).openInventory(gui1.getInventory()); // Opens NewListing inventory (Cast to Player might be unnecessary but I don't know)
					return;
		
		case 53:	e.getWhoClicked().closeInventory(); // Close their inventory // Gold Nugget
		
					MyListings gui2 = new MyListings((Player) e.getWhoClicked());
					((Player) e.getWhoClicked()).openInventory(gui2.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
		
		default:	return;
		}
	}
	
	@EventHandler
	public void onInventoryClickNewlisting(InventoryClickEvent e) { // New Listing interface manager
		
		((Player) e.getWhoClicked()).sendMessage(String.valueOf(e.getSlot())); // Debug
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof NewListing)) { return; } // If the inventory isn't of NewListing.java then kick us out
		
		NewListing inventory = (NewListing) inv.getHolder();
		
		int slot = e.getSlot();
		
		e.setCancelled(true); // defaults to cancel true :-)
		
		switch(slot) { // switch statement
		
		case 0: 	e.getWhoClicked().closeInventory(); // Close their inventory
		
					Base gui = new Base();
					((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
		case 4: 	e.setCancelled(false);
					return;
		case 10:	inventory.incrementPrice(-1);
					return;
		case 11:	inventory.incrementPrice(-5);
					return;
		case 12:	inventory.incrementPrice(-10);
					return;
		case 13: 	inventory.setCurrency(e.getCursor().getType());			
		
		case 14: 	inventory.incrementPrice(10);
					return;
		case 15:	inventory.incrementPrice(5);
					return;
		case 16: 	inventory.incrementPrice(1);
					return;
					
		case 26:	if (e.getInventory().getItem(4) == null) {
						return;
					}
					
					inventory.finalizeListing((Player) e.getWhoClicked());
		
					Listing.saveListings(); // saves listing
		
					e.getWhoClicked().closeInventory(); // Close their inventory
					return;
					
		default: 	return;
		}
	}
	
	@EventHandler
	public void onInventoryClickMyListings(InventoryClickEvent e) { // MyListings interface manager
		
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof MyListings)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		MyListings inventory = (MyListings) inv.getHolder();
		
		e.setCancelled(true);
		
		int slot = e.getSlot();
		int page = inventory.getPage();
		List<Listing> listings = inventory.getListings();
		
		if (slot >= 2 && slot <= 8) { // ----------sets Listings in the interface
			new ViewOwnListing(listings.get((slot - 2) + (page - 1) * 21));
		} else if (slot >= 11 && slot <= 17) {
			new ViewOwnListing(listings.get((slot - 4) + (page - 1) * 21));
		} else if (slot >= 20 && slot <= 26) {
			new ViewOwnListing(listings.get((slot - 6) + (page - 1) * 21));
		}
		
		switch(slot) {
		
		case 0: 	// Close their inventory
		
					Base gui = new Base();
					((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
		case 9:		if (inv.getItem(e.getSlot()) != null) { // goes to previous page
						inventory.flipPage(false);
					}
					return;
		case 18: 	if (inv.getItem(e.getSlot()) != null) { // goes to next page
						inventory.flipPage(true);
					}
					return;
		default: 	return;
		}
	}
	
	@EventHandler
	public void onInventoryClickConfirmListing(InventoryClickEvent e) { // buy interface manager
		
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof Confirm)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		Confirm inventory = (Confirm) inv.getHolder();
		
		e.setCancelled(true);
		if (!inventory.isBought) { return; } // if the item has been bought already
		
		int slot = e.getSlot();
		
		switch(slot) {
		
		case 0:		// Close their inventory
		
					Base gui = new Base();
					((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
					
		case 11: 	if ((e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE) && inventory.canBuy()) {
						e.setCancelled(false);
						inventory.confirmBuy();
					}
		
		}
		
		
		
		
		
		
		
		
		
		
		
	}
	
	public void inventoryCloseConfirmListing(InventoryCloseEvent e) {
		
	}
}
