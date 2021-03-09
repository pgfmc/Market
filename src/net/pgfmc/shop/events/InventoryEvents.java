package net.pgfmc.shop.events;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.shop.Database;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.inventories.Base;
import net.pgfmc.shop.inventories.MyListings;
import net.pgfmc.shop.inventories.NewListing;

public class InventoryEvents implements Listener {
	

	
	@EventHandler
	public void onInventoryClickBase(InventoryClickEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof Base)) { return; } // If the inventory isn't of Base.java then kick us out
		
		if (e.getCurrentItem() == null) // If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		{
			e.setCancelled(true);
			return;
		}
		
		if (e.getCurrentItem() != null) // If it is NOT null, execute the aciton that belongs to that slot
		{
			if (e.getSlot() == 46) // Balance (Emerald)
			{
				// Nothing happens
			}
			
			if (e.getSlot() == 48) // **I don't want to code this part yet**
			{
				// TODO Code Previous FEATHER
			}
			
			if (e.getSlot() == 49) // Refresh (Sunflower)
			{
				e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
				e.getWhoClicked().closeInventory(); // Close their inventory
				
				Base gui = new Base();
				((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Open a new instance of the inventory
				return;
			}
			
			if (e.getSlot() == 50) // **I don't want to code this part yet**
			{
				// TODO Code Next FEATHER
			}
			
			if (e.getSlot() == 52) //  New Listing (Oak Sign)
			{
				e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
				e.getWhoClicked().closeInventory(); // Close their inventory
				
				NewListing gui = new NewListing();
				((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens NewListing inventory (Cast to Player might be unnecessary but I don't know)
				return;
			}
			
			if (e.getSlot() == 53) // My Listings (Gold Nugget)
			{
				e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
				e.getWhoClicked().closeInventory(); // Close their inventory
				
				MyListings gui = new MyListings();
				((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
				return;
			}
			
			
			e.setCancelled(true); // A catch just in case all above if statements fail
			return;
			
		}
	}
	
	
	
	

	int cost = 50; // Default value for cost
	
	@EventHandler
	public void onInventoryClickNewlisting(InventoryClickEvent e)
	{
		List<Object> listing = new ArrayList<>();
		List<List<Object>> listings = new ArrayList<>();
		
		if (!(e.getInventory().getHolder() instanceof NewListing)) { return; } // If the inventory isn't of NewListing.java then kick us out
		
		if (e.getCurrentItem() == null && e.getSlot() != 4) // If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		{
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 0)
		{
			e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
			e.getWhoClicked().closeInventory(); // Close their inventory
			
			Base gui = new Base();
			((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
			return;
		}
		
		if (e.getSlot() == 4) // This is where you place the item you want to sell
		{
			return;
		}
		
		if (e.getSlot() == 10)
		{
			cost -= 1;
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 11)
		{
			cost -= 5;
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 12)
		{
			cost -= 10;
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 14)
		{
			cost += 10;
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 15)
		{
			cost += 5;
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 16)
		{
			cost += 1;
			
			List<String> lore = new ArrayList<String>();
			lore.add("§e§o" + String.valueOf(cost));
			
			e.getInventory().getItem(13).getItemMeta().setLore(lore);
			((Player) e.getWhoClicked()).updateInventory();
			
			e.setCancelled(true);
			return;
		}
		
		if (e.getSlot() == 26)
		{
			if (e.getInventory().getItem(4) == null)
			{
				e.setCancelled(true);
				return;
			}
			

			listing.add(((Player) e.getWhoClicked()).getUniqueId());
			listing.add(e.getInventory().getItem(4));
			listing.add(cost);
			
			if (Database.load(database, file) != null) { listings = Database.load(database, file); } // Null check
			
			listings.add(listing);
			
			Database.save((Player) e.getWhoClicked(), e.getInventory().getItem(4), cost, database, file, listings);
			
			e.setCancelled(true);
			e.getWhoClicked().closeInventory(); // Close their inventory
			
			return;
		}
		
		((Player) e.getWhoClicked()).sendMessage(String.valueOf(e.getSlot()));
		
		e.setCancelled(true); // A catch just in case all above if statements fail
		return;
	}
	
	
	
	
	
	
	@EventHandler
	public void onInventoryClickMyListings(InventoryClickEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof MyListings)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		if (e.getCurrentItem() == null) // If it is null, cancel the click and kick us out (No action to clicking a blank slot)
		{
			e.setCancelled(true);
			return;
		}
		
		
		if (e.getSlot() == 0)
		{
			e.setCancelled(true); // Cancel the event before it closes because it could mess up and let the player keep the item idk
			e.getWhoClicked().closeInventory(); // Close their inventory
			
			Base gui = new Base();
			((Player) e.getWhoClicked()).openInventory(gui.getInventory()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
			return;
		}
		 
	}

}
