package net.pgfmc.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Main;
import net.pgfmc.shop.Listing.listingType;

// manages the inventory for creating a new listing

public class NewListing implements InventoryHolder {
	
	private Inventory inv;
	private ItemStack price = new ItemStack(Material.DIAMOND, 1);
	private boolean isTrade = false;
	
	public NewListing()
	{
		inv = Bukkit.createInventory(this, 27, "New Listing");
		
		invBuilder();
	}
	
	private void invBuilder() {
		
		inv.setItem(0, Main.createItem("§eCancel", Material.FEATHER));
		inv.setItem(3, Main.createItem("§aItem Goes Here >>", Material.NETHER_STAR));
		inv.setItem(5, Main.createItem("§a<< Item Goes Here", Material.NETHER_STAR));
		inv.setItem(10, Main.createItem("§c-1", Material.IRON_NUGGET));
		inv.setItem(11, Main.createItem("§c-5", Material.GOLD_INGOT));
		inv.setItem(12, Main.createItem("§c-10", Material.DIAMOND));
		inv.setItem(13, Main.switchLore(Main.createItem("§bCOST", Material.EMERALD), String.valueOf(price) + " Diamonds"));
		inv.setItem(14, Main.createItem("§e+10", Material.DIAMOND));
		inv.setItem(15, Main.createItem("§e+5", Material.GOLD_INGOT));
		inv.setItem(16, Main.createItem("§e+1", Material.IRON_NUGGET));
		inv.setItem(26, Main.createItem("§4CONFIRM LISTING", Material.SLIME_BALL));
	}
	
	public int getPrice() {
		return price.getAmount();
	}
	
	public void incrementPrice(int increment) {
		
		price.setAmount(price.getAmount() + increment);
		if (price.getAmount() > 64) { // ---- overflow protection
			price.setAmount(64);
		} else if (price.getAmount() < 1) {
			price.setAmount(1);
		}
		inv.setItem(13, Main.switchLore(price, String.valueOf(price.getAmount()) + Main.makePlural(price)));
	}
	
	public Material getCurrency() {
		return price.getType();
	}
	
	public void setCurrency(Material mat) {
		price.setType(mat);
		inv.setItem(13, Main.switchLore(price, String.valueOf(price.getAmount()) + Main.makePlural(price)));
	}
	
	public boolean getIsTrade() {
		return isTrade;
	}
	
	public void setTrade(boolean setter) {
		isTrade = setter;
	}
	
	public void finalizeListing(Player player) {
		listingType type = listingType.LISTING;
		if (isTrade) {
			type = listingType.TRADEOFFER;
		}
		new Listing((OfflinePlayer) player, inv.getItem(4), price, type);
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}