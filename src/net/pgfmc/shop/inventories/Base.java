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
		pages = (int) (Math.ceil(listings.size() / 36.0));
		Listing.loadListings();
		
		ItemStack listingItem = null;
		
		if (listings != null) {
			int index = 0;
			
			for (Listing listing : listings) // Assigns each slot a listing
			{
				goToPage(1);
				
				if (listings.size() <= 36)
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
		
		
		
		inv.setItem(49, Main.createItem("§2Refresh", Material.SUNFLOWER));
		inv.setItem(52, Main.createItem("§eNew Listing", Material.OAK_SIGN));
		inv.setItem(53, Main.createItem("§eMy Listings", Material.GOLD_NUGGET));
	}
	
	public void goToPage(int page) { // interprets the listing data, and adds each listing to the interface
		
		int loopHelp = 0;
		
		while (loopHelp < 36) {
			
			try {
				ItemStack itemStack = listings.get(loopHelp + ((page - 1) * 36)).getItem();
				ItemMeta itemMeta = itemStack.getItemMeta();
				List<String> lore = new ArrayList<String>();
				
				lore.add("Price: " + Main.makePlural(itemStack));
				itemStack.setItemMeta(itemMeta);
				inv.setItem(loopHelp, itemStack);
			} catch(IndexOutOfBoundsException e) {
				inv.clear(loopHelp);
			}
			loopHelp++;
		}
		
		if (currentPage != pages) {
			inv.setItem(50, Main.createItem("§aNext Page", Material.ARROW));
		} else {
			inv.clear(50);
		}
		
		if (currentPage != 1) {
			inv.setItem(48, Main.createItem("§aPrevious Page", Material.IRON_HOE));
		} else {
			inv.clear(48);
		}
	}
	
	public void flipPage(boolean advance) { // flips the page forwards/backwards (true/false)
		if (advance && currentPage != pages) {
			currentPage++;
			goToPage(currentPage);
			
		} else if (!advance && currentPage != 1) {
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
	
	public int getPage() {
		return currentPage;
	}
}