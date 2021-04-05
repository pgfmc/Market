package net.pgfmc.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;

public class ViewOwnListing implements InventoryHolder {
	
	private Listing listing;
	private Inventory inv;
	
	public ViewOwnListing(Listing listing) {
		this.listing = listing;
		inv = Bukkit.createInventory(this, 27, listing.getItem().getItemMeta().getDisplayName());
		
		invBuilder();
	}
	
	private void invBuilder() {
		
		inv.setItem(0, Main.createItem("§eBack", Material.FEATHER));
		inv.setItem(12, listing.getItem());
		inv.setItem(14, Main.createItem("take the item to delete the Listing!", Material.PAPER));
	}
	
	public Listing getListing() {
		return listing;
	}
		
	@Override
	public Inventory getInventory() {
		// TODO Auto-generated method stub
		return null;
	}
}
