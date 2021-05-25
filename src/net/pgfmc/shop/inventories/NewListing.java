package net.pgfmc.shop.inventories;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.pgfmc.shop.Listing;
import net.pgfmc.shop.Listing.listingType;
import net.pgfmc.shop.Main;

// manages the inventory for creating a new listing

public class NewListing implements InventoryHolder {
	
	private Inventory inv;
	private ItemStack price = new ItemStack(Material.DIAMOND, 1);
	private boolean isTrade = false;
	private boolean closing = false;
	
	public NewListing()
	{
		inv = Bukkit.createInventory(this, 27, "New Listing");
		
		inv.setItem(0, Main.createItem("§eCancel", Material.FEATHER));
		inv.setItem(3, Main.createItem("§aItem Goes Here >>", Material.NETHER_STAR));
		inv.setItem(5, Main.createItem("§a<< Item Goes Here", Material.NETHER_STAR));
		inv.setItem(10, Main.createItem("§c-1", Material.IRON_NUGGET));
		inv.setItem(11, Main.createItem("§c-5", Material.GOLD_INGOT));
		inv.setItem(12, Main.createItem("§c-10", Material.EMERALD));
		inv.setItem(13, switchLore(Main.createItem("§bClick Here with any item to Change the Currency Used!", Material.DIAMOND), Main.makePlural(price)));
		inv.setItem(14, Main.createItem("§e+10", Material.EMERALD));
		inv.setItem(15, Main.createItem("§e+5", Material.GOLD_INGOT));
		inv.setItem(16, Main.createItem("§e+1", Material.IRON_NUGGET));
		inv.setItem(26, Main.createItem("§4CONFIRM LISTING", Material.SLIME_BALL));
	}
	
	public int getPrice() {
		return price.getAmount();
	}
	
	public void incrementPrice(int increment) {
		
		price.setAmount(price.getAmount() + increment);
		if (price.getAmount() > price.getMaxStackSize()) { // ---- overflow protection
			price.setAmount(price.getMaxStackSize());
		} else if (price.getAmount() < 1) {
			price.setAmount(1);
		}
		inv.setItem(13, switchLore(price, Main.makePlural(price)));
	}
	
	public Material getCurrency() {
		return price.getType();
	}
	
	public void setCurrency(Material mat) {
		if (mat != Material.AIR) {
			price.setType(mat);
			inv.setItem(13, switchLore(price, String.valueOf(price.getAmount()) + Main.makePlural(price)));
		}
		incrementPrice(0);
		return;
	}
	
	public boolean getIsTrade() {
		return isTrade;
	}
	
	public void setTrade(boolean setter) {
		isTrade = setter;
	}
	
	public void setClosing(boolean setter) {
		closing = setter;
	}
	
	public boolean getClosing() {
		return closing;
	}
	
	public void finalizeListing(Player player) {
		listingType type = listingType.LISTING;
		if (isTrade) {
			type = listingType.TRADEOFFER;
		}
		new Listing((OfflinePlayer) player, inv.getItem(4), price, type);
	}
	
	public static ItemStack switchLore(ItemStack item, String lore) { // changes the lore of an item using a string as input
		
		ItemMeta itemMeta = item.getItemMeta();
		
		List<String> list = new ArrayList<String>();
		list.add(lore);
		itemMeta.setLore(list);
		item.setItemMeta(itemMeta);
		return item;
	}
	
	@Override
	public Inventory getInventory() {
		return inv;
	}
}