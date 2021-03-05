package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Base implements InventoryHolder {
	
	private Inventory inv;
	
	public Base()
	{
		inv = Bukkit.createInventory(this, 27, "Shop");
		invBuilder();
	}
	
	
	private void invBuilder()
	{
		
	}
	
	private ItemStack createItem(String name, Material mat, List<String> lore)
	{
		ItemStack item = new ItemStack(mat, 1);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
		return item;
	}
	
	private List<String> createLore(String line1, String line2) // Creates a List<String> that represents lore // I want to be organized and not have this code for every ItemStack I want to create with a lore  -.-
	{
		List<String> lore = new ArrayList<String>();
		lore.add(line1);
		lore.add(line2);
		return lore;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
	
	

}
