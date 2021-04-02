package net.pgfmc.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.pgfmc.shop.Main;

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
		inv.setItem(0, Main.createItem("§eCancel", Material.FEATHER));
		inv.setItem(3, Main.createItem("§aItem Goes Here >>", Material.NETHER_STAR));
		inv.setItem(5, Main.createItem("§a<< Item Goes Here", Material.NETHER_STAR));
		inv.setItem(10, Main.createItem("§c-1", Material.IRON_NUGGET));
		inv.setItem(11, Main.createItem("§c-5", Material.GOLD_INGOT));
		inv.setItem(12, Main.createItem("§c-10", Material.DIAMOND));
		inv.setItem(13, Main.createItem("§bCOST", Material.EMERALD));
		inv.setItem(14, Main.createItem("§e+10", Material.DIAMOND));
		inv.setItem(15, Main.createItem("§e+5", Material.GOLD_INGOT));
		inv.setItem(16, Main.createItem("§e+1", Material.IRON_NUGGET));
		inv.setItem(26, Main.createItem("§4CONFIRM LISTING", Material.SLIME_BALL));
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}