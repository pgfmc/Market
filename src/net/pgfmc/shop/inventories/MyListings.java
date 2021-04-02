package net.pgfmc.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.pgfmc.shop.Main;

// manages the inventory for viewing your own listings

public class MyListings implements InventoryHolder {
	
	private Inventory inv;
	
	public MyListings()
	{
		inv = Bukkit.createInventory(this, 27, "My Listings");
		invBuilder();
	}
	
	private void invBuilder()
	{
		inv.setItem(0, Main.createItem("§eBack", Material.FEATHER));
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}
