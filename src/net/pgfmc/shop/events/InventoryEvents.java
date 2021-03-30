package net.pgfmc.shop.events;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.inventories.Base;
import net.pgfmc.shop.inventories.MyListings;
import net.pgfmc.shop.inventories.NewListing;

public class InventoryEvents implements Listener {
	
	File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	@EventHandler
	public void onInventoryClickBase(InventoryClickEvent e) // Main menu interface manager (list of listings)
	{
		
		if (!(e.getInventory().getHolder() instanceof Base) || e.getCurrentItem() == null) { return; } // If the inventory isn't of Base.java then kick us out
		
		// If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		
		e.setCancelled(true);
		
		
		int slot = e.getSlot();
		
		if (slot >= 0 && slot < 35) { // -------------------- its 35, not 36 because 4 x 9 - 1 = 35, and we -1 because lists start at 0
			// code here for buying an item / offering to buy an item
			return;
		}
		
		
		
		switch(slot) { // switch statement
			
		case 46: 	return; // Emerald
		
		case 48: 	return; // Feather // idk lol
		
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
		
					MyListings gui2 = new MyListings();
					((Player) e.getWhoClicked()).openInventory(gui2.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
		
		default:	return;
		}
	}
	
	
	
	

	int cost = 50; // Default value for cost
	
	@EventHandler
	public void onInventoryClickNewlisting(InventoryClickEvent e) // New Listing interface manager
	{
		
		if (!(e.getInventory().getHolder() instanceof NewListing)) { return; } // If the inventory isn't of NewListing.java then kick us out
		
		int slot = e.getSlot();
		
		((Player) e.getWhoClicked()).sendMessage(String.valueOf(slot)); // Debug
		
		
		e.setCancelled(true); // defaults to cancel true :-)
		
		switch(slot) { // switch statement
		
		case 0: 	e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
					e.getWhoClicked().closeInventory(); // Close their inventory
		
					Base gui = new Base();
					((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
					return;
		case 4: 	e.setCancelled(false);
					return;
		case 10:	cost -= 1;
					return;
		case 11:	cost -= 5;
					return;
		case 12:	cost -= 10;
					return;
		case 14: 	cost += 10;
					return;
		case 15:	cost += 5;
					return;
		case 16: 	cost += 1;
		
					List<String> lore = new ArrayList<String>();
					lore.add("§e§o" + String.valueOf(cost));
		
					e.getInventory().getItem(13).getItemMeta().setLore(lore);
					((Player) e.getWhoClicked()).updateInventory();
					return;
					
		case 26:	if (e.getInventory().getItem(4) == null) {
						e.setCancelled(true);
						return;
					}
		
					new Listing((OfflinePlayer) e.getWhoClicked(), e.getInventory().getItem(4), cost); // creates listing
		
					Listing.saveListings(); // saves listing
		
					e.getWhoClicked().closeInventory(); // Close their inventory
					return;
					
		default: 	return;
		}
	}
	
	
	
	
	
	
	@EventHandler
	public void onInventoryClickMyListings(InventoryClickEvent e) // MyListings interface manager
	{
		if (!(e.getInventory().getHolder() instanceof MyListings)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		if (e.getCurrentItem() == null) // If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		{
			e.setCancelled(true);
			return;
		}
		
		
		if (e.getSlot() == 0) // TODO change get slot because it is WRONG!!
		{
			e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
			e.getWhoClicked().closeInventory(); // Close their inventory
			
			Base gui = new Base();
			((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
			return;
		}
		 
	}

}
