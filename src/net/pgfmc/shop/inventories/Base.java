package net.pgfmc.shop.inventories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Database;
import net.pgfmc.shop.Main;

public class Base implements InventoryHolder {
	
	File file = new File(Main.plugin.getDataFolder() + File.separator + "database.yml"); // Creates a File object
	FileConfiguration database = YamlConfiguration.loadConfiguration(file); // Turns the File object into YAML and loads data
	
	private Inventory inv;
	
	public Base() // constructor (creates main menu for shops) 
	{
		inv = Bukkit.createInventory(this, 54, "Shop");
		invBuilder();
	}
	
	private void invBuilder() // builds the inventory to show all the listings
	{
		List<List<Object>> listings = new ArrayList<>();
		listings = Database.load(database, file); // Gets the List<List<Object>> from the database.yml file
		ItemStack listingItem = null;
		
		if (listings != null)
		{
			for (int i = 0; i < listings.size(); i++) // Assigns each slot a listing
			{
				if (i < 36)
				{
					listingItem = (ItemStack) listings.get(i).get(1); // ItemStack of item
					Material itemType = listingItem.getType(); // Item type
					String itemCost = listings.get(0).toString(); // Item cost
					
					inv.setItem(i, createItemWithLore(itemType.toString(), itemType, createLore("Cost: " + itemCost)));
				}
			}
		}
		
		inv.setItem(46, createItemWithLore("§eBalance", Material.EMERALD, createLore("0 Bits")));
		inv.setItem(48, createItem("§aPrevious", Material.FEATHER));
		inv.setItem(49, createItem("§2Refresh", Material.SUNFLOWER));
		inv.setItem(50, createItem("§aNext", Material.FEATHER));
		inv.setItem(52, createItem("§eNew Listing", Material.OAK_SIGN));
		inv.setItem(53, createItem("§eMy Listings", Material.GOLD_NUGGET));
	}
	
	private ItemStack createItem(String name, Material mat) // creates a new item with a custom name
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	private ItemStack createItemWithLore(String name, Material mat, List<String> lore) // creates a new item with custom name and lore
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private List<String> createLore(String line1) // Creates a List<String> that represents lore // I want to be organized and not have this code for every ItemStack I want to create with a lore  -.-
	{
		List<String> lore = new ArrayList<String>();
		lore.add(line1);
		return lore;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	

}
