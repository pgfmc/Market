package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

// manages the inventory for viewing your own listings

public class MyListings implements InventoryHolder {
	
	private final Inventory inv;
	private final List<Listing> list = new ArrayList<Listing>();
	private final int pages;
	private transient int currentPage = 1;
	
	
	public MyListings(Player player) { // ---------------------------- constructor
		inv = Bukkit.createInventory(this, 27, "My Listings");
		for (Listing listing : Listing.getListings()) { // adds all Listings the player posted to the list
			if ((Player) listing.getPlayer() == player) {
				list.add(listing);
			}
		}
		pages = ((int) Math.floor((list.size() + 1) / 21));
		
		Bukkit.broadcastMessage(String.valueOf(pages));
		
		invBuilder();
	}
	
	private void invBuilder() {
		
		inv.setItem(0, Main.createItem("§eBack", Material.FEATHER));
		
		if (pages > 1) { // if the size of the list is 21 or greater, show buttons for changing pages
			inv.setItem(9, Main.createItem("Previous Page", Material.IRON_HOE));
			inv.setItem(18, Main.createItem("Next Page", Material.ARROW));
		}
		goToPage(1);
	}
	
	public void goToPage(int page) { // interprets the listing data, and adds each listing to the interface
		
		for (Listing listing : list) {
			
			ItemStack itemStack = listing.getItem();
			ItemMeta itemMeta = itemStack.getItemMeta();
			int index = list.indexOf(listing);
			
			List<String> lore = new ArrayList<String>();
			
			lore.add("Price: " + Main.makePlural(itemStack));
			
			itemStack.setItemMeta(itemMeta);
			
			if (index >= (page - 1) * 21 && index <= (page - 1) * 21 + 6) { // sets Listings in the interface
				inv.setItem(index + 2 - (currentPage-1)*21, itemStack);
			} else if (index >= (page - 1) * 21 + 7 && index <= (page - 1) * 21 + 13) {
				inv.setItem(index + 4 - (currentPage-1)*21, itemStack);
			} else if (index >= (page - 1) * 21 + 14 && index <= (page - 1) * 21 + 20) {
				inv.setItem((index + 6) - (currentPage-1)*21, itemStack);
			}
		}
	}
	
	public void flipPage(boolean advance) { // flips the page forwards/backwards (true/false)
		if (advance) {
			currentPage++;
			goToPage(currentPage);
		} else {
			currentPage--;
			goToPage(currentPage);
		}
	}
	
	public int getPage() {
		return currentPage;
	}
	
	public List<Listing> getListings() {
		return list;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}
