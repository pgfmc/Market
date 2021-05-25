package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class Base implements InventoryHolder {
	
	private Inventory inv;
	private List<Listing> listings = new ArrayList<Listing>();
	private int pages;
	private transient int currentPage = 1;
	
	public Base() // constructor (creates main menu for shops) 
	{
		inv = Bukkit.createInventory(this, 54, "Shop");
		
		listings = Listing.getListings(); // Gets the Listing from the database.yml file
		pages = ((int) Math.floor((listings.size() + 1) / 36));
		Listing.loadListings();
		
		ItemStack listingItem = null;
		
		if (listings != null) {
			int index = 0;
			
			for (Listing listing : listings) // Assigns each slot a listing
			{
				if (listings.size() < 36)
				{
					listingItem = listing.getItem() ; // ItemStack of item
					
					List<String> lore = new ArrayList<String>(); // lore builder
					lore.add("§7Cost: " + listing.getPrice());
					lore.add("§7Sold by §r" + listing.getPlayer().getName());
					
					ItemMeta itemMeta = listingItem.getItemMeta();
					itemMeta.setLore(lore);
					listingItem.setItemMeta(itemMeta);
					
					inv.setItem(index, listingItem);
					index++;
				}
			}
		}
		
		inv.setItem(46, createItemWithLore("§eBalance", Material.EMERALD, createLore("§r§bClick here to view what You've Earned!")));
		
		// I'm going to try to make a "universal" language where an arrow would stand as "next", then a hoe would stand as "previous"
		// like with pages
		// feather = "back"
		// slimeball = "confirm"
		
		if (pages > 1) { // ------------ if the amount of listings is more than 36, then it allows for more pages
			inv.setItem(48, Main.createItem("§aPrevious Page", Material.IRON_HOE));
			inv.setItem(50, Main.createItem("§aNext Page", Material.ARROW));
		}
		
		inv.setItem(49, Main.createItem("§2Refresh", Material.SUNFLOWER));
		inv.setItem(52, Main.createItem("§eNew Listing", Material.OAK_SIGN));
		inv.setItem(53, Main.createItem("§eMy Listings", Material.GOLD_NUGGET));
	}
	
	public void goToPage(int page) { // interprets the listing data, and adds each listing to the interface
		
		for (Listing listing : listings) {
			
			ItemStack itemStack = listing.getItem();
			ItemMeta itemMeta = itemStack.getItemMeta();
			int index = listings.indexOf(listing);
			
			List<String> lore = new ArrayList<String>();
			
			lore.add("Price: " + Main.makePlural(itemStack));
			
			itemStack.setItemMeta(itemMeta);
			
			if (index >= (page - 1) * 36 && index <= (page - 1) * 36 + 8) { // sets Listings in the interface
				inv.setItem(index - (currentPage - 1) * 36, itemStack);
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
	
	private static ItemStack createItemWithLore(String name, Material mat, List<String> lore) // function for creating a new item with 
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	public static List<String> createLore(String line1) { // Creates a List<String> that represents lore // I want to be organized and not have this code for every ItemStack I want to create with a lore  -.-
		
		List<String> lore = new ArrayList<String>();
		lore.add(line1);
		return lore;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}