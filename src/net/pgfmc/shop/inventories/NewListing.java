package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

// manages the inventory for creating a new listing

public class NewListing implements InventoryHolder {
	
	private Inventory inv;
	
	public NewListing()
	{
		inv = Bukkit.createInventory(this, 27, "New Listing");
		invBuilder();
	}
	
	private void invBuilder()
	{
		inv.setItem(0, createItem("§eCancel", Material.FEATHER));
		inv.setItem(3, createItem("§aItem Goes Here >>", Material.NETHER_STAR));
		inv.setItem(5, createItem("§a<< Item Goes Here", Material.NETHER_STAR));
		inv.setItem(10, createItem("§c-1", Material.IRON_NUGGET));
		inv.setItem(11, createItem("§c-5", Material.GOLD_INGOT));
		inv.setItem(12, createItem("§c-10", Material.DIAMOND));
		inv.setItem(13, createItem("§bCOST", Material.EMERALD));
		inv.setItem(14, createItem("§e+10", Material.DIAMOND));
		inv.setItem(15, createItem("§e+5", Material.GOLD_INGOT));
		inv.setItem(16, createItem("§e+1", Material.IRON_NUGGET));
		inv.setItem(26, createItem("§4CONFIRM LISTING", Material.SLIME_BALL));
	}
	
	private ItemStack createItem(String name, Material mat) // function for creating an item with a custom name
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private ItemStack createItemWithLore(String name, Material mat, List<String> lore) // function for creating a new item with 
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
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