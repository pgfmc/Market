package net.pgfmc.shop.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

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
	
	
	
	
	
	@EventHandler
	public void onInventoryClickNewlisting(InventoryClickEvent e)
	{
		if (!(e.getInventory().getHolder() instanceof NewListing)) { return; } // If the inventory isn't of NewListing.java then kick us out
		
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
