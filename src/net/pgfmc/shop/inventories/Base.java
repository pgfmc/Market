package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class Base implements InventoryHolder {
	
	private Inventory inv;
	
	public Base() // constructor (creates main menu for shops) 
	{
		inv = Bukkit.createInventory(this, 54, "Shop");
		invBuilder();
	}
	
	private void invBuilder() // builds the inventory to show all the listings
	{
		Listing.loadListings();
		List<Listing> listings = Listing.getListings(); // Gets the Listing from the database.yml file
		
		ItemStack listingItem = null;
		
		if (listings != null)
		{
			
			int index = 0;
			
			for (Listing listing : listings) // Assigns each slot a listing
			{
				if (listings.size() < 36)
				{
					listingItem = listing.getItem() ; // ItemStack of item
					Material itemType = listingItem.getType(); // Item type
					String itemCost = listing.getPrice().toString(); // Item cost
					
					List<String> lore = new ArrayList<String>(); // lore builder
					lore.add("§7Cost: §b§l" + itemCost + " Bits");
					lore.add("§7Sold by §r" + listing.getPlayer().getName());
					
					inv.setItem(index, Main.createItemWithLore(itemType.toString(), itemType, lore));
					index++;
				}
			}
		}
		
		inv.setItem(46, Main.createItemWithLore("§eBalance", Material.EMERALD, Main.createLore("0 Bits")));
		inv.setItem(48, Main.createItem("§aPrevious", Material.FEATHER));
		inv.setItem(49, Main.createItem("§2Refresh", Material.SUNFLOWER));
		inv.setItem(50, Main.createItem("§aNext", Material.FEATHER));
		inv.setItem(52, Main.createItem("§eNew Listing", Material.OAK_SIGN));
		inv.setItem(53, Main.createItem("§eMy Listings", Material.GOLD_NUGGET));
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	

}
