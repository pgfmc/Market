package net.pgfmc.shop.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.Notifications;
import net.pgfmc.shop.inventories.Base;
import net.pgfmc.shop.inventories.MyListings;
import net.pgfmc.shop.inventories.NewListing;
import net.pgfmc.shop.inventories.PurchaseListing;
import net.pgfmc.shop.inventories.ViewOwnListing;

public class InventoryEvents implements Listener {
	
	
	private void openShopInventory(InventoryHolder holder, Player player) {
		
		player.getInventory().addItem(player.getItemOnCursor());
    	
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            @Override
            public void run() {
            	
            	
            	player.closeInventory();
				player.openInventory(holder.getInventory());
				 // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
            }
        }, 1);
	}
	
	@EventHandler
	public void onInventoryClickBase(InventoryClickEvent e) // Main menu interface manager (list of listings)
	{
		Inventory inv = e.getClickedInventory();
		if (inv != null && (inv.getHolder() instanceof Base)) { // If the inventory isn't of Base.java then kick us out
			
			Base inventory = (Base) inv.getHolder();
			
			// If it is null, cancel the click and kick us out (No action to clicking a blank slot)
			
			e.setCancelled(true);
			
			int slot = e.getSlot();
			
			if (slot >= 0 && slot < 35) { // -------------------- its 35, not 36 because 4 x 9 - 1 = 35, and we -1 because lists start at 0
				// code here for buying an item / offering to buy an item
				/*Listing listing = Listing.getListings().get(slot);
				if (listing.getPlayer() == e.getWhoClicked()) {
					e.getWhoClicked().openInventory(new ViewOwnListing(listing).getInventory());
				} else {
					
				}*/
				
				openShopInventory(new PurchaseListing(Listing.getListings().get(slot)), (Player) e.getWhoClicked()); // opens a buy interface to buy an item
				
				return;
			}
			
			switch(slot) { // switch statement
				
			case 46: 	openShopInventory(new Notifications((Player) e.getWhoClicked()), (Player) e.getWhoClicked());
				return; // Emerald
			
			case 48: 	inventory.flipPage(false);
				return; // Iron hoe
			
			case 49: 	openShopInventory(new Base(), (Player) e.getWhoClicked()); // Open a new instance of the inventory
						return;
			
			case 50:	return; // Feather // idk still idk
			
			case 52:	openShopInventory(new NewListing(), (Player) e.getWhoClicked());// Opens NewListing inventory (Cast to Player might be unnecessary but I don't know)
						return;
			
			case 53:	openShopInventory(new MyListings((Player) e.getWhoClicked()), (Player) e.getWhoClicked()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
						return;
			
			default:	return;
			}
		}
	}
	
	@EventHandler
	public void onInventoryClickNewlisting(InventoryClickEvent e) { // New Listing interface manager
		
		((Player) e.getWhoClicked()).sendMessage(String.valueOf(e.getSlot())); // Debug
		Inventory inv = e.getClickedInventory();
		if (inv!= null && (inv.getHolder() instanceof NewListing)) { // If the inventory isn't of NewListing.java then kick us out
			
			e.setResult(Result.DENY); // defaults to cancel true :-)
			int slot = e.getSlot();
			NewListing inventory = (NewListing) inv.getHolder();
			
			switch(slot) { // switch statement
			
			case 0:		openShopInventory(new Base(), (Player) e.getWhoClicked()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
						return;
			case 4: 	e.setResult(Result.ALLOW);
						return;
			case 10:	inventory.incrementPrice(-1);
						return;
			case 11:	inventory.incrementPrice(-5);
						return;
			case 12:	inventory.incrementPrice(-10);
						return;
			case 13: 	inventory.setCurrency(e.getCursor().getType());
						return;
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
						inventory.setClosing(true);
						Listing.saveListings(); // saves listing
			
						e.getWhoClicked().closeInventory(); // Close their inventory
						return;
						
			default: 	return;
			}
		}
	}
	
	@EventHandler
	public void onInventoryClickMyListings(InventoryClickEvent e) { // MyListings interface manager
		
		Inventory inv = e.getClickedInventory();
		if (inv != null && (inv.getHolder() instanceof MyListings)) { // If the inventory isn't of MyListings.java then kick us out
			
			MyListings inventory = (MyListings) inv.getHolder();
			
			e.setCancelled(true);
			
			int slot = e.getSlot();
			int page = inventory.getPage();
			List<Listing> listings = inventory.getListings();
			
			if (slot >= 2 && slot <= 8) { // ----------sets Listings in the interface
				openShopInventory(new ViewOwnListing(listings.get((slot - 2) + (page - 1) * 21)), (Player) e.getWhoClicked());
			} else if (slot >= 11 && slot <= 17) {
				openShopInventory(new ViewOwnListing(listings.get((slot - 4) + (page - 1) * 21)), (Player) e.getWhoClicked());
			} else if (slot >= 20 && slot <= 26) {
				openShopInventory(new ViewOwnListing(listings.get((slot - 6) + (page - 1) * 21)), (Player) e.getWhoClicked());
			}
			
			switch(slot) {
			
			case 0: 	// Close their inventory
			
						openShopInventory(new Base(), (Player) e.getWhoClicked()); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
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
	}
	
	@EventHandler
	public void onInventoryClickConfirmListing(InventoryClickEvent e) { // buy interface manager
		
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof PurchaseListing)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		PurchaseListing inventory = (PurchaseListing) inv.getHolder();
		
		e.setCancelled(true);
		if (inventory.isBought) {  // if the item has been bought already
			
			int slot = e.getSlot();
			
			
			switch(slot) {
			
			case 0: 	// Close their inventory
				
						openShopInventory(new Base(), (Player) e.getWhoClicked());
						return;
		
			default: 	e.setCancelled(true);
						return;
			}
		} else {
			int slot = e.getSlot();
			
			
			switch(slot) {
			
			case 0: 	// Close their inventory
						
						openShopInventory(new Base(), (Player) e.getWhoClicked());
						return;
						
			case 11: 	if ((e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE) && inventory.canBuy()) {
							e.setCancelled(false);
							inventory.confirmBuy();
						}
						return;
			
			case 14: 	if ((e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE || e.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
							e.setCancelled(false);
							Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
								@Override
								public void run() {
									inventory.canBuy();
						 
								}
							}, 2);
							
						}
						
						return;
			default: 	e.setCancelled(true);
						return;
			}
		}
	}
	
	
	@EventHandler
	public void onInventoryClickMyOwnListing(InventoryClickEvent e) { // buy interface manager
		
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof ViewOwnListing)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		ViewOwnListing inventory = (ViewOwnListing) inv.getHolder();
		
		e.setCancelled(true);
		if (inventory.isTaken) {  // if the item has been bought already
			
			int slot = e.getSlot();
			
			
			switch(slot) {
			
			case 0: 	// Close their inventory
				
						openShopInventory(new Base(), (Player) e.getWhoClicked());
						return;
		
			default: 	e.setCancelled(true);
						return;
			}
		} else {
			int slot = e.getSlot();
			
			
			switch(slot) {
			
			case 0: 	// Close their inventory
				
						openShopInventory(new Base(), (Player) e.getWhoClicked());
						return;
						
			case 12: 	if ((e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE)) {
							e.setCancelled(false);
							inventory.confirmBuy();
						}			
						return;
			default: 	e.setCancelled(true);
						return;
			}
		}
	}
	
	@EventHandler
	public void inventoryClickNotifications(InventoryClickEvent e) {
		
		Inventory inv = e.getClickedInventory();
		if (inv == null || !(inv.getHolder() instanceof Notifications)) { return; } // If the inventory isn't of MyListings.java then kick us out
		
		e.setCancelled(true);
		Notifications inventory = (Notifications) inv.getHolder();
			
		int slot = e.getSlot();
		
		switch(slot) {
			
		case 0: 	// Close their inventory
				
					openShopInventory(new Base(), (Player) e.getWhoClicked());
					return;
		case 9:		if (inv.getItem(e.getSlot()) != null) { // goes to previous page
						inventory.flipPage(false);
					}
					return;
		case 18: 	if (inv.getItem(e.getSlot()) != null) { // goes to next page
						inventory.flipPage(true);
					}
					return;
		
		default: 	if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) { // only if all are picked up at once
						e.setCancelled(false);
						inventory.itemTaken(slot);
					}
					return;
		}
	}
	
	@EventHandler
	public void inventoryCloseConfirmListing(InventoryCloseEvent e) { // gives the item in the inventory that the player put in it if the inventory closes somehow

		Inventory inv = e.getInventory();
		if (inv != null && inv.getHolder() instanceof PurchaseListing && ((PurchaseListing) inv.getHolder()).getClosing() == false) {

			e.getPlayer().getInventory().addItem(inv.getItem(14));
		} else if (inv != null && inv.getHolder() instanceof NewListing && ((NewListing) inv.getHolder()).getClosing() == false) {

			e.getPlayer().getInventory().addItem(inv.getItem(4));
		}
	}
	
	@EventHandler
	public void inventoryPreventItemDragging(InventoryDragEvent e) { // prevents item dragging in shop inventories

		InventoryHolder inv = e.getView().getTopInventory().getHolder();
		if (inv instanceof Base || inv instanceof PurchaseListing || inv instanceof MyListings || inv instanceof NewListing) {
			e.setResult(Result.DENY);
		}
	}
}
