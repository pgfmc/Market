package net.pgfmc.shop.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.inventories.Base;
import net.pgfmc.shop.inventories.MyListings;
import net.pgfmc.shop.inventories.NewListing;
import net.pgfmc.shop.inventories.Notifications;
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
	public void clickEvent(InventoryClickEvent e) { // manages all InventoryClickEvents, and does special processes for each inventory
		
		Inventory inv = e.getClickedInventory();
		Player player = (Player) e.getWhoClicked();
		
		if ((e.getInventory().getHolder() instanceof Base || e.getInventory().getHolder() instanceof NewListing || e.getInventory().getHolder() instanceof MyListings || e.getInventory().getHolder() instanceof PurchaseListing || e.getInventory().getHolder() instanceof ViewOwnListing) && (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT)) {
			e.setCancelled(true);
			return;
		}
		
		if (inv != null && (inv.getHolder() instanceof Base)) { // If the inventory isn't of Base.java then kick us out
			
			Base inventory = (Base) inv.getHolder();
			
			// If it is null, cancel the click and kick us out (No action to clicking a blank slot)
			
			e.setCancelled(true);
			
			int slot = e.getSlot();
			
			if (slot >= 0 && slot < 35) { // -------------------- its 35, not 36 because 4 x 9 - 1 = 35, and we -1 because lists start at 0
				// code here for buying an item / offering to buy an item
				Listing listing = Listing.getListings().get(slot);
				if (listing.getPlayer() == player) {
					openShopInventory(new ViewOwnListing(listing), player);
				} else {
					openShopInventory(new PurchaseListing(Listing.getListings().get(slot)), player); // opens a buy interface to buy an item
				}
				return;
			}
			
			switch(slot) { // switch statement
				
			case 46: 	openShopInventory(new Notifications(player), player);
				return; // Emerald
			
			case 48: 	inventory.flipPage(false);
				return; // Iron hoe
			
			case 49: 	openShopInventory(new Base(), player); // Open a new instance of the inventory
						return;
			
			case 50:	return; // Feather // idk still idk
			
			case 52:	openShopInventory(new NewListing(), player);// Opens NewListing inventory (Cast to Player might be unnecessary but I don't know)
						return;
			
			case 53:	openShopInventory(new MyListings(player), player); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
						return;
			
			default:	return;
			}
			
		} else if (inv!= null && (inv.getHolder() instanceof NewListing)) { // If the inventory isn't of NewListing.java then kick us out
			
			if (e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT) {
				e.setCancelled(true);
				return;
			}
			
			e.setResult(Result.DENY); // defaults to cancel true :-)
			int slot = e.getSlot();
			NewListing inventory = (NewListing) inv.getHolder();
			
			switch(slot) { // switch statement
			
			case 0:		openShopInventory(new Base(), player); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
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
						
						inventory.finalizeListing(player);
						inventory.setClosing(true);
						Listing.saveListings(); // saves listing
			
						player.closeInventory(); // Close their inventory
						openShopInventory(new Base(), player);
						return;
						
			default: 	return;
			}
			
		} else if (inv != null && (inv.getHolder() instanceof MyListings)) { // If the inventory isn't of MyListings.java then kick us out
			
			MyListings inventory = (MyListings) inv.getHolder();
			
			e.setCancelled(true);
			
			int slot = e.getSlot();
			int page = inventory.getPage();
			List<Listing> listings = inventory.getListings();
			
			if (slot >= 2 && slot <= 8) { // ----------sets Listings in the interface
				openShopInventory(new ViewOwnListing(listings.get((slot - 2) + (page - 1) * 21)), player);
			} else if (slot >= 11 && slot <= 17) {
				openShopInventory(new ViewOwnListing(listings.get((slot - 4) + (page - 1) * 21)), player);
			} else if (slot >= 20 && slot <= 26) {
				openShopInventory(new ViewOwnListing(listings.get((slot - 6) + (page - 1) * 21)), player);
			}
			
			switch(slot) {
			
			case 0: 	// Close their inventory
			
						openShopInventory(new Base(), player); // Opens MyListings inventory (Cast to Player might be unnecessary but I don't know)
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
			
		} else if (inv != null && (inv.getHolder() instanceof PurchaseListing)) { // If the inventory isn't of MyListings.java then kick us out
			
			PurchaseListing inventory = (PurchaseListing) inv.getHolder();
		
			int slot = e.getSlot();
			
			if (slot == 0) {
				e.setCancelled(true);
				openShopInventory(new Base(), player);
				return;
				
			} else if (slot == 11 && !inventory.isBought && inventory.canBuy() && (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE)) {
				inventory.confirmBuy(player);
				return;
				
			} else if (slot == 14 && !inventory.isBought && (e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_SOME || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE || e.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
					@Override
					public void run() {
						inventory.canBuy();
					}
				}, 2);
				return;
			} else {
				e.setCancelled(true);
				return;
			}
			
		} else if (inv != null && (inv.getHolder() instanceof ViewOwnListing)) { // If the inventory isn't of MyListings.java then kick us out
			
			ViewOwnListing inventory = (ViewOwnListing) inv.getHolder();
			
				
				int slot = e.getSlot();
					
				if (slot == 0) { // ------------------------------------------------- if the feather is clicked to go back
					e.setCancelled(true);
					openShopInventory(new Base(), player);
					return;
				} else if (slot == 12 && (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF || e.getAction() == InventoryAction.PICKUP_ONE) && !inventory.isTaken) {
					
					inventory.confirmBuy(); // actual hell lol
					return;
				} else {
					e.setCancelled(true);
					return;
				}
				
		} else if (inv != null && (inv.getHolder() instanceof Notifications)) { // If the inventory isn't of Notifications.java then kick us out
			
			e.setCancelled(true);
			Notifications inventory = (Notifications) inv.getHolder();
				
			int slot = e.getSlot();
			
			switch(slot) {
				
			case 0: 	// Close their inventory
					
						openShopInventory(new Base(), player);
						return;
			case 9:		if (inv.getItem(e.getSlot()) != null) { // goes to previous page
							inventory.flipPage(false);
						}
						return;
			case 18: 		if (inv.getItem(e.getSlot()) != null) { // goes to next page
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
